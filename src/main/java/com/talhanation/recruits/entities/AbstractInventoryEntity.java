package com.talhanation.recruits.entities;

import com.talhanation.recruits.Main;
import com.talhanation.recruits.FactionEvents;
//? if <1.21 {
//?}
import com.talhanation.recruits.config.RecruitsServerConfig;
import com.talhanation.recruits.inventory.RecruitSimpleContainer;
import com.talhanation.recruits.pathfinding.AsyncPathfinderMob;
import com.talhanation.recruits.platform.PlatformHooks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
//? if >=1.21 {
/*import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantments;
*///?}
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractSkullBlock;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static net.minecraft.world.entity.EquipmentSlot.*;

public abstract class AbstractInventoryEntity extends AsyncPathfinderMob {


    //iv slots
    //4 = offhand
    //5 = mainhand
    //0,1,2,3 = armor
    //rest = inv

    public RecruitSimpleContainer inventory;
    private int beforeItemSlot = -1;

    public AbstractInventoryEntity(EntityType<? extends AbstractInventoryEntity> entityType, Level world) {
        super(entityType, world);
        this.createInventory();
        this.setCanPickUpLoot(true);
    }

    ///////////////////////////////////TICK/////////////////////////////////////////

    public void aiStep() {
        super.aiStep();
    }

    public void tick() {
        super.tick();
    }

    ////////////////////////////////////DATA////////////////////////////////////

    protected void defineSynchedData() {

        super.defineSynchedData();
    }

    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        ListTag listnbt = new ListTag();
        for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
            ItemStack itemstack = this.inventory.getItem(i);
            if (!itemstack.isEmpty()) {
                CompoundTag compoundnbt = new CompoundTag();
                compoundnbt.putByte("Slot", (byte) i);
                //? if <1.21 {
                itemstack.save(compoundnbt);
                //?} else {
                /*itemstack.save(this.registryAccess(), compoundnbt);
                *///?}
                listnbt.add(compoundnbt);
            }
        }

        nbt.put("Items", listnbt);
        nbt.putInt("BeforeItemSlot", this.getBeforeItemSlot());
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        ListTag listnbt = nbt.getList("Items", 10);//muss 10 sein amk sonst nix save
        this.createInventory();

        for (int i = 0; i < listnbt.size(); ++i) {
            CompoundTag compoundnbt = listnbt.getCompound(i);
            int j = compoundnbt.getByte("Slot") & 255;
            if (j < this.inventory.getContainerSize()) {
                //? if <1.21 {
                this.inventory.setItem(j, ItemStack.of(compoundnbt));
                //?} else {
                /*this.inventory.setItem(j, ItemStack.parse(this.registryAccess(), compoundnbt).orElse(ItemStack.EMPTY));
                *///?}
            }
        }

        for (EquipmentSlot slot : new EquipmentSlot[]{HEAD, CHEST, LEGS, FEET, OFFHAND, MAINHAND}) {
            this.inventory.setItem(this.getInventorySlotIndex(slot), this.getItemBySlot(slot));
        }
        int beforeItemSlot = nbt.getInt("BeforeItemSlot");
        this.setBeforeItemSlot(beforeItemSlot);
        if(getBeforeItemSlot() != -1) resetItemInHand();// fail-safe in case eating is interrupted
    }


    ////////////////////////////////////GET////////////////////////////////////

    public SimpleContainer getInventory() {
        return this.inventory;
    }

    public int getInventorySize() {
        return 15;
    }

    public int getInventoryColumns() {
        return 3;
    }

    public int getInventorySlotIndex(EquipmentSlot slot) {
        switch (slot) {
            case HEAD -> {
                return 0;
            }
            case CHEST -> {
                return 1;
            }
            case LEGS -> {
                return 2;
            }
            case FEET -> {
                return 3;
            }
            case OFFHAND -> {
                return 4;
            }
            case MAINHAND -> {
                return 5;
            }
        }
        return 6;
    }
    public EquipmentSlot getEquipmentSlotIndex(int id) {
        switch (id) {
            case 0 -> {return HEAD;}
            case 1 -> {return CHEST;}
            case 2 -> {return LEGS;}
            case 3 -> {return FEET;}
            case 4 -> {return OFFHAND;}
            case 5 -> {return MAINHAND;}
        }
        return null;
    }

    ////////////////////////////////////SET////////////////////////////////////

    public void setItemInHand(@NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
        if (hand == InteractionHand.MAIN_HAND) {
            this.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
            this.inventory.setItem(5, itemStack);//5 == MAINHAND
        } else {
            if (hand != InteractionHand.OFF_HAND) {
                throw new IllegalArgumentException("Invalid hand " + hand);
            }

            this.setItemSlot(EquipmentSlot.OFFHAND, itemStack);
            this.inventory.setItem(4, itemStack);//4 == MAINHAND
        }

    }

    @Override
    public void setItemSlot(@NotNull EquipmentSlot slotIn, @NotNull ItemStack stack) {
        super.setItemSlot(slotIn, stack);
        switch (slotIn) {
            case HEAD ->{
                if (this.inventory.getItem(0).isEmpty())
                    this.inventory.setItem(0, stack);
            }
            case CHEST-> {
                if (this.inventory.getItem(1).isEmpty())
                    this.inventory.setItem(1, stack);
            }
            case LEGS-> {
                if (this.inventory.getItem(2).isEmpty())
                    this.inventory.setItem(2, stack);
            }
            case FEET-> {
                if (this.inventory.getItem(3).isEmpty())
                    this.inventory.setItem(3, stack);
            }
            case OFFHAND-> {
                if (this.inventory.getItem(4).isEmpty())
                    this.inventory.setItem(4, stack);
            }
            case MAINHAND-> {
                if (this.inventory.getItem(5).isEmpty())
                    this.inventory.setItem(5, stack);
            }
        }
    }
    public @NotNull SlotAccess getSlot(int slot) {
        return slot == 499 ? new SlotAccess() {
            public ItemStack get() {
                return new ItemStack(Items.CHEST);
            }

            public boolean set(ItemStack stack) {
                if (stack.isEmpty()) {

                    AbstractInventoryEntity.this.createInventory();

                    return true;
                } else {
                    return false;
                }
            }
        } : super.getSlot(slot);
    }



    ////////////////////////////////////OTHER FUNCTIONS////////////////////////////////////

    public void onInventoryChanged(){}
    public void onItemStackAdded(ItemStack itemStack){}
    public void createInventory() {
        SimpleContainer inventory = this.inventory;
        this.inventory = new RecruitSimpleContainer(this.getInventorySize(), this){

        };
        if (inventory != null) {
            int i = Math.min(inventory.getContainerSize(), this.inventory.getContainerSize());

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setItem(j, itemstack.copy());
                }
            }
        }
    }

    public void die(DamageSource dmg) {
        super.die(dmg);

        boolean shouldUseCorpse = Main.isCorpseLoaded && !Main.isRPGZLoaded && !this.getCommandSenderWorld().isClientSide() && RecruitsServerConfig.CompatCorpseMod.get();
        if (shouldUseCorpse && PlatformHooks.INSTANCE.spawnCorpse(this)) {
            return;
        }

        if (this.getCommandSenderWorld().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            for (int i = 0; i < this.inventory.getContainerSize(); i++) {
                this.spawnAtLocation(this.inventory.getItem(i));// Containers.dropItemStack(this.getCommandSenderWorld(), getX(), getY(), getZ(), );
            }
        }
    }

    protected void pickUpItem(ItemEntity itemEntity) {
        ItemStack itemstack = itemEntity.getItem();

        if (this.canEquipItem(itemstack)) {
            this.equipItem(itemstack);
            this.onItemPickup(itemEntity);
            this.take(itemEntity, itemstack.getCount());
            itemEntity.discard();
        }
        else {
            RecruitSimpleContainer inventory = this.inventory;
            boolean flag = inventory.canAddItem(itemstack);
            if (!flag) {
                return;
            }
            this.onItemPickup(itemEntity);
            this.take(itemEntity, itemstack.getCount());
            ItemStack itemstack1 = inventory.addItem(itemstack);
            if (itemstack1.isEmpty()) {
                itemEntity.remove(RemovalReason.KILLED);
            } else {
                itemstack.setCount(itemstack1.getCount());
            }
        }
    }
    public void equipItem(ItemStack itemStack) {
        EquipmentSlot equipmentslot = getPreferredEquipmentSlot(itemStack);
        ItemStack currentArmor = this.getItemBySlot(equipmentslot);
        this.spawnAtLocation(currentArmor);
        this.setItemSlot(equipmentslot, itemStack);
        this.inventory.setItem(getInventorySlotIndex(equipmentslot), itemStack);
        Equipable equipable = Equipable.get(itemStack);
        if(equipable != null)
            this.getCommandSenderWorld().playSound(null, this.getX(), this.getY(), this.getZ(), equipable.getEquipSound(), this.getSoundSource(), 1.0F, 1.0F);
    }
    public boolean canEquipItem(@NotNull ItemStack itemStack) {
        if(!itemStack.isEmpty()) {
            EquipmentSlot equipmentslot = getPreferredEquipmentSlot(itemStack);
                ItemStack currentArmor = this.getItemBySlot(equipmentslot);
                boolean flag = this.canReplaceCurrentItem(itemStack, currentArmor);
                return flag && this.canHoldItem(itemStack);
        }
        return false;
    }

    public boolean hasSameTypeOfItem(ItemStack stack) {
        return this.inventoryItems().anyMatch(itemStack -> itemStack.getDescriptionId().equals(stack.getDescriptionId()));
    }
    @Nullable
    public ItemStack getMatchingItem(Predicate<ItemStack> predicate) {
        for (int i = 0; i < this.getInventory().getContainerSize(); i++) {
            ItemStack stack = this.getInventory().getItem(i);
            if (!stack.isEmpty() && predicate.test(stack)) {
                return stack;
            }
        }
        return null;
    }
    public boolean canEquipItemToSlot(@NotNull ItemStack itemStack, EquipmentSlot slot) {
        if(!itemStack.isEmpty()) {
            ItemStack currentArmor = this.getItemBySlot(slot);
            boolean flag = this.canReplaceCurrentItem(itemStack, currentArmor);

            return flag && this.canHoldItem(itemStack) && PlatformHooks.INSTANCE.canEquip(itemStack, slot, this);
        }
        return false;
    }


    @Override
    public boolean wantsToPickUp(@NotNull ItemStack itemStack){
       if (itemStack.getItem() instanceof ArmorItem){
           EquipmentSlot equipmentslot = getPreferredEquipmentSlot(itemStack);

           return this.getItemBySlot(equipmentslot).isEmpty() && !hasSameTypeOfItem(itemStack) && canEquipItem(itemStack);
       }
       else
           //? if <1.21 {
           return itemStack.isEdible();
           //?} else {
           /*return isFood(itemStack);
           *///?}
    }
    @NotNull
    public static EquipmentSlot getPreferredEquipmentSlot(ItemStack itemStack) {
        final EquipmentSlot slot = PlatformHooks.INSTANCE.getEquipmentSlot(itemStack);
        if (slot != null) return slot; // FORGE: Allow modders to set a non-default equipment slot for a stack; e.g. a non-armor chestplate-slot item
        Item item = itemStack.getItem();
        if (!itemStack.is(Items.CARVED_PUMPKIN) && (!(item instanceof BlockItem) || !(((BlockItem)item).getBlock() instanceof AbstractSkullBlock))) {
            if (item instanceof ArmorItem) {
                return ((ArmorItem)item).getEquipmentSlot();
            }
            else if (itemStack.is(Items.ELYTRA)) {
                return EquipmentSlot.CHEST;
            }
            else if(item instanceof SwordItem) {
                return EquipmentSlot.MAINHAND;
            }
            else {
                return PlatformHooks.INSTANCE.canBlockWithShield(itemStack)? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
            }
        } else {
            return EquipmentSlot.HEAD;
        }
    }

    @Override
    protected boolean canReplaceCurrentItem(@NotNull ItemStack replacer, ItemStack current) {
        if (current.isEmpty()) {
            return true;
        } else if (current.getItem() instanceof DiggerItem digger && replacer.getItem() instanceof SwordItem sword) {

            if (getAttackDamage(current) != getAttackDamage(replacer)) {
                return getAttackDamage(current) < getAttackDamage(replacer);
            }
            return this.canReplaceEqualItem(replacer, current);
        }

        else if (replacer.getItem() instanceof SwordItem) {
            if (!(current.getItem() instanceof SwordItem)) {
                return true;
            } else {
                if (getAttackDamage(replacer) != getAttackDamage(current)) {
                    return getAttackDamage(replacer) > getAttackDamage(current);
                } else {
                    return this.canReplaceEqualItem(replacer, current);
                }
            }
        }

        else if (replacer.getItem() instanceof BowItem && current.getItem() instanceof BowItem) {
            return this.canReplaceEqualItem(replacer, current);
        }

        else if (replacer.getItem() instanceof CrossbowItem && current.getItem() instanceof CrossbowItem) {
            return this.canReplaceEqualItem(replacer, current);
        }

        else if (replacer.getItem() instanceof ArmorItem) {
            if (hasBindingCurse(current)) {
                return false;
            } else if (!(current.getItem() instanceof ArmorItem)) {
                return true;
            } else {
                ArmorItem armoritem = (ArmorItem)replacer.getItem();
                ArmorItem armoritem1 = (ArmorItem)current.getItem();
                if (armoritem.getDefense() != armoritem1.getDefense()) {
                    return armoritem.getDefense() > armoritem1.getDefense();
                } else if (armoritem.getToughness() != armoritem1.getToughness()) {
                    return armoritem.getToughness() > armoritem1.getToughness();
                } else {
                    return this.canReplaceEqualItem(replacer, current);
                }
            }
        } else {
            if (replacer.getItem() instanceof DiggerItem) {
                if (current.getItem() instanceof BlockItem) {
                    return true;
                }

                if (current.getItem() instanceof DiggerItem) {
                    if (getAttackDamage(replacer) != getAttackDamage(current)) {
                        return getAttackDamage(replacer) > getAttackDamage(current);
                    }

                    return this.canReplaceEqualItem(replacer, current);
                }
            }

            return false;
        }
    }

    private static double getAttackDamage(ItemStack stack) {
        //? if <1.21 {
        if (stack.getItem() instanceof DiggerItem diggerItem) return diggerItem.getAttackDamage();
        if (stack.getItem() instanceof SwordItem swordItem) return swordItem.getDamage();
        return 0.0D;
        //?} else {
        /*return stack.getOrDefault(net.minecraft.core.component.DataComponents.ATTRIBUTE_MODIFIERS, net.minecraft.world.item.component.ItemAttributeModifiers.EMPTY).compute(0.0D, EquipmentSlot.MAINHAND);
        *///?}
    }

    private boolean hasBindingCurse(ItemStack stack) {
        //? if <1.21 {
        return EnchantmentHelper.hasBindingCurse(stack);
        //?} else {
        /*return EnchantmentHelper.getItemEnchantmentLevel(
                this.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.BINDING_CURSE), stack
        ) > 0;
        *///?}
    }

    private Stream<ItemStack> inventoryItems() {
        return IntStream.range(0, this.inventory.getContainerSize()).mapToObj(this.inventory::getItem);
    }

    protected boolean isFood(ItemStack stack) {
        //? if <1.21 {
        return stack.isEdible();
        //?} else {
        /*return stack.has(DataComponents.FOOD);
        *///?}
    }


    public abstract Predicate<ItemEntity> getAllowedItems();

    public abstract void openGUI(Player player);

    public void consumeArrow(){
        for(ItemStack itemStack : this.inventoryItems().toList()){
            if(itemStack.is(ItemTags.ARROWS)){
                itemStack.shrink(1);
                break;
            }
        }
    }

    public boolean canTakeArrows() {
        int count = 0;
        for(ItemStack itemstack : this.inventoryItems().toList()){
             if(itemstack.is(ItemTags.ARROWS)){
                 count += itemstack.getCount();
             }
        }

        return count < 32;
    }

    public boolean canTakeCannonBalls() {
        int count = 0;
        for(ItemStack itemstack : this.inventoryItems().toList()){
            if(itemstack.getDescriptionId().contains("cannon_ball")){
                count += itemstack.getCount();
            }
        }

        return count < 50;
    }

    public boolean canTakePlanks() {
        int count = 0;
        for(ItemStack itemstack : this.inventoryItems().toList()){
            if(itemstack.is(ItemTags.PLANKS)){
                count += itemstack.getCount();
            }
        }

        return count < 64;
    }

    public boolean canTakeIronNuggets() {
        int count = 0;
        for(ItemStack itemstack : this.inventoryItems().toList()){
            if(itemstack.is(Items.IRON_NUGGET)){
                count += itemstack.getCount();
            }
        }

        return count < 64;
    }

    public boolean canTakeCartridge() {
        int count = 0;
        for(ItemStack itemstack : this.inventoryItems().toList()){
            if(itemstack.getDescriptionId().contains("cartridge")){
                count += itemstack.getCount();
            }
        }

        return count < 32;
    }

    public void resetItemInHand() {
        //food is in offhand
        //before-item is in inventory slot

        //get OffhandItem (food)
        ItemStack foodStack = this.getOffhandItem().copy();
        //get Before Item from saved slot
        ItemStack beforeItem = this.inventory.getItem(getBeforeItemSlot()).copy();
        //remove item from this slot
        inventory.removeItemNoUpdate(getBeforeItemSlot());

        //remove offhand item
        this.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
        //this.inventory.setItem(4, ItemStack.EMPTY);//set hand slot empty

        //set before item in hand and slot
        this.setItemInHand(InteractionHand.OFF_HAND, beforeItem.copy());
        this.inventory.setItem(getBeforeItemSlot(), foodStack.copy());

        this.setBeforeItemSlot(-1); // means eating was successfully without interrupt
    }


    public boolean isPaymentInContainer(Container container){
        int amount = 0;
        for(int i = 0; i < container.getContainerSize(); i++) {
            ItemStack itemStack = container.getItem(i);
            if(itemStack.is(FactionEvents.getCurrency().getItem())){
                amount += itemStack.getCount();
            }
        }
        return amount >= RecruitsServerConfig.RecruitsPaymentAmount.get();
    }

    @Nullable
    public ItemStack getCurrencyFromInv(Container inv){
        ItemStack currency = null;
        for(int i = 0; i < inv.getContainerSize(); i++){
            ItemStack itemStack = inv.getItem(i);
            if(itemStack.is(FactionEvents.getCurrency().getItem())){
                currency = itemStack;
                break;
            }
        }
        return currency;
    }

    public void doPayment(Container container){
        int amount = RecruitsServerConfig.RecruitsPaymentAmount.get();
        for (int i = 0; i < amount; i++) {
            ItemStack currency = this.getCurrencyFromInv(container);
            if (currency != null) {
                currency.shrink(amount);
            }
        }
    }


    public void setBeforeItemSlot(int i) {
        beforeItemSlot = i;
    }

    public int getBeforeItemSlot(){
        return beforeItemSlot;
    }

    public void switchMainHandItem(Predicate<ItemStack> predicate) {
        if (!this.isAlive() || predicate == null) return;

        SimpleContainer inventory = this.getInventory();
        ItemStack mainHand = this.getMainHandItem();
        if (predicate.test(mainHand)) return;

        for (int i = 6; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (predicate.test(stack)) {

                inventory.setItem(i, mainHand);
                this.setItemInHand(InteractionHand.MAIN_HAND, stack);
                return;
            }
        }
    }
    public void switchOffHandItem(Predicate<ItemStack> predicate) {
        if (!this.isAlive() || predicate == null) return;

        SimpleContainer inventory = this.getInventory();
        ItemStack offHand = this.getOffhandItem();
        if (predicate.test(offHand)) return;

        for (int i = 6; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (predicate.test(stack)) {

                inventory.setItem(i, offHand);
                this.setItemInHand(InteractionHand.OFF_HAND, stack);
                return;
            }
        }
    }
}

package com.talhanation.recruits.items;

import com.talhanation.recruits.Main;
import com.talhanation.recruits.FactionEvents;
import com.talhanation.recruits.entities.ICompanion;
import com.talhanation.recruits.entities.IHasTargetPriority;
import net.minecraft.world.entity.player.Player;
import com.talhanation.recruits.RecruitEvents;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import com.talhanation.recruits.util.RecruitsItemStacks;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import net.minecraft.world.scores.PlayerTeam;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;


public class RecruitsSpawnEgg extends ArchitecturySpawnEggItem {
    private static final EquipmentSlot[] ARMOR_SLOTS = {EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD};
    private final RegistrySupplier<? extends EntityType<? extends AbstractRecruitEntity>> entityType;

    public RecruitsSpawnEgg(RegistrySupplier<? extends EntityType<? extends AbstractRecruitEntity>> entityType, int primaryColor, int secondaryColor, Properties properties) {
        super(entityType, primaryColor, secondaryColor, properties);
        this.entityType = entityType;
    }
    //? if <1.21 {
    @Override
    public @NotNull EntityType<?> getType(CompoundTag compound){
        if(compound != null && compound.contains("EntityTag", 10)) {
            CompoundTag entityTag = compound.getCompound("EntityTag");

            if(entityTag.contains("id", 8)) {
                return EntityType.byString(entityTag.getString("id")).orElse(this.entityType.get());
            }
        }
        return this.entityType.get();
    }
    //?}

    public @NotNull EntityType<?> getRecruitsType(ItemStack stack) {
        //? if <1.21 {
        return this.getType(stack.getTag());
        //?} else {
        /*CompoundTag entityData = RecruitsItemStacks.getEntityData(stack);
        return EntityType.by(entityData).orElse(this.entityType.get());
        *///?}
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!(context.getLevel() instanceof ServerLevel level)) return InteractionResult.SUCCESS;

        ItemStack stack = context.getItemInHand();
        CompoundTag entityTag = RecruitsItemStacks.getEntityData(stack);
        if (entityTag.isEmpty() || level.getBlockEntity(context.getClickedPos()) instanceof net.minecraft.world.level.block.entity.SpawnerBlockEntity) {
            return super.useOn(context);
        }

        BlockPos clickedPos = context.getClickedPos();
        BlockPos spawnPos = level.getBlockState(clickedPos).getCollisionShape(level, clickedPos).isEmpty()
                ? clickedPos : clickedPos.relative(context.getClickedFace());
        boolean offsetDown = !clickedPos.equals(spawnPos) && context.getClickedFace() == net.minecraft.core.Direction.UP;
        AbstractRecruitEntity recruit = spawnRecruitCopy(level, getRecruitsType(stack), entityTag, spawnPos, offsetDown);
        if (recruit == null) return InteractionResult.FAIL;

        if (context.getPlayer() == null || !context.getPlayer().isCreative()) stack.shrink(1);
        level.gameEvent(context.getPlayer(), net.minecraft.world.level.gameevent.GameEvent.ENTITY_PLACE, clickedPos);
        return InteractionResult.CONSUME;
    }

    @javax.annotation.Nullable
    public static AbstractRecruitEntity spawnRecruitCopy(ServerLevel level, EntityType<?> entityType,
                                                         CompoundTag entityTag, BlockPos spawnPos) {
        return spawnRecruitCopy(level, entityType, entityTag, spawnPos, false);
    }

    @javax.annotation.Nullable
    private static AbstractRecruitEntity spawnRecruitCopy(ServerLevel level, EntityType<?> entityType,
                                                          CompoundTag entityTag, BlockPos spawnPos, boolean offsetDown) {
        // Vanilla handles placement and finalizeSpawn; restore the template before world-join events.
        //? if <1.21 {
        Entity entity = entityType.create(level, null, null, spawnPos, MobSpawnType.SPAWN_EGG, true, offsetDown);
        //?} else {
        /*Entity entity = entityType.create(level, null, spawnPos, MobSpawnType.SPAWN_EGG, true, offsetDown);
        *///?}
        if (!(entity instanceof AbstractRecruitEntity recruit)) return null;
        fillRecruit(recruit, entityTag, spawnPos);
        if (!level.addFreshEntity(recruit)) return null;
        registerSpawnedCopy(level, recruit, entityTag);
        return recruit;
    }

    public static void fillRecruit(AbstractRecruitEntity recruit, CompoundTag entityTag, BlockPos pos){
        CompoundTag nbt = entityTag;

        if(nbt.isEmpty()) return;

        if (nbt.contains("CustomName", Tag.TAG_STRING)) {
            try {
                Component customName = com.talhanation.recruits.util.RecruitsComponents.fromJson(nbt.getString("CustomName"), recruit.level().registryAccess());
                if (customName != null) recruit.setCustomName(customName);
            } catch (RuntimeException exception) {
                Main.LOGGER.warn("Could not read copied recruit name", exception);
                if (nbt.contains("Name", Tag.TAG_STRING)) {
                    recruit.setCustomName(Component.literal(nbt.getString("Name")));
                }
            }
        } else if (nbt.contains("Name", Tag.TAG_STRING)) {
            recruit.setCustomName(Component.literal(nbt.getString("Name")));
        }
        if (nbt.contains("CustomNameVisible")) {
            recruit.setCustomNameVisible(nbt.getBoolean("CustomNameVisible"));
        }

        if (nbt.hasUUID("OwnerUUID")) {
            recruit.setOwnerUUID(Optional.of(nbt.getUUID("OwnerUUID")));
        } else {
            recruit.setOwnerUUID(Optional.empty());
        }
        recruit.setIsOwned(nbt.getBoolean("isOwned") && recruit.getOwnerUUID() != null);

        recruit.setXpLevel(nbt.getInt("Level"));
        recruit.setAggroState(nbt.getInt("AggroState"));
        recruit.setXp(nbt.getInt("Xp"));
        recruit.setKills(nbt.getInt("Kills"));
        recruit.setVariant(nbt.getInt("Variant"));
        recruit.setHunger(nbt.getFloat("Hunger"));
        recruit.setMoral(nbt.getFloat("Moral"));
        recruit.setCost(nbt.getInt("Cost"));
        recruit.setColor(nbt.getByte("Color"));
        recruit.setBiome(nbt.getByte("Biome"));
        if (nbt.contains("Attributes", Tag.TAG_LIST)) {
            recruit.getAttributes().load(nbt.getList("Attributes", Tag.TAG_COMPOUND));
        }

        if(nbt.contains("Group")) {
            Tag tag = nbt.get("Group");
            if (tag != null && tag.getId() == Tag.TAG_INT) {
                if(recruit.getOwner() != null){
                    int oldGroupIndex = nbt.getInt("Group");
                    RecruitEvents.handleGroupBackwardCompatibility(recruit, oldGroupIndex);
                }
                else recruit.setGroupUUID(null);
            } else if (nbt.hasUUID("Group")) {
                recruit.setGroupUUID(nbt.getUUID("Group"));
            }
        } else {
            recruit.setGroupUUID(null);
        }

        // States 4 and 5 depend on the old recruit's position or target.
        int followState = nbt.getInt("FollowState");
        if (followState == 4) followState = 3;
        if (followState == 5) followState = 2;
        recruit.setFollowState(followState);
        if (recruit.getShouldHoldPos()) recruit.setHoldPos(Vec3.atCenterOf(pos));

        recruit.setShouldMount(false);
        recruit.setMountUUID(Optional.empty());
        recruit.setShouldProtect(false);
        recruit.setProtectUUID(Optional.empty());
        recruit.setShouldMovePos(false);
        recruit.clearMovePos();
        recruit.setFleeing(false);
        recruit.setIsFollowing(false);
        recruit.setTarget(null);

        if (nbt.contains("ShouldBlock")) recruit.setShouldBlock(nbt.getBoolean("ShouldBlock"));
        if (nbt.contains("ShouldRest")) recruit.setShouldRest(nbt.getBoolean("ShouldRest"));
        if (nbt.contains("ShouldRanged")) recruit.setShouldRanged(nbt.getBoolean("ShouldRanged"));
        if (nbt.contains("Listen")) recruit.setListen(nbt.getBoolean("Listen"));

        if (nbt.hasUUID("UpkeepUUID")) {
            recruit.setUpkeepUUID(Optional.of(nbt.getUUID("UpkeepUUID")));
        } else {
            recruit.setUpkeepUUID(Optional.empty());
        }

        if (nbt.contains("UpkeepPosX") && nbt.contains("UpkeepPosY") && nbt.contains("UpkeepPosZ")) {
            recruit.setUpkeepPos(new BlockPos (
                    nbt.getInt("UpkeepPosX"),
                    nbt.getInt("UpkeepPosY"),
                    nbt.getInt("UpkeepPosZ")));
        }

        if (recruit instanceof ICompanion companion && nbt.contains("CompanionOwnerName")) {
            companion.setOwnerName(nbt.getString("CompanionOwnerName"));
        }
        if (recruit instanceof IHasTargetPriority priorityRecruit && nbt.contains("TargetPriority")) {
            try {
                priorityRecruit.setTargetPriority(IHasTargetPriority.TargetPriority.fromIndex(nbt.getInt("TargetPriority")));
            } catch (IllegalArgumentException ignored) {
                priorityRecruit.setTargetPriority(IHasTargetPriority.TargetPriority.CLOSEST);
            }
        }

        ListTag listnbt = nbt.getList("Items", 10);//muss 10 sein amk sonst nix save
        // finalizeSpawn can add default gear, the copied inventory replaces it.
        recruit.inventory.clearContent();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            recruit.setItemSlot(slot, ItemStack.EMPTY);
        }
        recruit.setPersistenceRequired();

        for (int i = 0; i < listnbt.size(); ++i) {
            CompoundTag compoundnbt = listnbt.getCompound(i);
            int j = compoundnbt.getByte("Slot") & 255;
            if (j < recruit.inventory.getContainerSize()) {
                recruit.inventory.setItem(j, RecruitsItemStacks.parse(compoundnbt, recruit.level().registryAccess()));
            }
        }

        ListTag armorItems = nbt.getList("ArmorItems", Tag.TAG_COMPOUND);
        for (int i = 0; i < armorItems.size() && i < 4; i++) {
            ItemStack item = RecruitsItemStacks.parse(armorItems.getCompound(i), recruit.level().registryAccess());
            if (!item.isEmpty()) {
                recruit.inventory.setItem(recruit.getInventorySlotIndex(ARMOR_SLOTS[i]), item);
            }
        }

        ListTag handItems = nbt.getList("HandItems", Tag.TAG_COMPOUND);
        for (int i = 0; i < handItems.size() && i < 2; i++) {
            ItemStack item = RecruitsItemStacks.parse(handItems.getCompound(i), recruit.level().registryAccess());
            if (!item.isEmpty()) recruit.inventory.setItem(i == 0 ? 5 : 4, item);
        }

        for (int slotIndex = 0; slotIndex <= 5; slotIndex++) {
            recruit.setItemSlot(recruit.getEquipmentSlotIndex(slotIndex), recruit.inventory.getItem(slotIndex));
        }

        recruit.setHealth(recruit.getMaxHealth());
    }

    private static void registerSpawnedCopy(ServerLevel level, AbstractRecruitEntity recruit, CompoundTag nbt) {
        if (nbt.contains("Team", Tag.TAG_STRING)) {
            String teamName = nbt.getString("Team");
            PlayerTeam team = level.getScoreboard().getPlayerTeam(teamName);
            if (team != null) {
                if (FactionEvents.recruitsFactionManager != null) {
                    FactionEvents.addRecruitToTeam(recruit, team, level);
                    if (FactionEvents.recruitsFactionManager.getFactionByStringID(teamName) != null) {
                        FactionEvents.addNPCToData(level, teamName, 1);
                    }
                } else {
                    level.getScoreboard().addPlayerToTeam(recruit.getStringUUID(), team);
                }
            } else {
                Main.LOGGER.warn("Unable to add copied recruit to missing team \"{}\"", teamName);
            }
        }

        if(recruit.getGroup() != null && RecruitEvents.recruitsGroupsManager != null){
            RecruitEvents.recruitsGroupsManager.addMember(recruit.getGroup(), recruit.getUUID(), level);
        }

        UUID ownerId = recruit.getOwnerUUID();
        if (recruit.isOwned() && ownerId != null && RecruitEvents.recruitsPlayerUnitManager != null) {
            RecruitEvents.recruitsPlayerUnitManager.addRecruits(ownerId, 1);
            Player owner = level.getPlayerByUUID(ownerId);
            if (owner != null) RecruitEvents.recruitsPlayerUnitManager.broadCastUnitInfoToPlayer(owner);
        }
    }
}

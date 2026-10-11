package com.talhanation.recruits.compat.corpse;

import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

/**
 * Builds the NBT of a Corpse mod death from a recruit, as Corpse only creates deaths from players.
 */
public final class RecruitCorpseDeath {

    private static final int HEAD_SLOT = 0;
    private static final int CHEST_SLOT = 1;
    private static final int LEGS_SLOT = 2;
    private static final int FEET_SLOT = 3;
    private static final int OFFHAND_SLOT = 4;
    private static final int MAINHAND_SLOT = 5;

    private static final int[] EQUIPMENT_SLOTS = {MAINHAND_SLOT, OFFHAND_SLOT, FEET_SLOT, LEGS_SLOT, CHEST_SLOT, HEAD_SLOT};
    private static final int[] MAIN_INVENTORY_SLOTS = {MAINHAND_SLOT, 6, 7, 8, 9, 10, 11, 12, 13, 14};
    private static final int[] ARMOR_SLOTS = {FEET_SLOT, LEGS_SLOT, CHEST_SLOT, HEAD_SLOT};
    private static final int[] OFFHAND_SLOTS = {OFFHAND_SLOT};

    private RecruitCorpseDeath() {
    }

    public static CompoundTag createDeathTag(AbstractRecruitEntity recruit) {
        CompoundTag nbt = new CompoundTag();

        nbt.putUUID("PlayerUuid", RecruitCorpseAppearance.encode(recruit, selectPoseType(recruit)));
        nbt.putString("PlayerName", recruit.getName().getString());
        writeEquipment(nbt, recruit);
        writeSlottedInventory(nbt, "MainInventory", recruit, MAIN_INVENTORY_SLOTS);
        writeSlottedInventory(nbt, "ArmorInventory", recruit, ARMOR_SLOTS);
        writeSlottedInventory(nbt, "OffHandInventory", recruit, OFFHAND_SLOTS);

        nbt.putLong("Timestamp", System.currentTimeMillis());
        nbt.putInt("Experience", 6);
        nbt.putDouble("PosX", recruit.getX());
        nbt.putDouble("PosY", recruit.getY());
        nbt.putDouble("PosZ", recruit.getZ());
        nbt.putString("Dimension", recruit.level().dimension().location().toString());
        nbt.putByte("Model", RecruitCorpseAppearance.FULL_PLAYER_MODEL);

        return nbt;
    }

    private static void writeEquipment(CompoundTag nbt, AbstractRecruitEntity recruit) {
        ListTag items = new ListTag();
        // Corpse reads equipment by list index instead of the Slot tag, so empty positions must be preserved.
        for (int slot : EQUIPMENT_SLOTS) {
            items.add(saveEquipmentItem(recruit, recruit.getInventory().getItem(slot)));
        }
        nbt.put("Equipment", items);
    }

    private static void writeSlottedInventory(CompoundTag nbt, String key, AbstractRecruitEntity recruit, int[] slots) {
        ListTag items = new ListTag();

        for (int index = 0; index < slots.length; index++) {
            int inventorySlot = slots[index];
            int corpseSlot = index;
            Optional<CompoundTag> itemTag = saveItem(recruit, recruit.getInventory().getItem(inventorySlot));
            itemTag.ifPresent(tag -> {
                tag.putInt("Slot", corpseSlot);
                items.add(tag);
            });
        }

        nbt.put(key, items);
    }

    private static Optional<CompoundTag> saveItem(AbstractRecruitEntity recruit, ItemStack stack) {
        if (stack.isEmpty()) {
            return Optional.empty();
        }

        //? if >=1.21 {
        /*CompoundTag itemTag = (CompoundTag) stack.save(recruit.registryAccess());
        *///?} else {
        CompoundTag itemTag = new CompoundTag();
        stack.save(itemTag);
        //?}
        if (!itemTag.contains("id")) {
            return Optional.empty();
        }
        return Optional.of(itemTag);
    }

    private static CompoundTag saveEquipmentItem(AbstractRecruitEntity recruit, ItemStack stack) {
        if (!stack.isEmpty()) {
            return saveItem(recruit, stack).orElseGet(RecruitCorpseDeath::emptyItemTag);
        }
        return emptyItemTag();
    }

    private static CompoundTag emptyItemTag() {
        CompoundTag itemTag = new CompoundTag();
        //? if <1.21 {
        itemTag.putString("id", "minecraft:air");
        itemTag.putByte("Count", (byte) 0);
        //?}
        return itemTag;
    }

    private static byte selectPoseType(AbstractRecruitEntity recruit) {
        int roll = recruit.getRandom().nextInt(100);
        if (roll < 40) {
            return 0;
        }
        return (byte) (roll < 70 ? 1 : 2);
    }
}

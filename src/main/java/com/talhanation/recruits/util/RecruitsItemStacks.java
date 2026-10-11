package com.talhanation.recruits.util;

import com.talhanation.recruits.platform.PlatformHooks;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
//? if >=1.21 {
/*import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
*///?}

public final class RecruitsItemStacks {

    private RecruitsItemStacks() {
    }

    public static CompoundTag save(ItemStack stack, HolderLookup.Provider registries) {
        //? if <1.21 {
        return stack.save(new CompoundTag());
        //?} else {
        /*return (CompoundTag) stack.saveOptional(registries);
        *///?}
    }

    public static void saveInto(ItemStack stack, CompoundTag tag, HolderLookup.Provider registries) {
        //? if <1.21 {
        stack.save(tag);
        //?} else {
        /*tag.merge((CompoundTag) stack.saveOptional(registries));
        *///?}
    }

    public static ItemStack parse(CompoundTag tag, HolderLookup.Provider registries) {
        //? if <1.21 {
        return ItemStack.of(tag);
        //?} else {
        /*if (!tag.contains("id", net.minecraft.nbt.Tag.TAG_STRING)) return ItemStack.EMPTY;
        if (tag.contains("Count", net.minecraft.nbt.Tag.TAG_BYTE) && tag.getByte("Count") <= 0) return ItemStack.EMPTY;
        tag = upgradeLegacyItem(tag);
        return ItemStack.parse(registries, tag).orElse(ItemStack.EMPTY);
        *///?}
    }

    public static CompoundTag upgradeLegacyItem(CompoundTag tag) {
        //? if >=1.21 {
        /*if (tag.contains("Count", net.minecraft.nbt.Tag.TAG_BYTE)) {
            // 3465 is Minecraft 1.20.1. Vanilla does not traverse item NBT inside mod SavedData or unknown entities.
            return (CompoundTag) net.minecraft.util.datafix.DataFixers.getDataFixer().update(
                    net.minecraft.util.datafix.fixes.References.ITEM_STACK,
                    new com.mojang.serialization.Dynamic<>(net.minecraft.nbt.NbtOps.INSTANCE, tag.copy()),
                    3465, net.minecraft.SharedConstants.getCurrentVersion().getDataVersion().getVersion()).getValue();
        }
        *///?}
        return tag;
    }

    public static boolean isSame(ItemStack first, ItemStack second) {
        //? if <1.21 {
        return ItemStack.isSameItemSameTags(first, second);
        //?} else {
        /*return ItemStack.isSameItemSameComponents(first, second);
        *///?}
    }

    public static CompoundTag getEntityData(ItemStack stack) {
        //? if <1.21 {
        CompoundTag tag = stack.getTag();
        return tag == null ? new CompoundTag() : tag.getCompound("EntityTag");
        //?} else {
        /*return stack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY).copyTag();
        *///?}
    }

    public static void setEntityData(ItemStack stack, CompoundTag entityData) {
        //? if <1.21 {
        CompoundTag itemTag = new CompoundTag();
        itemTag.put("EntityTag", entityData);
        stack.setTag(itemTag);
        //?} else {
        /*CustomData.set(DataComponents.ENTITY_DATA, stack, entityData);
        *///?}
    }

    public static int nutrition(ItemStack stack, LivingEntity entity) {
        //? if <1.21 {
        return PlatformHooks.INSTANCE.getFoodProperties(stack, entity).getNutrition();
        //?} else {
        /*return stack.getOrDefault(DataComponents.FOOD, new net.minecraft.world.food.FoodProperties(0, 0, false, 0, java.util.Optional.empty(), java.util.List.of())).nutrition();
        *///?}
    }

    public static float saturation(ItemStack stack, LivingEntity entity) {
        //? if <1.21 {
        return PlatformHooks.INSTANCE.getFoodProperties(stack, entity).getSaturationModifier();
        //?} else {
        /*return stack.getOrDefault(DataComponents.FOOD, new net.minecraft.world.food.FoodProperties(0, 0, false, 0, java.util.Optional.empty(), java.util.List.of())).saturation();
        *///?}
    }
}

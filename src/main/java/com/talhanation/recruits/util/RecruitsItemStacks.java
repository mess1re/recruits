package com.talhanation.recruits.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
//? if neoforge {
/*import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
*///?}

public final class RecruitsItemStacks {

    private RecruitsItemStacks() {
    }

    public static CompoundTag save(ItemStack stack, HolderLookup.Provider registries) {
        //? if forge {
        return stack.serializeNBT();
        //?} else {
        /*return (CompoundTag) stack.save(registries);
        *///?}
    }

    public static void saveInto(ItemStack stack, CompoundTag tag, HolderLookup.Provider registries) {
        //? if forge {
        stack.save(tag);
        //?} else {
        /*tag.merge((CompoundTag) stack.save(registries));
        *///?}
    }

    public static ItemStack parse(CompoundTag tag, HolderLookup.Provider registries) {
        //? if forge {
        return ItemStack.of(tag);
        //?} else {
        /*return ItemStack.parse(registries, tag).orElse(ItemStack.EMPTY);
        *///?}
    }

    public static boolean isSame(ItemStack first, ItemStack second) {
        //? if forge {
        return ItemStack.isSameItemSameTags(first, second);
        //?} else {
        /*return ItemStack.isSameItemSameComponents(first, second);
        *///?}
    }

    public static CompoundTag getEntityData(ItemStack stack) {
        //? if forge {
        CompoundTag tag = stack.getTag();
        return tag == null ? new CompoundTag() : tag.getCompound("EntityTag");
        //?} else {
        /*return stack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY).copyTag();
        *///?}
    }

    public static void setEntityData(ItemStack stack, CompoundTag entityData) {
        //? if forge {
        CompoundTag itemTag = new CompoundTag();
        itemTag.put("EntityTag", entityData);
        stack.setTag(itemTag);
        //?} else {
        /*CustomData.set(DataComponents.ENTITY_DATA, stack, entityData);
        *///?}
    }

    public static int nutrition(ItemStack stack, LivingEntity entity) {
        //? if forge {
        return stack.getItem().getFoodProperties(stack, entity).getNutrition();
        //?} else {
        /*return stack.getOrDefault(DataComponents.FOOD, new net.minecraft.world.food.FoodProperties(0, 0, false, 0, java.util.Optional.empty(), java.util.List.of())).nutrition();
        *///?}
    }

    public static float saturation(ItemStack stack, LivingEntity entity) {
        //? if forge {
        return stack.getItem().getFoodProperties(stack, entity).getSaturationModifier();
        //?} else {
        /*return stack.getOrDefault(DataComponents.FOOD, new net.minecraft.world.food.FoodProperties(0, 0, false, 0, java.util.Optional.empty(), java.util.List.of())).saturation();
        *///?}
    }
}

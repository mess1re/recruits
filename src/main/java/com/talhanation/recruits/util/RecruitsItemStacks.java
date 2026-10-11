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
        /*return (CompoundTag) stack.save(registries);
        *///?}
    }

    public static void saveInto(ItemStack stack, CompoundTag tag, HolderLookup.Provider registries) {
        //? if <1.21 {
        stack.save(tag);
        //?} else {
        /*tag.merge((CompoundTag) stack.save(registries));
        *///?}
    }

    public static ItemStack parse(CompoundTag tag, HolderLookup.Provider registries) {
        //? if <1.21 {
        return ItemStack.of(tag);
        //?} else {
        /*return ItemStack.parse(registries, tag).orElse(ItemStack.EMPTY);
        *///?}
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

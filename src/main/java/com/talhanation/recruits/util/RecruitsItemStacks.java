package com.talhanation.recruits.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

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
}

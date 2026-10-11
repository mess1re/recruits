package com.talhanation.recruits.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public final class RecruitsNetworkCodecs {

    private RecruitsNetworkCodecs() {
    }

    public static ItemStack readItem(FriendlyByteBuf buffer) {
        //? if <1.21 {
        return buffer.readItem();
        //?} else {
        /*return ItemStack.OPTIONAL_STREAM_CODEC.decode((net.minecraft.network.RegistryFriendlyByteBuf) buffer);
        *///?}
    }

    public static void writeItem(FriendlyByteBuf buffer, ItemStack stack) {
        //? if <1.21 {
        buffer.writeItem(stack);
        //?} else {
        /*ItemStack.OPTIONAL_STREAM_CODEC.encode((net.minecraft.network.RegistryFriendlyByteBuf) buffer, stack);
        *///?}
    }
}

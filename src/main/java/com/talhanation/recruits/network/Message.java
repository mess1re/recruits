package com.talhanation.recruits.network;

import net.minecraft.network.FriendlyByteBuf;

public interface Message<T extends Message<T>> {

    MessageSide getExecutingSide();

    default void executeServerSide(MessageContext context) {
    }

    default void executeClientSide(MessageContext context) {
    }

    T fromBytes(FriendlyByteBuf buf);

    void toBytes(FriendlyByteBuf buf);
}

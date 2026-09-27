package com.talhanation.recruits.network;

import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;

public final class MessageContext {

    @Nullable
    private final ServerPlayer sender;

    public MessageContext(@Nullable ServerPlayer sender) {
        this.sender = sender;
    }

    @Nullable
    public ServerPlayer getSender() {
        return sender;
    }
}

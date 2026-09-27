package com.talhanation.recruits.network;

import com.talhanation.recruits.CommandEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class MessageCommandScreen implements Message<MessageCommandScreen> {

    private UUID uuid;

    public MessageCommandScreen() {
        this.uuid = new UUID(0, 0);
    }

    public MessageCommandScreen(Player player) {
        this.uuid = player.getUUID();
    }

    @Override
    public MessageSide getExecutingSide() {
        return MessageSide.SERVER;
    }

    @Override
    public void executeServerSide(MessageContext context) {
        ServerPlayer player = context.getSender();
        if (!player.getUUID().equals(uuid)) {
            return;
        }
        CommandEvents.openCommandScreen(player);
    }

    @Override
    public MessageCommandScreen fromBytes(FriendlyByteBuf buf) {
        this.uuid = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

}
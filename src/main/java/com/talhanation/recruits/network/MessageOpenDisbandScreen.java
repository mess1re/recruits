package com.talhanation.recruits.network;

import com.talhanation.recruits.FactionEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class MessageOpenDisbandScreen implements Message<MessageOpenDisbandScreen> {

    private UUID player;
    private UUID recruit;

    public MessageOpenDisbandScreen() {
        this.player = new UUID(0, 0);
    }

    public MessageOpenDisbandScreen(Player player, UUID recruit) {
        this.player = player.getUUID();
        this.recruit = recruit;
    }

    @Override
    public MessageSide getExecutingSide() {
        return MessageSide.SERVER;
    }

    @Override
    public void executeServerSide(MessageContext context) {
        ServerPlayer player = context.getSender();
        if (!player.getUUID().equals(this.player)) {
            return;
        }
        FactionEvents.openDisbandingScreen(player, recruit);
    }

    @Override
    public MessageOpenDisbandScreen fromBytes(FriendlyByteBuf buf) {
        this.player = buf.readUUID();
        this.recruit= buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(player);
        buf.writeUUID(recruit);
    }

}
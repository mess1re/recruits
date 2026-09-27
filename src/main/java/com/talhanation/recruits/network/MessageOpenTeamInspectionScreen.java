package com.talhanation.recruits.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class MessageOpenTeamInspectionScreen implements Message<MessageOpenTeamInspectionScreen> {

    private UUID uuid;

    public MessageOpenTeamInspectionScreen() {
        this.uuid = new UUID(0, 0);
    }

    public MessageOpenTeamInspectionScreen(Player player) {
        this.uuid = player.getUUID();
    }

    @Override
    public MessageSide getExecutingSide() {
        return MessageSide.SERVER;
    }

    @Override
    public void executeServerSide(MessageContext context) {
        if (!context.getSender().getUUID().equals(uuid)) {
            return;
        }
        ServerPlayer player = context.getSender();
        //TeamEvents.openTeamInspectionScreen(player, player.getTeam());
    }

    @Override
    public MessageOpenTeamInspectionScreen fromBytes(FriendlyByteBuf buf) {
        this.uuid = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }
}

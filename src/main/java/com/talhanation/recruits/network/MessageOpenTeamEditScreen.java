package com.talhanation.recruits.network;

import com.talhanation.recruits.FactionEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class MessageOpenTeamEditScreen implements Message<MessageOpenTeamEditScreen> {

    private UUID uuid;

    public MessageOpenTeamEditScreen() {
        this.uuid = new UUID(0, 0);
    }

    public MessageOpenTeamEditScreen(Player player) {
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
        FactionEvents.openTeamEditScreen(player);
    }

    @Override
    public MessageOpenTeamEditScreen fromBytes(FriendlyByteBuf buf) {
        this.uuid = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }
}

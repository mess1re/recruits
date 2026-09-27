package com.talhanation.recruits.network;

import com.talhanation.recruits.world.RecruitsRoute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;
import java.util.UUID;

/**
 * Client → Server: player wants to transfer a route to another online player.
 * Server → forwards as {@link MessageToClientReceiveRoute} to the target player.
 */
public class MessageTransferRoute implements Message<MessageTransferRoute> {

    private UUID targetPlayerUUID;
    private CompoundTag routeNBT;

    public MessageTransferRoute() {}

    public MessageTransferRoute(UUID targetPlayerUUID, RecruitsRoute route) {
        this.targetPlayerUUID = targetPlayerUUID;
        this.routeNBT = route.toNBT();
    }

    @Override
    public MessageSide getExecutingSide() {
        return MessageSide.SERVER;
    }

    @Override
    public void executeServerSide(MessageContext context) {
        ServerPlayer sender = Objects.requireNonNull(context.getSender());
        RecruitsRoute route = RecruitsRoute.fromNBT(routeNBT);
        if (route == null) return;

        ServerPlayer target = sender.getServer().getPlayerList().getPlayer(targetPlayerUUID);
        if (target == null) return;
        RecruitsNetwork.sendToPlayer(target,
                new MessageToClientReceiveRoute(route.toNBT()));
    }

    @Override
    public MessageTransferRoute fromBytes(FriendlyByteBuf buf) {
        this.targetPlayerUUID = buf.readUUID();
        this.routeNBT = buf.readNbt();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(this.targetPlayerUUID);
        buf.writeNbt(this.routeNBT);
    }
}

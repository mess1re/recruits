package com.talhanation.recruits.network;

import com.talhanation.recruits.client.gui.PatrolLeaderScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

import java.util.List;


public class MessageToClientUpdateLeaderScreen implements Message<MessageToClientUpdateLeaderScreen> {
    public List<BlockPos> waypoints;
    public List<ItemStack> waypointItems;
    public int size;

    public MessageToClientUpdateLeaderScreen() {
    }

    public MessageToClientUpdateLeaderScreen(List<BlockPos> waypoints, List<ItemStack> waypointItems, int size) {
        this.waypoints = waypoints;
        this.waypointItems = waypointItems;
        this.size = size;
    }

    @Override
    public MessageSide getExecutingSide() {
        return MessageSide.CLIENT;
    }

    @Override
    public void executeClientSide(MessageContext context) {

    }

    @Override
    public MessageToClientUpdateLeaderScreen fromBytes(FriendlyByteBuf buf) {
        this.waypoints = buf.readList(buffer -> buffer.readBlockPos());
        this.waypointItems = buf.readList(RecruitsNetworkCodecs::readItem);
        this.size = buf.readInt();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeCollection(waypoints, (buffer, waypoint) -> buffer.writeBlockPos(waypoint));
        buf.writeCollection(waypointItems, RecruitsNetworkCodecs::writeItem);
        buf.writeInt(this.size);
    }
}


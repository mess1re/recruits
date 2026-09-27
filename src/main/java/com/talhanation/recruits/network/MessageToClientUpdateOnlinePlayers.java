package com.talhanation.recruits.network;

import com.talhanation.recruits.client.ClientManager;
import com.talhanation.recruits.world.RecruitsPlayerInfo;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;


public class MessageToClientUpdateOnlinePlayers implements Message<MessageToClientUpdateOnlinePlayers> {
    private CompoundTag nbt;

    public MessageToClientUpdateOnlinePlayers() {
    }

    public MessageToClientUpdateOnlinePlayers(List<RecruitsPlayerInfo> playerInfoList) {
        this.nbt = RecruitsPlayerInfo.toNBT(playerInfoList);
    }

    @Override
    public MessageSide getExecutingSide() {
        return MessageSide.CLIENT;
    }

    @Override
    public void executeClientSide(MessageContext context) {
        ClientManager.onlinePlayers = RecruitsPlayerInfo.getListFromNBT(nbt);
    }

    @Override
    public MessageToClientUpdateOnlinePlayers fromBytes(FriendlyByteBuf buf) {
        this.nbt = buf.readNbt();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(this.nbt);
    }

}
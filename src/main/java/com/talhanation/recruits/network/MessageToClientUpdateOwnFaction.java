package com.talhanation.recruits.network;

import com.talhanation.recruits.client.ClientManager;
import com.talhanation.recruits.world.RecruitsFaction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

import java.util.List;


public class MessageToClientUpdateOwnFaction implements Message<MessageToClientUpdateOwnFaction> {
    private CompoundTag nbt;
    public MessageToClientUpdateOwnFaction() {

    }

    public MessageToClientUpdateOwnFaction(RecruitsFaction ownFaction) {
        if(ownFaction == null) this.nbt = new CompoundTag();
        else this.nbt = ownFaction.toNBT();
    }

    @Override
    public MessageSide getExecutingSide() {
        return MessageSide.CLIENT;
    }

    @Override
    public void executeClientSide(MessageContext context) {
        if(nbt.isEmpty()){
            ClientManager.ownFaction = null;
            return;
        }
        ClientManager.ownFaction = RecruitsFaction.fromNBT(nbt);
    }

    @Override
    public MessageToClientUpdateOwnFaction fromBytes(FriendlyByteBuf buf) {
        this.nbt = buf.readNbt();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(this.nbt);
    }

}
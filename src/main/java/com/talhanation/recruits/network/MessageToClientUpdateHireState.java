package com.talhanation.recruits.network;

import com.talhanation.recruits.client.ClientManager;
import com.talhanation.recruits.config.RecruitsServerConfig;
import com.talhanation.recruits.util.RegistryUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;


public class MessageToClientUpdateHireState implements Message<MessageToClientUpdateHireState> {
    public ItemStack currency;
    public boolean canHire;
    public MessageToClientUpdateHireState() {
    }

    public MessageToClientUpdateHireState(boolean canHire) {
        this.canHire = canHire;
        this.currency = RegistryUtils.getItem(RecruitsServerConfig.RecruitCurrency.get())
                .map(Item::getDefaultInstance).orElseGet(Items.EMERALD::getDefaultInstance);
    }

    @Override
    public MessageSide getExecutingSide() {
        return MessageSide.CLIENT;
    }

    @Override
    public void executeClientSide(MessageContext context) {
        ClientManager.canPlayerHire = this.canHire;
        ClientManager.currency = this.currency;
    }

    @Override
    public MessageToClientUpdateHireState fromBytes(FriendlyByteBuf buf) {
        this.canHire = buf.readBoolean();
        this.currency = RecruitsNetworkCodecs.readItem(buf);
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(canHire);
        RecruitsNetworkCodecs.writeItem(buf, currency);
    }

}

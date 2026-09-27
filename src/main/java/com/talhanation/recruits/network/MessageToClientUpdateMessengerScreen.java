package com.talhanation.recruits.network;

import com.talhanation.recruits.client.gui.MessengerScreen;
import com.talhanation.recruits.world.RecruitsPlayerInfo;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;


public class MessageToClientUpdateMessengerScreen implements Message<MessageToClientUpdateMessengerScreen> {
    public String message;
    public CompoundTag nbt;
    public MessageToClientUpdateMessengerScreen() {
    }

    public MessageToClientUpdateMessengerScreen(String message, RecruitsPlayerInfo playerInfo) {
        this.message = message;

        if(playerInfo != null){
            this.nbt = playerInfo.toNBT();
        }
    }

    @Override
    public MessageSide getExecutingSide() {
        return MessageSide.CLIENT;
    }

    @Override
    public void executeClientSide(MessageContext context) {
        //MessengerScreen.message = this.message;

        if(nbt != null){
            MessengerScreen.playerInfo = RecruitsPlayerInfo.getFromNBT(nbt);
        }
    }

    @Override
    public MessageToClientUpdateMessengerScreen fromBytes(FriendlyByteBuf buf) {
        this.message = buf.readUtf();

        if(nbt != null){
            this.nbt = buf.readNbt();
        }

        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(message);

        if(nbt != null){
            buf.writeNbt(nbt);
        }
    }

}
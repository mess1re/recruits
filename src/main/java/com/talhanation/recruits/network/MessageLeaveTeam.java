package com.talhanation.recruits.network;

import com.talhanation.recruits.FactionEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class MessageLeaveTeam implements Message<MessageLeaveTeam> {

    public MessageLeaveTeam(){
    }

    public MessageSide getExecutingSide() {
        return MessageSide.SERVER;
    }

    public void executeServerSide(MessageContext context) {
        ServerPlayer player = context.getSender();
        ServerLevel level = player.serverLevel();
        FactionEvents.leaveTeam(false, player, null, level, false);
    }

    public MessageLeaveTeam fromBytes(FriendlyByteBuf buf) {
        return this;
    }

    public void toBytes(FriendlyByteBuf buf) {

    }
}

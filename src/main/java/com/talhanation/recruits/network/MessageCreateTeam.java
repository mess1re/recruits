package com.talhanation.recruits.network;

import com.talhanation.recruits.FactionEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class MessageCreateTeam implements Message<MessageCreateTeam> {

    private String teamName;
    private String displayName;
    private ChatFormatting color;
    private ItemStack banner;
    private int index;

    public MessageCreateTeam(){
    }

    public MessageCreateTeam(String name, String displayName, ItemStack banner, ChatFormatting color, int index) {
        this.teamName = name;
        this.displayName = displayName;
        this.banner = banner;
        this.color = color;
        this.index = index;
    }

    public MessageSide getExecutingSide() {
        return MessageSide.SERVER;
    }

    public void executeServerSide(MessageContext context) {
        ServerPlayer player = context.getSender();
        ServerLevel world = player.serverLevel();
        FactionEvents.createTeam(true, context.getSender(), world, this.teamName, this.displayName, player.getName().getString(), this.banner, this.color, (byte) index);
    }

    public MessageCreateTeam fromBytes(FriendlyByteBuf buf) {
        this.teamName = buf.readUtf();
        this.displayName = buf.readUtf();
        this.banner = RecruitsNetworkCodecs.readItem(buf);
        this.color = ChatFormatting.getById(buf.readInt());
        this.index = buf.readInt();
        return this;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.teamName);
        buf.writeUtf(this.displayName);
        RecruitsNetworkCodecs.writeItem(buf, this.banner);
        buf.writeInt(this.color.getId());
        buf.writeInt(this.index);
    }
}

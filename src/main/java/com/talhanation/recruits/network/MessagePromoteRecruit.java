package com.talhanation.recruits.network;

import com.talhanation.recruits.RecruitEvents;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MessagePromoteRecruit implements Message<MessagePromoteRecruit> {

    private UUID recruit;
    private int profession;
    private String name;
    public MessagePromoteRecruit(){
    }

    public MessagePromoteRecruit(UUID recruit, int profession, String name) {
        this.recruit = recruit;
        this.profession = profession;
        this.name = name;
    }

    public MessageSide getExecutingSide() {
        return MessageSide.SERVER;
    }

    public void executeServerSide(MessageContext context){
        Objects.requireNonNull(context.getSender()).getCommandSenderWorld().getEntitiesOfClass(
                AbstractRecruitEntity.class,
                context.getSender().getBoundingBox().inflate(16D),
                livingEntity -> livingEntity.getUUID().equals(this.recruit)
        ).forEach((recruit) -> RecruitEvents.promoteRecruit(recruit, profession, name, context.getSender()));

    }
    public MessagePromoteRecruit fromBytes(FriendlyByteBuf buf) {
        this.recruit = buf.readUUID();
        this.profession = buf.readInt();
        this.name = buf.readUtf();
        return this;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(recruit);
        buf.writeInt(profession);
        buf.writeUtf(name);
    }

}
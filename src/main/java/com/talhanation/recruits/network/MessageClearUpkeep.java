package com.talhanation.recruits.network;

import com.talhanation.recruits.CommandEvents;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MessageClearUpkeep implements Message<MessageClearUpkeep> {
    private UUID uuid;
    private UUID group;

    public MessageClearUpkeep() {
    }

    public MessageClearUpkeep(UUID uuid, UUID group) {
        this.uuid = uuid;
        this.group = group;
    }

    public MessageSide getExecutingSide() {
        return MessageSide.SERVER;
    }

    public void executeServerSide(MessageContext context) {
        Objects.requireNonNull(context.getSender()).getCommandSenderWorld().getEntitiesOfClass(
                AbstractRecruitEntity.class,
                context.getSender().getBoundingBox().inflate(100)
        ).forEach(
                (recruit) -> CommandEvents.onClearUpkeepButton(uuid, recruit, group)
        );
    }

    public MessageClearUpkeep fromBytes(FriendlyByteBuf buf) {
        this.uuid = buf.readUUID();
        this.group = buf.readUUID();
        return this;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeUUID(group);
    }
}


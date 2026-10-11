package com.talhanation.recruits.network;

import com.talhanation.recruits.CommandEvents;
import com.talhanation.recruits.RecruitEvents;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import com.talhanation.recruits.world.RecruitsGroup;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;
import java.util.UUID;

public class MessageHire implements Message<MessageHire> {

    private UUID player;
    private UUID recruit;
    private UUID groupUUID;

    public MessageHire() {
    }

    public MessageHire(UUID player, UUID recruit, UUID groupUUID) {
        this.player = player;
        this.recruit = recruit;
        this.groupUUID = groupUUID;
    }

    public MessageSide getExecutingSide()  {
        return MessageSide.SERVER;
    }

    public void executeServerSide(MessageContext context) {
        ServerPlayer player = Objects.requireNonNull(context.getSender());
        RecruitsGroup group = RecruitEvents.recruitsGroupsManager.getGroup(groupUUID);
        if (group == null || !player.getUUID().equals(group.getPlayerUUID())) return;
        player.getCommandSenderWorld().getEntitiesOfClass(
                AbstractRecruitEntity.class,
                player.getBoundingBox().inflate(16.0D),
                v -> v.getUUID().equals(this.recruit) && v.isAlive()
        ).forEach(recruit -> CommandEvents.handleRecruiting(player, group, recruit, true));
    }

    public MessageHire fromBytes(FriendlyByteBuf buf) {
        this.player = buf.readUUID();
        this.recruit = buf.readUUID();
        this.groupUUID = buf.readUUID();
        return this;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(this.player);
        buf.writeUUID(this.recruit);
        buf.writeUUID(this.groupUUID);
    }
}

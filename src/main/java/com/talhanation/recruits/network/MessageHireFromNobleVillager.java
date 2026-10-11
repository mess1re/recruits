package com.talhanation.recruits.network;

import com.talhanation.recruits.Main;
import com.talhanation.recruits.RecruitEvents;
import com.talhanation.recruits.VillagerEvents;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import com.talhanation.recruits.entities.VillagerNobleEntity;
import com.talhanation.recruits.world.RecruitsGroup;
import com.talhanation.recruits.world.RecruitsHireTrade;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class MessageHireFromNobleVillager implements Message<MessageHireFromNobleVillager> {
    private UUID nobleUUID;
    private UUID villagerUUID;
    private int cost;
    private boolean needsVillager;
    private boolean closing;
    private ResourceLocation resource;
    private UUID groupUUID;
    public MessageHireFromNobleVillager() {
    }

    public MessageHireFromNobleVillager(UUID nobleUUID, UUID villagerUUID, RecruitsHireTrade trade, RecruitsGroup group, boolean needsVillager, boolean closing) {
        this.nobleUUID = nobleUUID;
        this.villagerUUID = villagerUUID;
        this.groupUUID = group == null ? new UUID(0, 0) : group.getUUID();
        if(trade != null){
            this.cost = trade.cost;
            this.resource = trade.resourceLocation;
        }
        else{
            this.cost = 0;
            this.resource = new ResourceLocation(Main.MOD_ID, "none");
        }

        this.needsVillager = needsVillager;
        this.closing = closing;
    }

    public MessageSide getExecutingSide() {
        return MessageSide.SERVER;
    }

    public void executeServerSide(MessageContext context) {
        ServerPlayer player = Objects.requireNonNull(context.getSender());
        ServerLevel serverLevel = player.serverLevel();
        VillagerNobleEntity villagerNoble = player.getCommandSenderWorld().getEntitiesOfClass(
                VillagerNobleEntity.class,
                player.getBoundingBox().inflate(32.0D),
                noble -> noble.getUUID().equals(this.nobleUUID) && noble.isAlive()
        ).stream().findAny().orElse(null);

        if (villagerNoble == null) return;

        if(closing){
            villagerNoble.isTrading(false);
            return;
        }

        RecruitsGroup group = RecruitEvents.recruitsGroupsManager.getGroup(groupUUID);
        if (group == null || !player.getUUID().equals(group.getPlayerUUID())) return;

        if(this.needsVillager){
            player.getCommandSenderWorld().getEntitiesOfClass(
                    Villager.class,
                    player.getBoundingBox().inflate(32.0D),
                    villager -> villager.getUUID().equals(this.villagerUUID) && villager.isAlive()
            ).forEach(villager -> this.createRecruit(serverLevel, villager, villagerNoble, player, group));
        }
        else{
            String string = resource.toString();
            Optional<EntityType<?>> optionalType = EntityType.byString(string);
            optionalType.ifPresent(type -> VillagerEvents.spawnHiredRecruit(serverLevel, (EntityType<? extends AbstractRecruitEntity>) type, player, group));

            villagerNoble.doTrade(resource);
        }

        String stringID = player.getTeam() != null ? player.getTeam().getName() : "";
        boolean canHire = RecruitEvents.recruitsPlayerUnitManager.canPlayerRecruit(stringID, player.getUUID());
        RecruitsNetwork.sendToPlayer(()-> player, new MessageToClientUpdateHireState(canHire));
    }
    public void createRecruit(ServerLevel serverLevel, Villager villager, VillagerNobleEntity villagerNoble, Player player, RecruitsGroup group){
        String string = resource.toString();
        Optional<EntityType<?>> optionalType = EntityType.byString(string);

        optionalType.ifPresent(type -> {
            VillagerEvents.createHiredRecruitFromVillager(serverLevel, villager, (EntityType<? extends AbstractRecruitEntity>) type, player, group);
        });

        villagerNoble.doTrade(resource);
    }

    public MessageHireFromNobleVillager fromBytes(FriendlyByteBuf buf) {
        this.nobleUUID = buf.readUUID();
        this.villagerUUID = buf.readUUID();
        this.cost = buf.readInt();
        this.resource = buf.readResourceLocation();
        this.needsVillager = buf.readBoolean();
        this.closing = buf.readBoolean();
        this.groupUUID = buf.readUUID();
        return this;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(this.nobleUUID);
        buf.writeUUID(this.villagerUUID);
        buf.writeInt(this.cost);
        buf.writeResourceLocation(resource);
        buf.writeBoolean(needsVillager);
        buf.writeBoolean(closing);
        buf.writeUUID(this.groupUUID);
    }
}

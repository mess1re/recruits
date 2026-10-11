package com.talhanation.recruits.init;

import com.talhanation.recruits.Main;
import net.minecraft.core.registries.Registries;
import com.talhanation.recruits.entities.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;

import static com.talhanation.recruits.util.RecruitsEntityTypes.humanoid;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Main.MOD_ID, Registries.ENTITY_TYPE);


    public static final RegistrySupplier<EntityType<RecruitEntity>> RECRUIT = ENTITY_TYPES.register("recruit",
            () -> humanoid(RecruitEntity::new)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "recruit").toString()));

    public static final RegistrySupplier<EntityType<RecruitShieldmanEntity>> RECRUIT_SHIELDMAN = ENTITY_TYPES.register("recruit_shieldman",
            () -> humanoid(RecruitShieldmanEntity::new)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "recruit_shield").toString()));

    public static final RegistrySupplier<EntityType<BowmanEntity>> BOWMAN = ENTITY_TYPES.register("bowman",
            () -> humanoid(BowmanEntity::new)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "bowman").toString()));

    public static final RegistrySupplier<EntityType<CrossBowmanEntity>> CROSSBOWMAN = ENTITY_TYPES.register("crossbowman",
            () -> humanoid(CrossBowmanEntity::new)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "crossbowman").toString()));
    public static final RegistrySupplier<EntityType<NomadEntity>> NOMAD = ENTITY_TYPES.register("nomad",
            () -> humanoid(NomadEntity::new)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "nomad").toString()));

    public static final RegistrySupplier<EntityType<HorsemanEntity>> HORSEMAN = ENTITY_TYPES.register("horseman",
            () -> humanoid(HorsemanEntity::new)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "horseman").toString()));

    public static final RegistrySupplier<EntityType<MessengerEntity>> MESSENGER = ENTITY_TYPES.register("messenger",
            () -> humanoid(MessengerEntity::new)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "messenger").toString()));

    public static final RegistrySupplier<EntityType<ScoutEntity>> SCOUT = ENTITY_TYPES.register("scout",
            () -> humanoid(ScoutEntity::new)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "scout").toString()));

    public static final RegistrySupplier<EntityType<CommanderEntity>> PATROL_LEADER = ENTITY_TYPES.register("patrol_leader",
            () -> humanoid(CommanderEntity::new)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "patrol_leader").toString()));

    public static final RegistrySupplier<EntityType<CaptainEntity>> CAPTAIN = ENTITY_TYPES.register("captain",
            () -> humanoid(CaptainEntity::new)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "captain").toString()));

    public static final RegistrySupplier<EntityType<VillagerNobleEntity>> VILLAGER_NOBLE = ENTITY_TYPES.register("villager_noble",
            () -> humanoid(VillagerNobleEntity::new)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "villager_noble").toString()));

    public static final RegistrySupplier<EntityType<SiegeEngineerEntity>> SIEGE_ENGINEER = ENTITY_TYPES.register("siege_engineer",
            () -> humanoid(SiegeEngineerEntity::new)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "siege_engineer").toString()));


    /*
    public static final RegistrySupplier<EntityType<AssassinEntity>> ASSASSIN = ENTITY_TYPES.register("assassin",
            () -> humanoid(AssassinEntity::new, 0.9F)
                    .canSpawnFarFromPlayer()
                    .setTrackingRange(32)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "assassin").toString()));

    public static final RegistrySupplier<EntityType<AssassinLeaderEntity>> ASSASSIN_LEADER = ENTITY_TYPES.register("assassin_leader",
            () -> humanoid(AssassinLeaderEntity::new, 0.9F)
                    .canSpawnFarFromPlayer()
                    .setTrackingRange(32)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "assassin_leader").toString()));

     */

}

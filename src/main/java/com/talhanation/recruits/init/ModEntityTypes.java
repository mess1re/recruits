package com.talhanation.recruits.init;

import com.talhanation.recruits.Main;
import net.minecraft.core.registries.Registries;
import com.talhanation.recruits.entities.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Main.MOD_ID, Registries.ENTITY_TYPE);


    public static final RegistrySupplier<EntityType<RecruitEntity>> RECRUIT = ENTITY_TYPES.register("recruit",
            () -> EntityType.Builder.of(RecruitEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "recruit").toString()));

    public static final RegistrySupplier<EntityType<RecruitShieldmanEntity>> RECRUIT_SHIELDMAN = ENTITY_TYPES.register("recruit_shieldman",
            () -> EntityType.Builder.of(RecruitShieldmanEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "recruit_shield").toString()));

    public static final RegistrySupplier<EntityType<BowmanEntity>> BOWMAN = ENTITY_TYPES.register("bowman",
            () -> EntityType.Builder.of(BowmanEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "bowman").toString()));

    public static final RegistrySupplier<EntityType<CrossBowmanEntity>> CROSSBOWMAN = ENTITY_TYPES.register("crossbowman",
            () -> EntityType.Builder.of(CrossBowmanEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "crossbowman").toString()));
    public static final RegistrySupplier<EntityType<NomadEntity>> NOMAD = ENTITY_TYPES.register("nomad",
            () -> EntityType.Builder.of(NomadEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "nomad").toString()));

    public static final RegistrySupplier<EntityType<HorsemanEntity>> HORSEMAN = ENTITY_TYPES.register("horseman",
            () -> EntityType.Builder.of(HorsemanEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "horseman").toString()));

    public static final RegistrySupplier<EntityType<MessengerEntity>> MESSENGER = ENTITY_TYPES.register("messenger",
            () -> EntityType.Builder.of(MessengerEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "messenger").toString()));

    public static final RegistrySupplier<EntityType<ScoutEntity>> SCOUT = ENTITY_TYPES.register("scout",
            () -> EntityType.Builder.of(ScoutEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "scout").toString()));

    public static final RegistrySupplier<EntityType<CommanderEntity>> PATROL_LEADER = ENTITY_TYPES.register("patrol_leader",
            () -> EntityType.Builder.of(CommanderEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "patrol_leader").toString()));

    public static final RegistrySupplier<EntityType<CaptainEntity>> CAPTAIN = ENTITY_TYPES.register("captain",
            () -> EntityType.Builder.of(CaptainEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "captain").toString()));

    public static final RegistrySupplier<EntityType<VillagerNobleEntity>> VILLAGER_NOBLE = ENTITY_TYPES.register("villager_noble",
            () -> EntityType.Builder.of(VillagerNobleEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "villager_noble").toString()));

    public static final RegistrySupplier<EntityType<SiegeEngineerEntity>> SIEGE_ENGINEER = ENTITY_TYPES.register("siege_engineer",
            () -> EntityType.Builder.of(SiegeEngineerEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(Main.MOD_ID, "siege_engineer").toString()));


    /*
    public static final RegistrySupplier<EntityType<AssassinEntity>> ASSASSIN = ENTITY_TYPES.register("assassin",
            () -> EntityType.Builder.of(AssassinEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .canSpawnFarFromPlayer()
                    .setTrackingRange(32)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "assassin").toString()));

    public static final RegistrySupplier<EntityType<AssassinLeaderEntity>> ASSASSIN_LEADER = ENTITY_TYPES.register("assassin_leader",
            () -> EntityType.Builder.of(AssassinLeaderEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .canSpawnFarFromPlayer()
                    .setTrackingRange(32)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "assassin_leader").toString()));

     */

}

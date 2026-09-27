package com.talhanation.recruits.init;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableSet;
import com.talhanation.recruits.Main;
import net.minecraft.core.registries.Registries;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;


public class ModProfessions {
    private static final Logger logger = LogManager.getLogger(Main.MOD_ID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS =
            DeferredRegister.create(Main.MOD_ID, Registries.VILLAGER_PROFESSION);
    public static final RegistrySupplier<VillagerProfession> RECRUIT = makeProfession("recruit", ModPois.POI_RECRUIT);
    public static final RegistrySupplier<VillagerProfession> SHIELDMAN = makeProfession("shieldman", ModPois.POI_SHIELDMAN);
    public static final RegistrySupplier<VillagerProfession> BOWMAN = makeProfession("bowman", ModPois.POI_BOWMAN);

    public static final RegistrySupplier<VillagerProfession> CROSSBOWMAN = makeProfession("crossbowman", ModPois.POI_CROSSBOWMAN);

    public static final RegistrySupplier<VillagerProfession> HORSEMAN = makeProfession("horseman", ModPois.POI_HORSEMAN);

    public static final RegistrySupplier<VillagerProfession> NOMAD = makeProfession("nomad", ModPois.POI_NOMAD);

    private static RegistrySupplier<VillagerProfession> makeProfession(String name, RegistrySupplier<PoiType> pointOfInterest) {
        logger.info("Registering profession for {} with POI {}", name, pointOfInterest);
        return PROFESSIONS.register(name,
                () -> new VillagerProfession(name, poi -> poi.get().equals(pointOfInterest.get()),
                        poi -> poi.get().equals(pointOfInterest.get()), ImmutableSet.of(), ImmutableSet.of(),
                        SoundEvents.VILLAGER_CELEBRATE));
    }
}

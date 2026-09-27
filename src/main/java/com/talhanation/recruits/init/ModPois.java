package com.talhanation.recruits.init;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableSet;
import com.talhanation.recruits.Main;
import net.minecraft.core.registries.Registries;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;


public class ModPois {
    private static final Logger logger = LogManager.getLogger(Main.MOD_ID);
    public static final DeferredRegister<PoiType> POIS =
            DeferredRegister.create(Main.MOD_ID, Registries.POINT_OF_INTEREST_TYPE);

    public static final RegistrySupplier<PoiType> POI_RECRUIT = makePoi("recruit", ModBlocks.RECRUIT_BLOCK);
    public static final RegistrySupplier<PoiType> POI_SHIELDMAN = makePoi("shieldman", ModBlocks.RECRUIT_SHIELD_BLOCK);
    public static final RegistrySupplier<PoiType> POI_BOWMAN = makePoi("bowman", ModBlocks.BOWMAN_BLOCK);

    public static final RegistrySupplier<PoiType> POI_CROSSBOWMAN = makePoi("crossbowman", ModBlocks.CROSSBOWMAN_BLOCK);

    public static final RegistrySupplier<PoiType> POI_HORSEMAN = makePoi("horseman", ModBlocks.HORSEMAN_BLOCK);

    public static final RegistrySupplier<PoiType> POI_NOMAD = makePoi("nomad", ModBlocks.NOMAD_BLOCK);

    private static RegistrySupplier<PoiType> makePoi(String name, RegistrySupplier<Block> block) {
        logger.info("Registering POI for " + block.getKey().toString());
        return POIS.register(name, () -> {
            Set<BlockState> blockStates = ImmutableSet.copyOf(block.get().getStateDefinition().getPossibleStates());
            return new PoiType(blockStates, 1, 1);
        });
    }
}

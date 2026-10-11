package com.talhanation.recruits.init;

import com.talhanation.recruits.Main;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Main.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> RECRUIT_BLOCK = BLOCKS.register("recruit_block",
            () -> new Block(copyOf(Blocks.FLETCHING_TABLE)));

    public static final RegistrySupplier<Block> BOWMAN_BLOCK = BLOCKS.register("bowman_block",
            () -> new Block(copyOf(Blocks.FLETCHING_TABLE)));

    public static final RegistrySupplier<Block> NOMAD_BLOCK = BLOCKS.register("nomad_block",
            () -> new Block(copyOf(Blocks.FLETCHING_TABLE)));

    public static final RegistrySupplier<Block> CROSSBOWMAN_BLOCK = BLOCKS.register("crossbowman_block",
            () -> new Block(copyOf(Blocks.FLETCHING_TABLE)));

    public static final RegistrySupplier<Block> HORSEMAN_BLOCK = BLOCKS.register("horseman_block",
            () -> new Block(copyOf(Blocks.FLETCHING_TABLE)));

    public static final RegistrySupplier<Block> RECRUIT_SHIELD_BLOCK = BLOCKS.register("recruit_shield_block",
            () -> new Block(copyOf(Blocks.FLETCHING_TABLE)));

    private static BlockBehaviour.Properties copyOf(Block block) {
        //? if <1.21 {
        return BlockBehaviour.Properties.copy(block);
        //?} else {
        /*return BlockBehaviour.Properties.ofFullCopy(block);
        *///?}
    }

}

package com.talhanation.recruits.init;

import com.talhanation.recruits.Main;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ModTags {

    // Each tag includes the matching forge: and c: conventional tags of all loaders.
    public static class Biomes {
        public static final TagKey<Biome> IS_DESERT = tag("is_desert");
        public static final TagKey<Biome> IS_SANDY = tag("is_sandy");
        public static final TagKey<Biome> IS_WET_OVERWORLD = tag("is_wet_overworld");
        public static final TagKey<Biome> IS_CONIFEROUS = tag("is_coniferous");
        public static final TagKey<Biome> IS_COLD_OVERWORLD = tag("is_cold_overworld");
        public static final TagKey<Biome> IS_SNOWY = tag("is_snowy");
        public static final TagKey<Biome> IS_SWAMP = tag("is_swamp");
        public static final TagKey<Biome> IS_HOT_OVERWORLD = tag("is_hot_overworld");
        public static final TagKey<Biome> IS_SPARSE_OVERWORLD = tag("is_sparse_overworld");

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, new ResourceLocation(Main.MOD_ID, name));
        }
    }
}

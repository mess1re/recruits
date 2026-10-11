package com.talhanation.recruits.init;

import com.google.common.collect.Lists;
import com.talhanation.recruits.Main;
import net.minecraft.core.registries.Registries;
import com.talhanation.recruits.items.RecruitsSpawnEgg;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;

import java.util.List;

import static com.talhanation.recruits.util.RegistryUtils.createSpawnEggItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Main.MOD_ID, Registries.ITEM);
    public static final List<RegistrySupplier<SpawnEggItem>> SPAWN_EGGS = Lists.newArrayList();

    public static final RegistrySupplier<SpawnEggItem> RECRUIT_SPAWN_EGG = createSpawnEggItem("recruit", ModEntityTypes.RECRUIT, 16755200, 16777045);
    public static final RegistrySupplier<SpawnEggItem> BOWMAN_SPAWN_EGG = createSpawnEggItem("bowman", ModEntityTypes.BOWMAN, 16755200, 16777045);
    public static final RegistrySupplier<SpawnEggItem> NOMAD_SPAWN_EGG = createSpawnEggItem("nomad", ModEntityTypes.NOMAD, 16755200, 16777045);
    public static final RegistrySupplier<SpawnEggItem> RECRUIT_SHIELD_SPAWN_EGG = createSpawnEggItem("recruit_shieldman", ModEntityTypes.RECRUIT_SHIELDMAN, 16755200, 16777045);
    public static final RegistrySupplier<SpawnEggItem> HORSEMAN_SPAWN_EGG = createSpawnEggItem("horseman", ModEntityTypes.HORSEMAN, 16755200, 16777045);
    public static final RegistrySupplier<SpawnEggItem> CROSSBOWMAN_SPAWN_EGG = createSpawnEggItem("crossbowman", ModEntityTypes.CROSSBOWMAN, 16755200, 16777045);
    public static final RegistrySupplier<SpawnEggItem> VILLAGER_NOBLE_SPAWN_EGG = createSpawnEggItem("villager_noble", ModEntityTypes.VILLAGER_NOBLE, 16755200, 16777045);

    public static final RegistrySupplier<BlockItem> RECRUIT_BLOCK = ITEMS.register("recruit_block", () -> new BlockItem(ModBlocks.RECRUIT_BLOCK.get(), new Item.Properties()));
    public static final RegistrySupplier<BlockItem> BOWMAN_BLOCK = ITEMS.register("bowman_block", () -> new BlockItem(ModBlocks.BOWMAN_BLOCK.get(), new Item.Properties()));
    public static final RegistrySupplier<BlockItem> RECRUIT_SHIELD_BLOCK = ITEMS.register("recruit_shield_block", () -> new BlockItem(ModBlocks.RECRUIT_SHIELD_BLOCK.get(), new Item.Properties()));
    public static final RegistrySupplier<BlockItem> CROSSBOWMAN_BLOCK = ITEMS.register("crossbowman_block", () -> new BlockItem(ModBlocks.CROSSBOWMAN_BLOCK.get(), new Item.Properties()));
    public static final RegistrySupplier<BlockItem> HORSEMAN_BLOCK = ITEMS.register("horseman_block", () -> new BlockItem(ModBlocks.HORSEMAN_BLOCK.get(), new Item.Properties()));
    public static final RegistrySupplier<BlockItem> NOMAD_BLOCK = ITEMS.register("nomad_block", () -> new BlockItem(ModBlocks.NOMAD_BLOCK.get(), new Item.Properties()));

    //public static final RegistrySupplier<Item> HELD_BANNER_ITEM = ITEMS.register("held_banner_item",() -> new HeldBannerItem((new Item.Properties()).tab(ItemGroup.TAB_COMBAT)));
}

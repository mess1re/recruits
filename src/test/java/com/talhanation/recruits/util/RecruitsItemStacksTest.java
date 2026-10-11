package com.talhanation.recruits.util;

import net.minecraft.SharedConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
//? if >=1.21 {
/*import net.minecraft.core.component.DataComponents;
*///?}

import static org.junit.jupiter.api.Assertions.*;

class RecruitsItemStacksTest {
    private static HolderLookup.Provider registries;

    @BeforeAll
    static void bootstrap() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        registries = VanillaRegistries.createLookup();
    }

    @Test
    void inventoryEntryRetainsItemAndSlot() {
        CompoundTag tag = new CompoundTag();
        tag.putByte("Slot", (byte) 5);
        RecruitsItemStacks.saveInto(new ItemStack(Items.DIAMOND, 3), tag, registries);
        assertEquals(5, tag.getByte("Slot"));
        ItemStack restored = RecruitsItemStacks.parse(tag, registries);
        assertTrue(restored.is(Items.DIAMOND));
        assertEquals(3, restored.getCount());
    }

    @Test
    void emptyItemRoundTrips() {
        assertTrue(RecruitsItemStacks.parse(RecruitsItemStacks.save(ItemStack.EMPTY, registries), registries).isEmpty());
        CompoundTag brokenLegacyEntry = new CompoundTag();
        brokenLegacyEntry.putByte("Slot", (byte) 5);
        assertTrue(RecruitsItemStacks.parse(brokenLegacyEntry, registries).isEmpty());
    }

    @Test
    void legacyFactionBannerRetainsItsPattern() {
        CompoundTag banner = new CompoundTag();
        banner.putString("id", "minecraft:green_banner");
        banner.putByte("Count", (byte) 1);
        CompoundTag pattern = new CompoundTag();
        pattern.putString("Pattern", "cr");
        pattern.putInt("Color", 14);
        ListTag patterns = new ListTag();
        patterns.add(pattern);
        CompoundTag blockEntityTag = new CompoundTag();
        blockEntityTag.put("Patterns", patterns);
        CompoundTag itemTag = new CompoundTag();
        itemTag.put("BlockEntityTag", blockEntityTag);
        banner.put("tag", itemTag);

        ItemStack restored = RecruitsItemStacks.parse(banner, registries);
        assertTrue(restored.is(Items.GREEN_BANNER));
        //? if >=1.21 {
        /*var layers = restored.get(DataComponents.BANNER_PATTERNS).layers();
        assertEquals(1, layers.size());
        assertEquals(net.minecraft.world.item.DyeColor.RED, layers.getFirst().color());
        assertEquals("minecraft:cross", layers.getFirst().pattern().unwrapKey().orElseThrow().location().toString());
        *///?} else {
        assertEquals(1, restored.getTag().getCompound("BlockEntityTag").getList("Patterns", 10).size());
        //?}
        assertTrue(banner.contains("Count"), "Parsing must not mutate the source NBT");
    }

    @Test
    void spawnEggEntityDataCanBeSavedInAPlayerInventory() {
        ItemStack egg = new ItemStack(Items.VILLAGER_SPAWN_EGG);
        CompoundTag entity = new CompoundTag();
        entity.putString("id", "minecraft:villager");
        entity.putInt("Xp", 12);
        RecruitsItemStacks.setEntityData(egg, entity);
        ItemStack restored = RecruitsItemStacks.parse(RecruitsItemStacks.save(egg, registries), registries);
        assertTrue(restored.is(Items.VILLAGER_SPAWN_EGG));
        assertEquals("minecraft:villager", RecruitsItemStacks.getEntityData(restored).getString("id"));
        assertEquals(12, RecruitsItemStacks.getEntityData(restored).getInt("Xp"));
    }
}

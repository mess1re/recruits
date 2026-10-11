package com.talhanation.recruits.items;

import com.talhanation.recruits.entities.AbstractRecruitEntity;
import com.talhanation.recruits.network.MessageWriteSpawnEgg;
import com.talhanation.recruits.util.RecruitsComponents;
import com.talhanation.recruits.util.RecruitsItemStacks;
import net.minecraft.SharedConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.level.ServerLevel;
import com.talhanation.recruits.inventory.RecruitSimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RecruitsSpawnEggTest {
    @Test
    void writerPreservesPersistentDataWithoutOldMovementTargets() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        var registries = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        ServerLevel level = mock(ServerLevel.class);
        when(level.registryAccess()).thenReturn(registries);
        AbstractRecruitEntity recruit = mock(AbstractRecruitEntity.class);
        when(recruit.level()).thenReturn(level);
        when(recruit.getType()).thenReturn((EntityType) EntityType.VILLAGER);
        Component name = Component.literal("Veteran").withStyle(ChatFormatting.GOLD);
        when(recruit.getCustomName()).thenReturn(name);
        var attributes = new AttributeMap(Pig.createAttributes().build());
        attributes.getInstance(Attributes.MAX_HEALTH).setBaseValue(40D);
        when(recruit.getAttributes()).thenReturn(attributes);
        when(recruit.getShouldRanged()).thenReturn(true);
        when(recruit.getOwnerUUID()).thenReturn(new java.util.UUID(1, 2));
        when(recruit.getIsOwned()).thenReturn(true);
        when(recruit.getItemBySlot(any(EquipmentSlot.class))).thenReturn(ItemStack.EMPTY);
        recruit.inventory = new RecruitSimpleContainer(15, recruit);
        recruit.inventory.setItem(6, new ItemStack(Items.DIAMOND, 3));

        CompoundTag template = new MessageWriteSpawnEgg().fillRecruitsInfo(new CompoundTag(), recruit);

        assertEquals("minecraft:villager", template.getString("id"));
        assertEquals(name, RecruitsComponents.fromJson(template.getString("CustomName"), registries));
        assertEquals(recruit.getOwnerUUID(), template.getUUID("OwnerUUID"));
        assertTrue(template.getBoolean("ShouldRanged"));
        var restoredAttributes = new AttributeMap(Pig.createAttributes().build());
        restoredAttributes.load(template.getList("Attributes", 10));
        assertEquals(40D, restoredAttributes.getValue(Attributes.MAX_HEALTH));
        assertEquals(3, RecruitsItemStacks.parse(template.getList("Items", 10).getCompound(0), registries).getCount());
        for (String key : new String[]{"MountUUID", "ProtectUUID", "Target", "MovePosX", "UUID"}) {
            assertFalse(template.contains(key), key + " must not bind a new recruit to the original's transient state");
        }
    }

    @Test
    void copyReplacesDefaultGearAndClearsOldTargets() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        var registries = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        ServerLevel level = mock(ServerLevel.class);
        when(level.registryAccess()).thenReturn(registries);
        AbstractRecruitEntity recruit = mock(AbstractRecruitEntity.class);
        when(recruit.level()).thenReturn(level);
        when(recruit.getMaxHealth()).thenReturn(40F);
        when(recruit.getEquipmentSlotIndex(anyInt())).thenCallRealMethod();
        when(recruit.getInventorySlotIndex(any(EquipmentSlot.class))).thenCallRealMethod();
        recruit.inventory = new RecruitSimpleContainer(15, recruit);
        recruit.inventory.setItem(5, new ItemStack(Items.IRON_SWORD));

        CompoundTag template = new CompoundTag();
        template.putInt("FollowState", 4);
        template.putBoolean("ShouldRanged", true);
        CompoundTag stored = new CompoundTag();
        stored.putByte("Slot", (byte) 6);
        RecruitsItemStacks.saveInto(new ItemStack(Items.DIAMOND, 3), stored, registries);
        ListTag items = new ListTag();
        items.add(stored);
        template.put("Items", items);
        ListTag armor = new ListTag();
        armor.add(RecruitsItemStacks.save(new ItemStack(Items.IRON_BOOTS), registries));
        template.put("ArmorItems", armor);

        RecruitsSpawnEgg.fillRecruit(recruit, template, BlockPos.ZERO);

        assertTrue(recruit.inventory.getItem(5).isEmpty(), "Default spawn weapon must not leak into a copy");
        assertTrue(recruit.inventory.getItem(6).is(Items.DIAMOND));
        assertEquals(3, recruit.inventory.getItem(6).getCount());
        assertTrue(recruit.inventory.getItem(recruit.getInventorySlotIndex(EquipmentSlot.FEET)).is(Items.IRON_BOOTS));
        verify(recruit).setFollowState(3);
        verify(recruit).setShouldRanged(true);
        verify(recruit).setShouldMount(false);
        verify(recruit).setMountUUID(Optional.empty());
        verify(recruit).setProtectUUID(Optional.empty());
        verify(recruit).clearMovePos();
        verify(recruit).setTarget(null);
        verify(recruit).setHealth(40F);
    }
}

package com.talhanation.recruits.forge;

import com.talhanation.recruits.Main;
import com.talhanation.recruits.entities.AbstractInventoryEntity;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class ForgeCapabilities {

    private static final ResourceLocation INVENTORY = new ResourceLocation(Main.MOD_ID, "inventory");

    private ForgeCapabilities() {
    }

    static void register() {
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, ForgeCapabilities::attachInventory);
    }

    private static void attachInventory(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof AbstractInventoryEntity entity)) return;

        event.addCapability(INVENTORY, new ICapabilityProvider() {
            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
                // The entity replaces its inventory when it changes size, so wrap the current one on each request.
                if (entity.isAlive() && capability == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER && entity.inventory != null) {
                    return LazyOptional.<InvWrapper>of(() -> new InvWrapper(entity.inventory)).cast();
                }
                return LazyOptional.empty();
            }
        });
    }
}

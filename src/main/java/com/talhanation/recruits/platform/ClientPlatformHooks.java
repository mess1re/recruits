package com.talhanation.recruits.platform;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ServiceLoader;

/**
 * Client side loader specific functionality. Each loader project provides an implementation as a service.
 */
@Environment(EnvType.CLIENT)
public interface ClientPlatformHooks {

    ClientPlatformHooks INSTANCE = ServiceLoader.load(ClientPlatformHooks.class, ClientPlatformHooks.class.getClassLoader())
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No ClientPlatformHooks implementation found"));

    /**
     * Arm pose other mods assign to their items; null falls back to vanilla.
     */
    @Nullable
    HumanoidModel.ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack stack);
}

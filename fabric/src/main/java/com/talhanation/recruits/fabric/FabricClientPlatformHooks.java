package com.talhanation.recruits.fabric;

import com.talhanation.recruits.platform.ClientPlatformHooks;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class FabricClientPlatformHooks implements ClientPlatformHooks {

    @Override
    public HumanoidModel.ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack stack) {
        return null;
    }
}

package com.talhanation.recruits.forge;

import com.talhanation.recruits.platform.ClientPlatformHooks;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ForgeClientPlatformHooks implements ClientPlatformHooks {

    @Override
    public HumanoidModel.ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack stack) {
        return IClientItemExtensions.of(stack).getArmPose(entity, hand, stack);
    }
}

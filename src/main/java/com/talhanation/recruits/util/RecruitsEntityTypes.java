package com.talhanation.recruits.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class RecruitsEntityTypes {
    private RecruitsEntityTypes() {
    }

    public static <T extends Entity> EntityType.Builder<T> humanoid(EntityType.EntityFactory<T> factory) {
        return humanoid(factory, 0.98F);
    }

    public static <T extends Entity> EntityType.Builder<T> humanoid(EntityType.EntityFactory<T> factory, float eyeHeightRatio) {
        EntityType.Builder<T> builder = EntityType.Builder.of(factory, MobCategory.CREATURE).sized(0.6F, 1.95F);
        //? if >=1.21 {
        /*builder.vehicleAttachment(net.minecraft.world.entity.player.Player.DEFAULT_VEHICLE_ATTACHMENT);
        builder.eyeHeight(1.95F * eyeHeightRatio);
        *///?}
        return builder;
    }
}

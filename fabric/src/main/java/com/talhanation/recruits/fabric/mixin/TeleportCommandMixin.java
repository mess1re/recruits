package com.talhanation.recruits.fabric.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.talhanation.recruits.platform.PlatformEvents;
import net.minecraft.server.commands.TeleportCommand;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TeleportCommand.class)
public class TeleportCommandMixin {

    @Inject(method = "performTeleport", at = @At("HEAD"))
    private static void recruits$teleport(CallbackInfo ci, @Local(argsOnly = true) Entity entity,
                                          @Local(argsOnly = true, ordinal = 0) double x, @Local(argsOnly = true, ordinal = 1) double y, @Local(argsOnly = true, ordinal = 2) double z) {
        PlatformEvents.ENTITY_TELEPORT.invoker().teleport(entity, x, y, z);
    }
}

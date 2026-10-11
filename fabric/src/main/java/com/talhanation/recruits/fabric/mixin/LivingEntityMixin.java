package com.talhanation.recruits.fabric.mixin;

import com.talhanation.recruits.platform.PlatformEvents;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void recruits$livingTick(CallbackInfo ci) {
        PlatformEvents.LIVING_TICK.invoker().tick((LivingEntity) (Object) this);
    }

    @Inject(method = "knockback", at = @At("HEAD"), cancellable = true)
    private void recruits$knockback(double strength, double x, double z, CallbackInfo ci) {
        if (PlatformEvents.LIVING_KNOCKBACK.invoker().knockBack((LivingEntity) (Object) this).isFalse()) {
            ci.cancel();
        }
    }
}

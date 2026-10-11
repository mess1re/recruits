package com.talhanation.recruits.fabric.mixin;

import com.talhanation.recruits.platform.PlatformEvents;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public class ProjectileMixin {

    @Inject(method = "onHit", at = @At("HEAD"), cancellable = true)
    private void recruits$impact(HitResult hitResult, CallbackInfo ci) {
        if (PlatformEvents.PROJECTILE_IMPACT.invoker().impact((Projectile) (Object) this, hitResult).isFalse()) {
            ci.cancel();
        }
    }
}

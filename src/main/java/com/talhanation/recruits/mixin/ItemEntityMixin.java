package com.talhanation.recruits.mixin;

import com.talhanation.recruits.PillagerEvents;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void raidStartOnBurningOminous(CallbackInfo ci) {
        PillagerEvents.raidStartOnBurningOminous((ItemEntity) (Object) this);
    }
}

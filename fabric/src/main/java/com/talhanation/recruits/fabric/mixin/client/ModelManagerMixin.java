package com.talhanation.recruits.fabric.mixin.client;

import com.talhanation.recruits.platform.ClientPlatformEvents;
import net.minecraft.client.resources.model.ModelManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelManager.class)
public class ModelManagerMixin {

    @Inject(method = "apply", at = @At("TAIL"))
    private void recruits$modelsBaked(CallbackInfo ci) {
        ClientPlatformEvents.MODELS_BAKED.invoker().run();
    }
}

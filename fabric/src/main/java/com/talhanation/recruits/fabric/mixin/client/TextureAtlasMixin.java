package com.talhanation.recruits.fabric.mixin.client;

import com.talhanation.recruits.platform.ClientPlatformEvents;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextureAtlas.class)
public class TextureAtlasMixin {

    @Shadow
    @Final
    private ResourceLocation location;

    @Inject(method = "upload", at = @At("TAIL"))
    private void recruits$atlasStitched(CallbackInfo ci) {
        if (TextureAtlas.LOCATION_BLOCKS.equals(this.location)) {
            ClientPlatformEvents.BLOCK_ATLAS_STITCHED.invoker().run();
        }
    }
}

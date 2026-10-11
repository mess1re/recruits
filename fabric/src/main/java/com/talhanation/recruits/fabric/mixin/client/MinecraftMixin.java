package com.talhanation.recruits.fabric.mixin.client;

import com.talhanation.recruits.platform.ClientPlatformEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow
    public ClientLevel level;

    //? if <1.21 {
    @Inject(method = {"setLevel", "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V"}, at = @At("HEAD"))
    //?} else {
    /*@Inject(method = {"setLevel", "disconnect(Lnet/minecraft/client/gui/screens/Screen;Z)V"}, at = @At("HEAD"))
    *///?}
    private void recruits$unloadLevel(CallbackInfo ci) {
        if (this.level != null) {
            ClientPlatformEvents.LEVEL_UNLOAD.invoker().unload(this.level);
        }
    }

    @Inject(method = "pickBlock", at = @At("HEAD"), cancellable = true)
    private void recruits$pickBlock(CallbackInfo ci) {
        if (ClientPlatformEvents.PICK_BLOCK.invoker().pick().isFalse()) {
            ci.cancel();
        }
    }
}

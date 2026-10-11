package com.talhanation.recruits.forge.mixin.corpse;

import com.mojang.blaze3d.vertex.PoseStack;
import com.talhanation.recruits.compat.corpse.RecruitCorpseClientRenderer;
import de.maxhenkel.corpse.Main;
import de.maxhenkel.corpse.entities.CorpseEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "de.maxhenkel.corpse.entities.CorpseRenderer", remap = false)
public abstract class CorpseRendererMixin extends EntityRenderer<CorpseEntity> {

    protected CorpseRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(
            method = "render(Lde/maxhenkel/corpse/entities/CorpseEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void recruits$renderRecruitCorpse(CorpseEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, CallbackInfo ci) {
        if (!entity.isSkeleton() && RecruitCorpseClientRenderer.renderRecruitCorpse(entity, entity.getCorpseUUID().orElse(null), entity.getEquipment(),
                Main.SERVER_CONFIG.spawnCorpseOnFace.get(), partialTicks, poseStack, bufferSource, packedLight)) {
            super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
            ci.cancel();
        }
    }
}

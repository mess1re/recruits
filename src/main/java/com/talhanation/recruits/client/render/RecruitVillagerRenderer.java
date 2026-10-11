package com.talhanation.recruits.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.talhanation.recruits.Main;
import com.talhanation.recruits.client.events.ClientEvent;
import com.talhanation.recruits.client.models.RecruitVillagerModel;
import com.talhanation.recruits.client.render.layer.RecruitVillagerBiomeLayer;
import com.talhanation.recruits.client.render.layer.RecruitVillagerCompanionLayer;
import com.talhanation.recruits.client.render.layer.RecruitVillagerTeamColorLayer;
import com.talhanation.recruits.client.render.layer.VillagerRecruitCustomHeadLayer;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class RecruitVillagerRenderer extends MobRenderer<AbstractRecruitEntity, HumanoidModel<AbstractRecruitEntity>> {

    private static final ResourceLocation[] TEXTURE = {
            new ResourceLocation(Main.MOD_ID,"textures/entity/villager/villager_1.png"),
    };
    public ResourceLocation getTextureLocation(AbstractRecruitEntity recruit) {
        return TEXTURE[0];
    }
    public RecruitVillagerRenderer(EntityRendererProvider.Context context) {
        super(context, new RecruitVillagerModel(context.bakeLayer(ClientEvent.RECRUIT)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel(context.bakeLayer(ClientEvent.RECRUIT_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ClientEvent.RECRUIT_OUTER_ARMOR)), context.getModelManager()));
        this.addLayer(new RecruitVillagerTeamColorLayer(this));
        this.addLayer(new RecruitVillagerBiomeLayer(this));
        this.addLayer(new RecruitVillagerCompanionLayer(this));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new VillagerRecruitCustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
    }

    @Override
    public void render(AbstractRecruitEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        this.setModelVisibilities(entityIn);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    //PlayerRenderer
    private void setModelVisibilities(AbstractRecruitEntity recruit) {
        RecruitModelProperties.configure(this.getModel(), recruit);
        this.getModel().hat.visible = true;
    }

    @Override
    protected void scale(AbstractRecruitEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(0.9375F, 0.9375F, 0.9375F);
    }


}

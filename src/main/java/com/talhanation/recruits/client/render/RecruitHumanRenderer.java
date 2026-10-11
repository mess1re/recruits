package com.talhanation.recruits.client.render;
import com.mojang.blaze3d.vertex.PoseStack;
import com.talhanation.recruits.Main;
import com.talhanation.recruits.client.events.ClientEvent;
import com.talhanation.recruits.client.render.layer.RecruitHumanBiomeLayer;
import com.talhanation.recruits.client.render.layer.RecruitHumanCompanionLayer;
import com.talhanation.recruits.client.render.layer.RecruitHumanTeamColorLayer;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;

public class RecruitHumanRenderer extends MobRenderer<AbstractRecruitEntity, HumanoidModel<AbstractRecruitEntity>> {

    private static final ResourceLocation[] TEXTURE = {
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_0.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_1.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_2.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_3.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_4.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_5.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_6.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_7.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_8.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_9.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_10.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_11.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_12.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_13.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_14.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_15.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_16.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_17.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_18.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/human/human_19.png")
    };

    @Override
    public ResourceLocation getTextureLocation(AbstractRecruitEntity recruit) {
        return TEXTURE[recruit.getVariant()];
    }
    public RecruitHumanRenderer(EntityRendererProvider.Context mgr) {
        super(mgr, new HumanoidModel<>((mgr.bakeLayer(ModelLayers.PLAYER))), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel(mgr.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(mgr.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), mgr.getModelManager()));
        this.addLayer(new RecruitHumanTeamColorLayer(this, mgr.bakeLayer(ClientEvent.RECRUIT_HUMAN_TEAM_LAYER)));
        this.addLayer(new RecruitHumanBiomeLayer(this, mgr.bakeLayer(ClientEvent.RECRUIT_HUMAN_BIOME_LAYER)));
        this.addLayer(new RecruitHumanCompanionLayer(this, mgr.bakeLayer(ClientEvent.RECRUIT_HUMAN_COMPANION_LAYER)));
        //this.addLayer(new ArrowLayer<>(mgr, this));
        this.addLayer(new ItemInHandLayer<>(this, mgr.getItemInHandRenderer()));
        this.addLayer(new CustomHeadLayer<>(this, mgr.getModelSet(), mgr.getItemInHandRenderer()));

    }


    public void render(AbstractRecruitEntity recruit, float p_117789_, float p_117790_, PoseStack p_117791_, MultiBufferSource p_117792_, int p_117793_) {
        this.setModelProperties(recruit);
        super.render(recruit, p_117789_, p_117790_, p_117791_, p_117792_, p_117793_);
    }

    private void setModelProperties(AbstractRecruitEntity recruit) {
        RecruitModelProperties.configure(this.getModel(), recruit);
    }



}

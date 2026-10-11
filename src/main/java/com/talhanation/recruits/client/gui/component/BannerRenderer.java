package com.talhanation.recruits.client.gui.component;

import com.mojang.blaze3d.platform.Lighting;
//? if <1.21 {
import com.mojang.datafixers.util.Pair;
//?}
import com.talhanation.recruits.util.RecruitsItemStacks;
import com.talhanation.recruits.world.RecruitsFaction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
//? if <1.21 {
import net.minecraft.core.Holder;
//?} else {
/*import net.minecraft.core.component.DataComponents;
*///?}
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
//? if <1.21 {
import net.minecraft.world.level.block.entity.BannerPattern;
//?} else {
/*import net.minecraft.world.level.block.entity.BannerPatternLayers;
*///?}
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BannerRenderer {
    //? if <1.21 {
    private List<Pair<Holder<BannerPattern>, DyeColor>> resultBannerPatterns;
    //?} else {
    /*private BannerPatternLayers resultBannerPatterns;
    *///?}
    private final ModelPart flag;
    private ItemStack bannerItem;
    private RecruitsFaction recruitsFaction;
    private final Minecraft minecraft;
    public BannerRenderer(@Nullable RecruitsFaction team) {
        this.minecraft = Minecraft.getInstance();
        this.recruitsFaction = team;
        boolean fail = true;
        if (team != null && team.getBanner() != null){
            ItemStack itemStack = RecruitsItemStacks.parse(team.getBanner(), this.minecraft.level.registryAccess());
            if(itemStack.getItem() instanceof BannerItem bannerItem1){
                this.bannerItem = itemStack;
                //? if <1.21 {
                this.resultBannerPatterns = BannerBlockEntity.createPatterns(
                        bannerItem1.getColor(),
                        BannerBlockEntity.getItemPatterns(this.bannerItem)
                );
                //?} else {
                /*this.resultBannerPatterns = this.bannerItem.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
                *///?}
                fail = false;
            }

        }

        if(fail){
            this.bannerItem = ItemStack.EMPTY;
            //? if <1.21 {
            this.resultBannerPatterns = List.of();
            //?} else {
            /*this.resultBannerPatterns = BannerPatternLayers.EMPTY;
            *///?}
        }

        this.flag = Minecraft.getInstance().getEntityModels()
                .bakeLayer(ModelLayers.BANNER)
                .getChild("flag"); 
    }

    public void renderBanner(GuiGraphics guiGraphics, int left, int top, int width, int height, int scale0) {
        if (bannerItem.isEmpty() || this.flag == null || this.resultBannerPatterns == null) return;

        Lighting.setupForFlatItems();
        guiGraphics.pose().pushPose();
        try {
            guiGraphics.pose().translate(left + 10, top + 20, 0.0D);
            guiGraphics.pose().scale(scale0, -scale0, 1.0F);

            float scale = 0.6666667F;
            guiGraphics.pose().scale(scale, -scale, -scale);
            this.flag.xRot = 0.0F;
            this.flag.y = -32.0F;

            MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
            //? if <1.21 {
            net.minecraft.client.renderer.blockentity.BannerRenderer.renderPatterns(
                    guiGraphics.pose(), bufferSource, 15728880, OverlayTexture.NO_OVERLAY,
                    this.flag, ModelBakery.BANNER_BASE, true, this.resultBannerPatterns);
            //?} else {
            /*net.minecraft.client.renderer.blockentity.BannerRenderer.renderPatterns(
                    guiGraphics.pose(), bufferSource, 15728880, OverlayTexture.NO_OVERLAY,
                    this.flag, ModelBakery.BANNER_BASE, true,
                    ((BannerItem) this.bannerItem.getItem()).getColor(), this.resultBannerPatterns);
            *///?}
            bufferSource.endBatch();
        } finally {
            guiGraphics.pose().popPose();
            Lighting.setupFor3DItems();
        }
    }

    public void setBannerItem(ItemStack bannerItem) {
        if(bannerItem.getItem() instanceof BannerItem){
            this.bannerItem = bannerItem;
            //? if <1.21 {
            this.resultBannerPatterns = BannerBlockEntity.createPatterns(((BannerItem) this.bannerItem.getItem()).getColor(), BannerBlockEntity.getItemPatterns(this.bannerItem));
            //?} else {
            /*this.resultBannerPatterns = this.bannerItem.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
            *///?}
        }
    }

    public void setRecruitsFaction(RecruitsFaction faction){
        if (faction == null || faction.getBanner() == null) {
            this.recruitsFaction = null;
            this.bannerItem = ItemStack.EMPTY;
            //? if <1.21 {
            this.resultBannerPatterns = List.of();
            //?} else {
            /*this.resultBannerPatterns = BannerPatternLayers.EMPTY;
            *///?}
            return;
        }

        this.recruitsFaction = faction;
        ItemStack itemStack = RecruitsItemStacks.parse(faction.getBanner(), this.minecraft.level.registryAccess());
        if (itemStack.getItem() instanceof BannerItem bannerItem) {
            this.bannerItem = itemStack;
            //? if <1.21 {
            this.resultBannerPatterns =
                    BannerBlockEntity.createPatterns(bannerItem.getColor(), BannerBlockEntity.getItemPatterns(this.bannerItem));
            //?} else {
            /*this.resultBannerPatterns = this.bannerItem.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
            *///?}
        } else {
            this.bannerItem = ItemStack.EMPTY;
            //? if <1.21 {
            this.resultBannerPatterns = List.of();
            //?} else {
            /*this.resultBannerPatterns = BannerPatternLayers.EMPTY;
            *///?}
        }
    }
}

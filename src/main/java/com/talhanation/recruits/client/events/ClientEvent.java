package com.talhanation.recruits.client.events;


import com.talhanation.recruits.Main;
import com.talhanation.recruits.client.models.RecruitVillagerModel;
import com.talhanation.recruits.client.gui.worldmap.storage.WorldMapCacheManager;
import com.talhanation.recruits.client.render.RecruitHumanRenderer;
import com.talhanation.recruits.client.render.RecruitVillagerRenderer;
import com.talhanation.recruits.client.render.layer.RecruitArmorLayer;
import com.talhanation.recruits.config.RecruitsClientConfig;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import com.talhanation.recruits.init.ModEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.talhanation.recruits.platform.ClientPlatformEvents;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import javax.annotation.Nullable;

public class ClientEvent {

    public static ModelLayerLocation RECRUIT = new ModelLayerLocation(new ResourceLocation(Main.MOD_ID + "recruit"), "recruit");
    public static ModelLayerLocation RECRUIT_OUTER_ARMOR = new ModelLayerLocation(new ResourceLocation(Main.MOD_ID + "recruit_outer_layer"), "recruit_outer_layer");
    public static ModelLayerLocation RECRUIT_INNER_ARMOR = new ModelLayerLocation(new ResourceLocation(Main.MOD_ID + "recruit_inner_layer"), "recruit_inner_layer");

    public static void register() {
        entityRenderersEvent();
        layerDefinitions();
        ClientPlatformEvents.MODELS_BAKED.register(ClientEvent::modelBakingCompleted);
        ClientPlatformEvents.BLOCK_ATLAS_STITCHED.register(ClientEvent::textureStitchCompleted);
    }

    @Environment(EnvType.CLIENT)
    public static void entityRenderersEvent(){
        EntityRendererRegistry.register(ModEntityTypes.RECRUIT, ClientEvent::createRecruitRenderer);
        EntityRendererRegistry.register(ModEntityTypes.BOWMAN, ClientEvent::createRecruitRenderer);
        EntityRendererRegistry.register(ModEntityTypes.NOMAD, ClientEvent::createRecruitRenderer);
        EntityRendererRegistry.register(ModEntityTypes.HORSEMAN, ClientEvent::createRecruitRenderer);
        EntityRendererRegistry.register(ModEntityTypes.CROSSBOWMAN, ClientEvent::createRecruitRenderer);
        EntityRendererRegistry.register(ModEntityTypes.RECRUIT_SHIELDMAN, ClientEvent::createRecruitRenderer);

        //COMPANIONS
        EntityRendererRegistry.register(ModEntityTypes.MESSENGER, ClientEvent::createRecruitRenderer);
        EntityRendererRegistry.register(ModEntityTypes.SCOUT, ClientEvent::createRecruitRenderer);
        EntityRendererRegistry.register(ModEntityTypes.PATROL_LEADER, ClientEvent::createRecruitRenderer);
        EntityRendererRegistry.register(ModEntityTypes.CAPTAIN, ClientEvent::createRecruitRenderer);
        EntityRendererRegistry.register(ModEntityTypes.SIEGE_ENGINEER, ClientEvent::createRecruitRenderer);

        //OTHER
        EntityRendererRegistry.register(ModEntityTypes.VILLAGER_NOBLE, ClientEvent::createRecruitRenderer);
    }

    // The config is read when renderers are created, it is not loaded yet when they are registered.
    private static EntityRenderer<AbstractRecruitEntity> createRecruitRenderer(EntityRendererProvider.Context context) {
        if(RecruitsClientConfig.RecruitsLookLikeVillagers.get()){
            return new RecruitVillagerRenderer(context);
        }
        return new RecruitHumanRenderer(context);
    }

    public static void layerDefinitions() {
        EntityModelLayerRegistry.register(ClientEvent.RECRUIT, RecruitVillagerModel::createLayerDefinition);
        EntityModelLayerRegistry.register(ClientEvent.RECRUIT_OUTER_ARMOR, RecruitArmorLayer::createOuterArmorLayer);
        EntityModelLayerRegistry.register(ClientEvent.RECRUIT_INNER_ARMOR, RecruitArmorLayer::createInnerArmorLayer);

    }

    public static void modelBakingCompleted() {
        WorldMapCacheManager.getInstance().onClientModelsReloaded();
    }

    public static void textureStitchCompleted() {
        WorldMapCacheManager.getInstance().onClientBlockAtlasStitched();
    }

    @Nullable
    public static Entity getEntityByLooking() {
        HitResult hit = Minecraft.getInstance().hitResult;

        if (hit instanceof EntityHitResult entityHitResult){
            return entityHitResult.getEntity();
        }
        return null;
    }
}

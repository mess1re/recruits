package com.talhanation.recruits.forge;

import com.talhanation.recruits.platform.ClientPlatformEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.IEventBus;

final class ForgeClientPlatformEvents {

    private ForgeClientPlatformEvents() {
    }

    static void register(IEventBus modEventBus) {
        MinecraftForge.EVENT_BUS.addListener((LevelEvent.Unload event) -> {
            if (event.getLevel() instanceof ClientLevel level) {
                ClientPlatformEvents.LEVEL_UNLOAD.invoker().unload(level);
            }
        });

        MinecraftForge.EVENT_BUS.addListener((ChunkEvent.Load event) -> {
            if (event.getLevel() instanceof ClientLevel level && event.getChunk() instanceof LevelChunk chunk) {
                ClientPlatformEvents.CHUNK_LOAD.invoker().chunk(level, chunk);
            }
        });

        MinecraftForge.EVENT_BUS.addListener((ChunkEvent.Unload event) -> {
            if (event.getLevel() instanceof ClientLevel level && event.getChunk() instanceof LevelChunk chunk) {
                ClientPlatformEvents.CHUNK_UNLOAD.invoker().chunk(level, chunk);
            }
        });

        MinecraftForge.EVENT_BUS.addListener((InputEvent.InteractionKeyMappingTriggered event) -> {
            if (event.isPickBlock() && ClientPlatformEvents.PICK_BLOCK.invoker().pick().isFalse()) {
                event.setCanceled(true);
            }
        });

        modEventBus.addListener((ModelEvent.BakingCompleted event) -> ClientPlatformEvents.MODELS_BAKED.invoker().run());

        modEventBus.addListener((TextureStitchEvent.Post event) -> {
            if (TextureAtlas.LOCATION_BLOCKS.equals(event.getAtlas().location())) {
                ClientPlatformEvents.BLOCK_ATLAS_STITCHED.invoker().run();
            }
        });
    }
}

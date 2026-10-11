package com.talhanation.recruits.neoforge;

import com.talhanation.recruits.platform.ClientPlatformEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.TextureAtlasStitchedEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.bus.api.IEventBus;

final class NeoForgeClientPlatformEvents {

    private NeoForgeClientPlatformEvents() {
    }

    static void register(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener((LevelEvent.Unload event) -> {
            if (event.getLevel() instanceof ClientLevel level) {
                ClientPlatformEvents.LEVEL_UNLOAD.invoker().unload(level);
            }
        });

        NeoForge.EVENT_BUS.addListener((ChunkEvent.Load event) -> {
            if (event.getLevel() instanceof ClientLevel level && event.getChunk() instanceof LevelChunk chunk) {
                ClientPlatformEvents.CHUNK_LOAD.invoker().chunk(level, chunk);
            }
        });

        NeoForge.EVENT_BUS.addListener((ChunkEvent.Unload event) -> {
            if (event.getLevel() instanceof ClientLevel level && event.getChunk() instanceof LevelChunk chunk) {
                ClientPlatformEvents.CHUNK_UNLOAD.invoker().chunk(level, chunk);
            }
        });

        NeoForge.EVENT_BUS.addListener((InputEvent.InteractionKeyMappingTriggered event) -> {
            if (event.isPickBlock() && ClientPlatformEvents.PICK_BLOCK.invoker().pick().isFalse()) {
                event.setCanceled(true);
            }
        });

        modEventBus.addListener((ModelEvent.BakingCompleted event) -> ClientPlatformEvents.MODELS_BAKED.invoker().run());

        modEventBus.addListener((TextureAtlasStitchedEvent event) -> {
            if (TextureAtlas.LOCATION_BLOCKS.equals(event.getAtlas().location())) {
                ClientPlatformEvents.BLOCK_ATLAS_STITCHED.invoker().run();
            }
        });
    }
}

package com.talhanation.recruits.client.events;

import com.talhanation.recruits.client.gui.worldmap.storage.WorldMapCacheManager;
import com.talhanation.recruits.client.gui.worldmap.storage.WorldMapStorageId;
import com.talhanation.recruits.config.RecruitsClientConfig;
import com.talhanation.recruits.platform.ClientPlatformEvents;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class ClientPlayerEvents {
    public void register() {
        ClientTickEvent.CLIENT_POST.register(this::onClientTick);
        ClientLifecycleEvent.CLIENT_LEVEL_LOAD.register(this::onWorldLoad);
        ClientPlatformEvents.LEVEL_UNLOAD.register(this::onWorldUnload);
        ClientPlatformEvents.CHUNK_LOAD.register(this::onChunkLoad);
        ClientPlatformEvents.CHUNK_UNLOAD.register(this::onChunkUnload);
    }

    public void onClientTick(Minecraft minecraft) {
        if (!RecruitsClientConfig.UpdateMapTiles.get()) return;

        updateMapTiles();
    }

    private void updateMapTiles() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;
        if (mc.level.dimension() != Level.OVERWORLD) return;

        WorldMapCacheManager.getInstance().updateCurrentTile();
    }

    public void onWorldLoad(ClientLevel level) {
        WorldMapCacheManager.getInstance().initialize(level);
    }

    public void onWorldUnload(ClientLevel level) {
        WorldMapCacheManager.getInstance().close();
        WorldMapStorageId.clearServerWorldId();
    }

    public void onChunkLoad(ClientLevel level, LevelChunk chunk) {
        WorldMapCacheManager.getInstance().onChunkLoaded(level, chunk.getPos());
    }

    public void onChunkUnload(ClientLevel level, LevelChunk chunk) {
        WorldMapCacheManager.getInstance().onChunkUnloaded(level, chunk.getPos());
    }
}

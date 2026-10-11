package com.talhanation.recruits.fabric;

import com.talhanation.recruits.platform.ClientPlatformEvents;
import com.talhanation.recruits.platform.PlatformEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;

final class FabricClientPlatformEvents {

    private FabricClientPlatformEvents() {
    }

    static void register() {
        ClientEntityEvents.ENTITY_LOAD.register((entity, level) -> PlatformEvents.ENTITY_JOIN.invoker().join(entity, level));

        ClientChunkEvents.CHUNK_LOAD.register((level, chunk) -> ClientPlatformEvents.CHUNK_LOAD.invoker().chunk(level, chunk));
        ClientChunkEvents.CHUNK_UNLOAD.register((level, chunk) -> ClientPlatformEvents.CHUNK_UNLOAD.invoker().chunk(level, chunk));
    }
}

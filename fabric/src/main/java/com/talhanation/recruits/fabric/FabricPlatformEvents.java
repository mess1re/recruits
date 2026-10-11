package com.talhanation.recruits.fabric;

import com.talhanation.recruits.platform.PlatformEvents;
import com.talhanation.recruits.platform.PlatformHooks;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.nbt.CompoundTag;

final class FabricPlatformEvents {

    private FabricPlatformEvents() {
    }

    static void register() {
        ServerWorldEvents.LOAD.register((server, level) -> FabricChunkTickets.restore(level));

        ServerEntityEvents.ENTITY_LOAD.register((entity, level) ->
                PlatformEvents.ENTITY_JOIN.invoker().join(entity, level));

        ServerEntityEvents.ENTITY_UNLOAD.register((entity, level) ->
                PlatformEvents.ENTITY_LEAVE.invoker().leave(entity, level));

        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            CompoundTag persisted = PlatformHooks.INSTANCE.getPersistentData(oldPlayer).getCompound(PlatformHooks.PERSISTED_NBT_TAG);
            PlatformHooks.INSTANCE.getPersistentData(newPlayer).put(PlatformHooks.PERSISTED_NBT_TAG, persisted.copy());
        });
    }
}

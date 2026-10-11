package com.talhanation.recruits.platform;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.chunk.LevelChunk;

/**
 * Client events Architectury does not provide. They are fired by the loader projects.
 */
@Environment(EnvType.CLIENT)
public final class ClientPlatformEvents {

    public static final Event<LevelUnload> LEVEL_UNLOAD = EventFactory.createLoop();
    public static final Event<Chunk> CHUNK_LOAD = EventFactory.createLoop();
    public static final Event<Chunk> CHUNK_UNLOAD = EventFactory.createLoop();
    /**
     * {@link EventResult#interruptFalse()} cancels the vanilla pick block action.
     */
    public static final Event<PickBlock> PICK_BLOCK = EventFactory.createEventResult();
    public static final Event<Runnable> MODELS_BAKED = EventFactory.createLoop();
    public static final Event<Runnable> BLOCK_ATLAS_STITCHED = EventFactory.createLoop();

    private ClientPlatformEvents() {
    }

    public interface LevelUnload {
        void unload(ClientLevel level);
    }

    public interface Chunk {
        void chunk(ClientLevel level, LevelChunk chunk);
    }

    public interface PickBlock {
        EventResult pick();
    }
}

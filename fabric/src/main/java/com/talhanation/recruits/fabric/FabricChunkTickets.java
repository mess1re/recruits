package com.talhanation.recruits.fabric;

import com.talhanation.recruits.Main;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;
//? if >=1.21 {
/*import net.minecraft.core.HolderLookup;
*///?}

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Chunks forced on behalf of entities, like Forge's chunk manager does. Each chunk stays loaded while any owner
 * holds it, independent of other owners and of the /forceload command.
 */
final class FabricChunkTickets {

    private static final TicketType<ChunkPos> TICKET = TicketType.create(Main.MOD_ID + ":forced", (a, b) -> Long.compare(a.toLong(), b.toLong()));
    // Same distance as vanilla forced chunks, so entities in them keep ticking.
    private static final int TICKET_DISTANCE = 2;
    private static final String DATA_NAME = Main.MOD_ID + "_forced_chunks";

    private FabricChunkTickets() {
    }

    static void forceChunk(ServerLevel level, UUID owner, ChunkPos pos, boolean add) {
        Data data = data(level);
        Set<UUID> owners = data.owners.get(pos.toLong());

        if (add) {
            if (owners == null) {
                owners = new HashSet<>();
                data.owners.put(pos.toLong(), owners);
                level.getChunkSource().addRegionTicket(TICKET, pos, TICKET_DISTANCE, pos);
            }
            if (owners.add(owner)) data.setDirty();
        } else if (owners != null && owners.remove(owner)) {
            if (owners.isEmpty()) {
                data.owners.remove(pos.toLong());
                level.getChunkSource().removeRegionTicket(TICKET, pos, TICKET_DISTANCE, pos);
            }
            data.setDirty();
        }
    }

    /**
     * Tickets are not saved with the world, so they are added again from the saved owners when a level loads.
     */
    static void restore(ServerLevel level) {
        for (long pos : data(level).owners.keySet()) {
            ChunkPos chunkPos = new ChunkPos(pos);
            level.getChunkSource().addRegionTicket(TICKET, chunkPos, TICKET_DISTANCE, chunkPos);
        }
    }

    private static Data data(ServerLevel level) {
        //? if >=1.21 {
        /*return level.getDataStorage().computeIfAbsent(new SavedData.Factory<>(Data::new, Data::load, null), DATA_NAME);
        *///?} else {
        return level.getDataStorage().computeIfAbsent(Data::load, Data::new, DATA_NAME);
        //?}
    }

    private static final class Data extends SavedData {

        private final Map<Long, Set<UUID>> owners = new HashMap<>();

        //? if >=1.21 {
        /*private static Data load(CompoundTag tag, HolderLookup.Provider registries) {
        *///?} else {
        private static Data load(CompoundTag tag) {
        //?}
            Data data = new Data();
            ListTag chunks = tag.getList("Chunks", Tag.TAG_COMPOUND);
            for (int i = 0; i < chunks.size(); i++) {
                CompoundTag chunk = chunks.getCompound(i);
                Set<UUID> owners = new HashSet<>();
                for (Tag owner : chunk.getList("Owners", Tag.TAG_INT_ARRAY)) {
                    owners.add(NbtUtils.loadUUID(owner));
                }
                if (!owners.isEmpty()) data.owners.put(chunk.getLong("Pos"), owners);
            }
            return data;
        }

        @Override
        //? if >=1.21 {
        /*public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        *///?} else {
        public CompoundTag save(CompoundTag tag) {
        //?}
            ListTag chunks = new ListTag();
            owners.forEach((pos, chunkOwners) -> {
                CompoundTag chunk = new CompoundTag();
                chunk.putLong("Pos", pos);
                ListTag ownerList = new ListTag();
                chunkOwners.forEach(owner -> ownerList.add(NbtUtils.createUUID(owner)));
                chunk.put("Owners", ownerList);
                chunks.add(chunk);
            });
            tag.put("Chunks", chunks);
            return tag;
        }
    }
}

package com.talhanation.recruits.world;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.UUID;

public class RecruitsWorldSaveData extends SavedData {
    private static final String FILE_ID = "recruitsWorldId";
    private UUID worldId;

    public RecruitsWorldSaveData() {
        this.worldId = UUID.randomUUID();
        this.setDirty();
    }

    private RecruitsWorldSaveData(UUID worldId) {
        this.worldId = worldId == null ? UUID.randomUUID() : worldId;
        if (worldId == null) this.setDirty();
    }

    public static RecruitsWorldSaveData get(ServerLevel level) {
        //? if <1.21 {
        return level.getDataStorage().computeIfAbsent(
                RecruitsWorldSaveData::load,
                RecruitsWorldSaveData::new,
                FILE_ID);
        //?} else {
        /*return level.getDataStorage().computeIfAbsent(
                new SavedData.Factory<>(RecruitsWorldSaveData::new, RecruitsWorldSaveData::load, null),
                FILE_ID);
        *///?}
    }

    //? if <1.21 {
    public static RecruitsWorldSaveData load(CompoundTag nbt) {
    //?} else {
    /*public static RecruitsWorldSaveData load(CompoundTag nbt, net.minecraft.core.HolderLookup.Provider registries) {
    *///?}
        UUID worldId = nbt.hasUUID("WorldId") ? nbt.getUUID("WorldId") : null;
        return new RecruitsWorldSaveData(worldId);
    }

    @Override
    //? if <1.21 {
    public CompoundTag save(CompoundTag nbt) {
    //?} else {
    /*public CompoundTag save(CompoundTag nbt, net.minecraft.core.HolderLookup.Provider registries) {
    *///?}
        nbt.putUUID("WorldId", worldId);
        return nbt;
    }

    public UUID getWorldId() {
        return worldId;
    }
}

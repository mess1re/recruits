package com.talhanation.recruits.world;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;

public class RecruitsTreatySaveData extends SavedData {

    private static final String DATA_NAME = "treaties_data";
    private Map<String, Long> treaties = new HashMap<>();

    public RecruitsTreatySaveData() {
        this.setDirty();
    }

    public static RecruitsTreatySaveData get(ServerLevel level) {
        //? if <1.21 {
        return level.getDataStorage().computeIfAbsent(RecruitsTreatySaveData::load, RecruitsTreatySaveData::new, DATA_NAME);
        //?} else {
        /*return level.getDataStorage().computeIfAbsent(
                new SavedData.Factory<>(RecruitsTreatySaveData::new, RecruitsTreatySaveData::load, null), DATA_NAME);
        *///?}
    }

    //? if <1.21 {
    public static RecruitsTreatySaveData load(CompoundTag nbt) {
    //?} else {
    /*public static RecruitsTreatySaveData load(CompoundTag nbt, net.minecraft.core.HolderLookup.Provider registries) {
    *///?}
        RecruitsTreatySaveData data = new RecruitsTreatySaveData();
        data.treaties = RecruitsTreatyManager.mapFromNbt(nbt.getCompound("treaties"));
        return data;
    }

    @Override
    //? if <1.21 {
    public CompoundTag save(CompoundTag nbt) {
    //?} else {
    /*public CompoundTag save(CompoundTag nbt, net.minecraft.core.HolderLookup.Provider registries) {
    *///?}
        nbt.put("treaties", RecruitsTreatyManager.mapToNbt(treaties));
        return nbt;
    }

    public Map<String, Long> getTreaties() {
        return treaties;
    }

    public void setTreaties(Map<String, Long> treaties) {
        this.treaties = new HashMap<>(treaties);
        this.setDirty();
    }
}

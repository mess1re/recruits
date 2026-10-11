package com.talhanation.recruits.world;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class RecruitsClaimSaveData extends SavedData {
    private static final String FILE_ID = "recruitsClaims";
    private List<RecruitsClaim> claimList = new ArrayList<>();

    public static RecruitsClaimSaveData get(ServerLevel level) {
        //? if <1.21 {
        return level.getDataStorage().computeIfAbsent(RecruitsClaimSaveData::load, RecruitsClaimSaveData::new, FILE_ID);
        //?} else {
        /*return level.getDataStorage().computeIfAbsent(
                new SavedData.Factory<>(RecruitsClaimSaveData::new, RecruitsClaimSaveData::load, null), FILE_ID);
        *///?}
    }

    //? if <1.21 {
    public static RecruitsClaimSaveData load(CompoundTag nbt) {
    //?} else {
    /*public static RecruitsClaimSaveData load(CompoundTag nbt, net.minecraft.core.HolderLookup.Provider registries) {
    *///?}
        RecruitsClaimSaveData data = new RecruitsClaimSaveData();
        if (nbt.contains("claims", Tag.TAG_LIST)) {
            ListTag list = nbt.getList("claims", Tag.TAG_COMPOUND);
            for (Tag t : list) {
                data.claimList.add(RecruitsClaim.fromNBT((CompoundTag) t));
            }
        }
        return data;
    }

    @Override
    //? if <1.21 {
    public CompoundTag save(CompoundTag nbt) {
    //?} else {
    /*public CompoundTag save(CompoundTag nbt, net.minecraft.core.HolderLookup.Provider registries) {
    *///?}
        ListTag list = new ListTag();
        for (RecruitsClaim claim : this.claimList) {
            list.add(claim.toNBT());
        }
        nbt.put("claims", list);
        return nbt;
    }

    public List<RecruitsClaim> getAllClaims() {
        return this.claimList;
    }

    public void setAllClaims(List<RecruitsClaim> claims) {
        this.claimList = claims;
    }
}



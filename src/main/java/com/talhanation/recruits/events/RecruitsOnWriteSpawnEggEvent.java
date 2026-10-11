package com.talhanation.recruits.events;

import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.nbt.CompoundTag;
public class RecruitsOnWriteSpawnEggEvent extends Event {
    public final AbstractRecruitEntity recruit;
    public final CompoundTag tag;

    public RecruitsOnWriteSpawnEggEvent(AbstractRecruitEntity recruit, CompoundTag tag) {
        this.recruit = recruit;
        this.tag = tag;
    }
}

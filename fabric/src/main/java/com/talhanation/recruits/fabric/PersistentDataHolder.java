package com.talhanation.recruits.fabric;

import net.minecraft.nbt.CompoundTag;

/**
 * Implemented on entities by a mixin, as Fabric has no equivalent of Forge's persistent data.
 */
public interface PersistentDataHolder {

    String NBT_TAG = "RecruitsData";

    CompoundTag recruits$getPersistentData();
}

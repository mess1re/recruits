package com.talhanation.recruits.fabric.mixin;

import com.talhanation.recruits.fabric.PersistentDataHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin implements PersistentDataHolder {

    @Unique
    private CompoundTag recruits$persistentData;

    @Override
    public CompoundTag recruits$getPersistentData() {
        if (recruits$persistentData == null) {
            recruits$persistentData = new CompoundTag();
        }
        return recruits$persistentData;
    }

    @Inject(method = "saveWithoutId", at = @At("HEAD"))
    private void recruits$savePersistentData(CompoundTag tag, CallbackInfoReturnable<CompoundTag> cir) {
        if (recruits$persistentData != null && !recruits$persistentData.isEmpty()) {
            tag.put(NBT_TAG, recruits$persistentData.copy());
        }
    }

    @Inject(method = "load", at = @At("HEAD"))
    private void recruits$loadPersistentData(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains(NBT_TAG, Tag.TAG_COMPOUND)) {
            recruits$persistentData = tag.getCompound(NBT_TAG);
        }
    }
}

package com.talhanation.recruits.platform;

import com.talhanation.recruits.entities.AbstractInventoryEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
//? if >=1.21 {
/*import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
*///?} else {
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
//?}

import javax.annotation.Nullable;
import java.util.ServiceLoader;

/**
 * Loader specific functionality. Each loader project provides an implementation as a service.
 */
public interface PlatformHooks {

    PlatformHooks INSTANCE = ServiceLoader.load(PlatformHooks.class, PlatformHooks.class.getClassLoader())
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No PlatformHooks implementation found"));

    /**
     * Sub tag of {@link #getPersistentData(Entity)} that is kept when a player respawns.
     */
    String PERSISTED_NBT_TAG = "PlayerPersisted";

    UpdateStatus getUpdateStatus();

    /**
     * Custom data saved together with the entity.
     */
    CompoundTag getPersistentData(Entity entity);

    /**
     * Custom data saved together with the block entity.
     */
    CompoundTag getPersistentData(BlockEntity blockEntity);

    // Item and block behaviour that loaders let other mods customize.

    boolean canEquip(ItemStack stack, EquipmentSlot slot, LivingEntity entity);

    @Nullable
    EquipmentSlot getEquipmentSlot(ItemStack stack);

    boolean canBlockWithShield(ItemStack stack);

    boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker);

    boolean isBed(BlockState state, BlockGetter level, BlockPos pos, LivingEntity sleeper);

    /**
     * Whether the entity is deep enough in a non-vanilla fluid it can swim in.
     */
    boolean isInSwimmableFluid(LivingEntity entity, double minHeight);

    boolean canEntityGrief(Level level, Entity entity);

    /**
     * Forces or releases a chunk on behalf of the owner entity.
     */
    void forceChunk(ServerLevel level, Entity owner, int chunkX, int chunkZ, boolean add);

    /**
     * Spawns a Corpse mod corpse holding the inventory, if that integration exists on this loader.
     *
     * @return {@code true} if a corpse was spawned and the inventory must not be dropped.
     */
    boolean spawnCorpse(AbstractInventoryEntity entity);

    //? if >=1.21 {
    /*Holder<Attribute> swimSpeed();
    *///?} else {
    Attribute swimSpeed();

    Attribute entityReach();

    AbstractArrow customArrow(BowItem bow, AbstractArrow arrow);

    @Nullable
    FoodProperties getFoodProperties(ItemStack stack, LivingEntity entity);

    // Path types other mods assign to their blocks and fluids; null falls back to vanilla.

    @Nullable
    BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob);

    @Nullable
    BlockPathTypes getFluidPathType(FluidState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, boolean canFluidLog);

    @Nullable
    BlockPathTypes getAdjacentBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, BlockPathTypes originalType);

    @Nullable
    BlockPathTypes getAdjacentFluidPathType(FluidState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, BlockPathTypes originalType);
    //?}

    enum UpdateStatus {
        OUTDATED,
        FAILED,
        OTHER
    }
}

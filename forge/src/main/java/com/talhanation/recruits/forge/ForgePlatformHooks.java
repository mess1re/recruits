package com.talhanation.recruits.forge;

import com.talhanation.recruits.Main;
import com.talhanation.recruits.entities.AbstractInventoryEntity;
import com.talhanation.recruits.forge.compat.corpse.RecruitCorpseSpawner;
import com.talhanation.recruits.platform.PlatformHooks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowItem;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;

public class ForgePlatformHooks implements PlatformHooks {

    @Override
    public UpdateStatus getUpdateStatus() {
        VersionChecker.Status status = VersionChecker.getResult(ModList.get().getModContainerById(Main.MOD_ID).get().getModInfo()).status();
        return switch (status) {
            case OUTDATED -> UpdateStatus.OUTDATED;
            case FAILED -> UpdateStatus.FAILED;
            default -> UpdateStatus.OTHER;
        };
    }

    @Override
    public CompoundTag getPersistentData(Entity entity) {
        return entity.getPersistentData();
    }

    @Override
    public CompoundTag getPersistentData(BlockEntity blockEntity) {
        return blockEntity.getPersistentData();
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot slot, LivingEntity entity) {
        return stack.canEquip(slot, entity);
    }

    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return stack.getEquipmentSlot();
    }

    @Override
    public boolean canBlockWithShield(ItemStack stack) {
        return stack.canPerformAction(ToolActions.SHIELD_BLOCK);
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return stack.canDisableShield(shield, entity, attacker);
    }

    @Override
    public boolean isBed(BlockState state, BlockGetter level, BlockPos pos, LivingEntity sleeper) {
        return state.isBed(level, pos, sleeper);
    }

    @Override
    public boolean isInSwimmableFluid(LivingEntity entity, double minHeight) {
        return entity.isInFluidType((fluidType, height) -> entity.canSwimInFluidType(fluidType) && height > minHeight);
    }

    @Override
    public boolean canEntityGrief(Level level, Entity entity) {
        return ForgeEventFactory.getMobGriefingEvent(level, entity);
    }

    @Override
    public void forceChunk(ServerLevel level, Entity owner, int chunkX, int chunkZ, boolean add) {
        ForgeChunkManager.forceChunk(level, Main.MOD_ID, owner, chunkX, chunkZ, add, false);
    }

    @Override
    public boolean spawnCorpse(AbstractInventoryEntity entity) {
        return RecruitCorpseSpawner.spawnCorpse(entity);
    }

    @Override
    public BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, Mob mob) {
        return state.getBlockPathType(level, pos, mob);
    }

    @Override
    public BlockPathTypes getFluidPathType(FluidState state, BlockGetter level, BlockPos pos, Mob mob, boolean canFluidLog) {
        return state.getBlockPathType(level, pos, mob, canFluidLog);
    }

    @Override
    public BlockPathTypes getAdjacentBlockPathType(BlockState state, BlockGetter level, BlockPos pos, Mob mob, BlockPathTypes originalType) {
        return state.getAdjacentBlockPathType(level, pos, mob, originalType);
    }

    @Override
    public BlockPathTypes getAdjacentFluidPathType(FluidState state, BlockGetter level, BlockPos pos, Mob mob, BlockPathTypes originalType) {
        return state.getAdjacentBlockPathType(level, pos, mob, originalType);
    }

    @Override
    public Attribute swimSpeed() {
        return ForgeMod.SWIM_SPEED.get();
    }

    @Override
    public Attribute entityReach() {
        return ForgeMod.ENTITY_REACH.get();
    }

    @Override
    public AbstractArrow customArrow(BowItem bow, AbstractArrow arrow) {
        return bow.customArrow(arrow);
    }

    @Override
    public FoodProperties getFoodProperties(ItemStack stack, LivingEntity entity) {
        return stack.getItem().getFoodProperties(stack, entity);
    }
}

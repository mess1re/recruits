package com.talhanation.recruits.fabric;

import com.talhanation.recruits.entities.AbstractInventoryEntity;
import com.talhanation.recruits.platform.PlatformHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
//? if >=1.21 {
/*import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
*///?} else {
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
//?}

import java.util.Map;
import java.util.WeakHashMap;

public class FabricPlatformHooks implements PlatformHooks {

    // Block entities only hold transient state here, so it is not saved with the world.
    private static final Map<BlockEntity, CompoundTag> BLOCK_ENTITY_DATA = new WeakHashMap<>();

    @Override
    public UpdateStatus getUpdateStatus() {
        return UpdateStatus.OTHER;
    }

    @Override
    public CompoundTag getPersistentData(Entity entity) {
        return ((PersistentDataHolder) entity).recruits$getPersistentData();
    }

    @Override
    public CompoundTag getPersistentData(BlockEntity blockEntity) {
        synchronized (BLOCK_ENTITY_DATA) {
            return BLOCK_ENTITY_DATA.computeIfAbsent(blockEntity, key -> new CompoundTag());
        }
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot slot, LivingEntity entity) {
        //? if >=1.21 {
        /*return entity.getEquipmentSlotForItem(stack) == slot;
        *///?} else {
        return Mob.getEquipmentSlotForItem(stack) == slot;
        //?}
    }

    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return null;
    }

    @Override
    public boolean canBlockWithShield(ItemStack stack) {
        return stack.getItem() instanceof ShieldItem;
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return stack.getItem() instanceof AxeItem;
    }

    @Override
    public boolean isBed(BlockState state, BlockGetter level, BlockPos pos, LivingEntity sleeper) {
        return state.getBlock() instanceof BedBlock;
    }

    @Override
    public boolean isInSwimmableFluid(LivingEntity entity, double minHeight) {
        return false;
    }

    @Override
    public boolean canEntityGrief(Level level, Entity entity) {
        return level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
    }

    @Override
    public void forceChunk(ServerLevel level, Entity owner, int chunkX, int chunkZ, boolean add) {
        FabricChunkTickets.forceChunk(level, owner.getUUID(), new ChunkPos(chunkX, chunkZ), add);
    }

    @Override
    public boolean spawnCorpse(AbstractInventoryEntity entity) {
        return false;
    }

    //? if >=1.21 {
    /*@Override
    public Holder<Attribute> swimSpeed() {
        return FabricAttributes.SWIM_SPEED;
    }
    *///?} else {
    @Override
    public Attribute swimSpeed() {
        return FabricAttributes.SWIM_SPEED;
    }

    @Override
    public Attribute entityReach() {
        return FabricAttributes.ENTITY_REACH;
    }

    @Override
    public AbstractArrow customArrow(BowItem bow, AbstractArrow arrow) {
        return arrow;
    }

    @Override
    public FoodProperties getFoodProperties(ItemStack stack, LivingEntity entity) {
        return stack.getItem().getFoodProperties();
    }

    @Override
    public BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, Mob mob) {
        return null;
    }

    @Override
    public BlockPathTypes getFluidPathType(FluidState state, BlockGetter level, BlockPos pos, Mob mob, boolean canFluidLog) {
        return null;
    }

    @Override
    public BlockPathTypes getAdjacentBlockPathType(BlockState state, BlockGetter level, BlockPos pos, Mob mob, BlockPathTypes originalType) {
        return null;
    }

    @Override
    public BlockPathTypes getAdjacentFluidPathType(FluidState state, BlockGetter level, BlockPos pos, Mob mob, BlockPathTypes originalType) {
        return null;
    }
    //?}
}

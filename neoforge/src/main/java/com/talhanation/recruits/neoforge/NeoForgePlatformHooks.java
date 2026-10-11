package com.talhanation.recruits.neoforge;

import com.talhanation.recruits.Main;
import com.talhanation.recruits.entities.AbstractInventoryEntity;
import com.talhanation.recruits.neoforge.compat.corpse.RecruitCorpseSpawner;
import com.talhanation.recruits.platform.PlatformHooks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.world.chunk.TicketController;
import net.neoforged.neoforge.event.EventHooks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.fml.ModList;
import net.neoforged.fml.VersionChecker;

public class NeoForgePlatformHooks implements PlatformHooks {

    static final TicketController CHUNK_TICKETS = new TicketController(ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "recruit_entities"));

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
        return stack.canPerformAction(ItemAbilities.SHIELD_BLOCK);
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
        return EventHooks.canEntityGrief(level, entity);
    }

    @Override
    public void forceChunk(ServerLevel level, Entity owner, int chunkX, int chunkZ, boolean add) {
        CHUNK_TICKETS.forceChunk(level, owner, chunkX, chunkZ, add, false);
    }

    @Override
    public boolean spawnCorpse(AbstractInventoryEntity entity) {
        return RecruitCorpseSpawner.spawnCorpse(entity);
    }

    @Override
    public Holder<Attribute> swimSpeed() {
        return NeoForgeMod.SWIM_SPEED;
    }
}

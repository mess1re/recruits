package com.talhanation.recruits;

import com.talhanation.recruits.config.RecruitsServerConfig;
import com.talhanation.recruits.platform.PlatformEvents;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class DamageEvent {

    public void register() {
        PlayerEvent.ATTACK_ENTITY.register(this::onPlayerAttack);
        PlatformEvents.LIVING_KNOCKBACK.register(this::onKnockback);
        EntityEvent.LIVING_HURT.register(this::onEntityHurt);
        PlayerEvent.ATTACK_ENTITY.register(this::onEntityHurtByPlayer);
    }

    public EventResult onPlayerAttack(Player player, Level level, Entity target, InteractionHand hand, EntityHitResult hitResult) {
        if (player.getCommandSenderWorld().isClientSide()) {
            return EventResult.pass();
        }
        float str = player.getAttackStrengthScale(0);
        if (str <= 0.1) {
            return EventResult.interruptFalse();
        }
        if (str <= 0.75) {
            if (target != null && target instanceof LivingEntity) {
                ((LivingEntity)target).swinging = true;
            }
        }
        return EventResult.pass();
    }


    public EventResult onKnockback(LivingEntity entity) {
        if (entity.swinging) {
            entity.swinging = false;
            return EventResult.interruptFalse();
        }
        return EventResult.pass();
    }

    public EventResult onEntityHurt(LivingEntity target, DamageSource source, float amount) {
        if (target.getCommandSenderWorld().isClientSide()) {
            return EventResult.pass();
        }

        Entity sourceEntity = target;

        if(!RecruitsServerConfig.NoDamageImmunity.get()) return EventResult.pass();

        if (target.getCommandSenderWorld().isClientSide()) {
            return EventResult.pass();
        }

        //Velocity Damage
        if(source != null && sourceEntity != null){

        }

        //NO Damage Immunity
        if(!RecruitsServerConfig.NoDamageImmunity.get()) return EventResult.pass();


        if (source != null && RecruitsServerConfig.AcceptedDamagesourceImmunity.get().contains(source.getMsgId())) {
            return EventResult.pass();
        }
        target.invulnerableTime = 0;
        return EventResult.pass();
    }

    public EventResult onEntityHurtByPlayer(Player player, Level level, Entity target, InteractionHand hand, EntityHitResult hitResult) {
        if(target.getFirstPassenger() instanceof LivingEntity passenger){
            if (!RecruitEvents.canHarmTeam(player, passenger)) {
                return EventResult.interruptFalse();
            }
        }
        return EventResult.pass();
    }
}

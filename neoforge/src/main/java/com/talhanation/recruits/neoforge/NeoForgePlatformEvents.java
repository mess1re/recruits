package com.talhanation.recruits.neoforge;

import com.talhanation.recruits.platform.PlatformEvents;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

final class NeoForgePlatformEvents {

    private NeoForgePlatformEvents() {
    }

    static void register() {
        NeoForge.EVENT_BUS.addListener((EntityJoinLevelEvent event) ->
                PlatformEvents.ENTITY_JOIN.invoker().join(event.getEntity(), event.getLevel()));

        NeoForge.EVENT_BUS.addListener((EntityLeaveLevelEvent event) ->
                PlatformEvents.ENTITY_LEAVE.invoker().leave(event.getEntity(), event.getLevel()));

        NeoForge.EVENT_BUS.addListener((EntityTickEvent.Post event) -> {
            if (event.getEntity() instanceof LivingEntity living) {
                PlatformEvents.LIVING_TICK.invoker().tick(living);
            }
        });

        NeoForge.EVENT_BUS.addListener((EntityTeleportEvent.TeleportCommand event) -> onTeleport(event));
        NeoForge.EVENT_BUS.addListener((EntityTeleportEvent.SpreadPlayersCommand event) -> onTeleport(event));

        NeoForge.EVENT_BUS.addListener((ProjectileImpactEvent event) -> {
            if (PlatformEvents.PROJECTILE_IMPACT.invoker().impact(event.getProjectile(), event.getRayTraceResult()).isFalse()) {
                event.setCanceled(true);
            }
        });

        NeoForge.EVENT_BUS.addListener((LivingKnockBackEvent event) -> {
            if (PlatformEvents.LIVING_KNOCKBACK.invoker().knockBack(event.getEntity()).isFalse()) {
                event.setCanceled(true);
            }
        });
    }

    private static void onTeleport(EntityTeleportEvent event) {
        PlatformEvents.ENTITY_TELEPORT.invoker().teleport(event.getEntity(), event.getTargetX(), event.getTargetY(), event.getTargetZ());
    }
}

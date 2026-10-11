package com.talhanation.recruits.forge;

import com.talhanation.recruits.platform.PlatformEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;

final class ForgePlatformEvents {

    private ForgePlatformEvents() {
    }

    static void register() {
        MinecraftForge.EVENT_BUS.addListener((EntityJoinLevelEvent event) ->
                PlatformEvents.ENTITY_JOIN.invoker().join(event.getEntity(), event.getLevel()));

        MinecraftForge.EVENT_BUS.addListener((EntityLeaveLevelEvent event) ->
                PlatformEvents.ENTITY_LEAVE.invoker().leave(event.getEntity(), event.getLevel()));

        MinecraftForge.EVENT_BUS.addListener((LivingEvent.LivingTickEvent event) ->
                PlatformEvents.LIVING_TICK.invoker().tick(event.getEntity()));

        MinecraftForge.EVENT_BUS.addListener((EntityTeleportEvent.TeleportCommand event) -> onTeleport(event));
        MinecraftForge.EVENT_BUS.addListener((EntityTeleportEvent.SpreadPlayersCommand event) -> onTeleport(event));

        MinecraftForge.EVENT_BUS.addListener((ProjectileImpactEvent event) -> {
            if (PlatformEvents.PROJECTILE_IMPACT.invoker().impact(event.getProjectile(), event.getRayTraceResult()).isFalse()) {
                event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
            }
        });

        MinecraftForge.EVENT_BUS.addListener((LivingKnockBackEvent event) -> {
            if (PlatformEvents.LIVING_KNOCKBACK.invoker().knockBack(event.getEntity()).isFalse()) {
                event.setCanceled(true);
            }
        });
    }

    private static void onTeleport(EntityTeleportEvent event) {
        PlatformEvents.ENTITY_TELEPORT.invoker().teleport(event.getEntity(), event.getTargetX(), event.getTargetY(), event.getTargetZ());
    }
}

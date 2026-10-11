package com.talhanation.recruits.platform;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

/**
 * Events Architectury does not provide. They are fired by the loader projects.
 */
public final class PlatformEvents {

    /**
     * Fired on both sides whenever an entity is added to a level: spawned, loaded with its chunk, or a player joining
     * or changing dimension. Architectury's entity add event misses players and loaded entities on Fabric.
     */
    public static final Event<EntityJoin> ENTITY_JOIN = EventFactory.createLoop();
    public static final Event<EntityLeave> ENTITY_LEAVE = EventFactory.createLoop();
    public static final Event<LivingTick> LIVING_TICK = EventFactory.createLoop();
    /**
     * Fired for teleport commands only, not for ender pearls, chorus fruit or enderman teleports.
     */
    public static final Event<EntityTeleport> ENTITY_TELEPORT = EventFactory.createLoop();
    /**
     * {@link EventResult#interruptFalse()} lets the projectile pass through the hit entity.
     */
    public static final Event<ProjectileImpact> PROJECTILE_IMPACT = EventFactory.createEventResult();
    /**
     * {@link EventResult#interruptFalse()} cancels the knockback.
     */
    public static final Event<LivingKnockBack> LIVING_KNOCKBACK = EventFactory.createEventResult();

    private PlatformEvents() {
    }

    public interface EntityJoin {
        void join(Entity entity, Level level);
    }

    public interface EntityLeave {
        void leave(Entity entity, Level level);
    }

    public interface LivingTick {
        void tick(LivingEntity entity);
    }

    public interface EntityTeleport {
        void teleport(Entity entity, double targetX, double targetY, double targetZ);
    }

    public interface ProjectileImpact {
        EventResult impact(Projectile projectile, HitResult hitResult);
    }

    public interface LivingKnockBack {
        EventResult knockBack(LivingEntity entity);
    }
}

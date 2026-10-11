package com.talhanation.recruits;

import com.talhanation.recruits.entities.*;
import com.talhanation.recruits.init.ModEntityTypes;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;

import java.util.Random;

public class AttributeEvent {
    protected final Random random = new Random();

    public static void entityAttributeEvent() {
        //EntityAttributeRegistry.register(ModEntityTypes.ASSASSIN, AssassinEntity::setAttributes);
        //EntityAttributeRegistry.register(ModEntityTypes.ASSASSIN_LEADER, AssassinLeaderEntity::setAttributes);
        EntityAttributeRegistry.register(ModEntityTypes.BOWMAN, BowmanEntity::setAttributes);
        EntityAttributeRegistry.register(ModEntityTypes.CROSSBOWMAN, CrossBowmanEntity::setAttributes);
        EntityAttributeRegistry.register(ModEntityTypes.NOMAD, NomadEntity::setAttributes);
        EntityAttributeRegistry.register(ModEntityTypes.RECRUIT, RecruitEntity::setAttributes);
        EntityAttributeRegistry.register(ModEntityTypes.RECRUIT_SHIELDMAN, RecruitShieldmanEntity::setAttributes);
        EntityAttributeRegistry.register(ModEntityTypes.HORSEMAN, HorsemanEntity::setAttributes);
        EntityAttributeRegistry.register(ModEntityTypes.MESSENGER, MessengerEntity::setAttributes);
        EntityAttributeRegistry.register(ModEntityTypes.PATROL_LEADER, CommanderEntity::setAttributes);
        EntityAttributeRegistry.register(ModEntityTypes.CAPTAIN, CaptainEntity::setAttributes);
        EntityAttributeRegistry.register(ModEntityTypes.SCOUT, ScoutEntity::setAttributes);
        EntityAttributeRegistry.register(ModEntityTypes.VILLAGER_NOBLE, VillagerNobleEntity::setAttributes);
        EntityAttributeRegistry.register(ModEntityTypes.SIEGE_ENGINEER, SiegeEngineerEntity::setAttributes);

    }
}

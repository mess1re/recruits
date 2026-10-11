package com.talhanation.recruits.util;

import com.talhanation.recruits.platform.PlatformHooks;
import net.minecraft.world.entity.ai.attributes.Attribute;
//? if >=1.21 {
/*import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attributes;
*///?}

public final class RecruitsAttributes {

    private RecruitsAttributes() {
    }

    //? if >=1.21 {
    /*public static Holder<Attribute> swimSpeed() {
        return PlatformHooks.INSTANCE.swimSpeed();
    }

    public static Holder<Attribute> entityReach() {
        return Attributes.ENTITY_INTERACTION_RANGE;
    }
    *///?} else {
    public static Attribute swimSpeed() {
        return PlatformHooks.INSTANCE.swimSpeed();
    }

    public static Attribute entityReach() {
        return PlatformHooks.INSTANCE.entityReach();
    }
    //?}
}

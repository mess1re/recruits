package com.talhanation.recruits.util;

import net.minecraft.world.entity.ai.attributes.Attribute;
//? if forge {
import net.minecraftforge.common.ForgeMod;
//?} else {
/*import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attributes;
*///?}

public final class RecruitsAttributes {

    private RecruitsAttributes() {
    }

    //? if forge {
    public static Attribute swimSpeed() {
        return ForgeMod.SWIM_SPEED.get();
    }

    public static Attribute entityReach() {
        return ForgeMod.ENTITY_REACH.get();
    }
    //?} else {
    /*public static Holder<Attribute> swimSpeed() {
        return Attributes.WATER_MOVEMENT_EFFICIENCY;
    }

    public static Holder<Attribute> entityReach() {
        return Attributes.ENTITY_INTERACTION_RANGE;
    }
    *///?}
}

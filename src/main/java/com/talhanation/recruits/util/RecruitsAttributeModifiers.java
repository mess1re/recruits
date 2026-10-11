package com.talhanation.recruits.util;

import com.talhanation.recruits.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
//? if >=1.21 {
/*import java.util.UUID;
*///?}

public final class RecruitsAttributeModifiers {

    private RecruitsAttributeModifiers() {
    }

    //? if >=1.21 {
    /*/^*
     * Modifiers are identified by their id since 1.21. Before, each modifier got a random UUID and the name was only a label,
     * so modifiers with the same name stack, e.g. one per recruit level.
     ^/
    private static String uniquePath(String name) {
        return name + "/" + UUID.randomUUID();
    }

    *///?}
    public static AttributeModifier addition(String name, double amount) {
        //? if <1.21 {
        return new AttributeModifier(name, amount, AttributeModifier.Operation.ADDITION);
        //?} else {
        /*String path = uniquePath(name);
        return new AttributeModifier(new ResourceLocation(Main.MOD_ID, path), amount, AttributeModifier.Operation.ADD_VALUE);
        *///?}
    }

    public static AttributeModifier multiplyBase(String name, double amount) {
        //? if <1.21 {
        return new AttributeModifier(name, amount, AttributeModifier.Operation.MULTIPLY_BASE);
        //?} else {
        /*String path = uniquePath(name);
        return new AttributeModifier(new ResourceLocation(Main.MOD_ID, path), amount, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        *///?}
    }
}

package com.talhanation.recruits.util;

import com.talhanation.recruits.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public final class RecruitsAttributeModifiers {

    private RecruitsAttributeModifiers() {
    }

    public static AttributeModifier addition(String name, double amount) {
        //? if forge {
        return new AttributeModifier(name, amount, AttributeModifier.Operation.ADDITION);
        //?} else {
        /*return new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, name), amount, AttributeModifier.Operation.ADD_VALUE);
        *///?}
    }

    public static AttributeModifier multiplyBase(String name, double amount) {
        //? if forge {
        return new AttributeModifier(name, amount, AttributeModifier.Operation.MULTIPLY_BASE);
        //?} else {
        /*return new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, name), amount, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        *///?}
    }
}

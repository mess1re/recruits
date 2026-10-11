package com.talhanation.recruits.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;

public final class RecruitsComponents {
    private RecruitsComponents() {
    }

    public static String toJson(Component component, HolderLookup.Provider registries) {
        //? if <1.21 {
        return Component.Serializer.toJson(component);
        //?} else {
        /*return Component.Serializer.toJson(component, registries);
        *///?}
    }

    public static Component fromJson(String json, HolderLookup.Provider registries) {
        //? if <1.21 {
        return Component.Serializer.fromJson(json);
        //?} else {
        /*return Component.Serializer.fromJson(json, registries);
        *///?}
    }
}

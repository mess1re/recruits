package com.talhanation.recruits.util;

import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecruitsComponentsTest {
    @Test
    void copiedNameRetainsTranslationStyleAndSiblings() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        var registries = VanillaRegistries.createLookup();
        Component name = Component.translatable("entity.minecraft.villager")
                .withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD)
                .append(Component.literal(" Veteran").withStyle(ChatFormatting.ITALIC));
        assertEquals(name, RecruitsComponents.fromJson(RecruitsComponents.toJson(name, registries), registries));
    }
}

package com.talhanation.recruits.init;

import net.minecraft.client.KeyMapping;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import org.lwjgl.glfw.GLFW;

public class ModShortcuts {
    public static KeyMapping COMMAND_SCREEN_KEY;
    public static KeyMapping TEAM_SCREEN_KEY;
    public static KeyMapping MAP_SCREEN_KEY;
    @Environment(EnvType.CLIENT)
    public static void registerBindings() {
        COMMAND_SCREEN_KEY = new KeyMapping("key.recruits.command_screen_key", GLFW.GLFW_KEY_R, "category.recruits");
        TEAM_SCREEN_KEY =  new KeyMapping("key.recruits.team_screen_key", GLFW.GLFW_KEY_U, "category.recruits");
        MAP_SCREEN_KEY =  new KeyMapping("key.recruits.map_screen_key", GLFW.GLFW_KEY_M, "category.recruits");

        KeyMappingRegistry.register(COMMAND_SCREEN_KEY);
        KeyMappingRegistry.register(TEAM_SCREEN_KEY);
        KeyMappingRegistry.register(MAP_SCREEN_KEY);
    }
}

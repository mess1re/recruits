package com.talhanation.recruits.forge;

import com.talhanation.recruits.Main;
import com.talhanation.recruits.client.RecruitsClient;
import com.talhanation.recruits.config.RecruitsClientConfig;
import com.talhanation.recruits.config.RecruitsServerConfig;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;

import static com.talhanation.recruits.Main.MOD_ID;

@Mod(MOD_ID)
public class RecruitsForge {
    public RecruitsForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(MOD_ID, modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, RecruitsServerConfig.SERVER);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, RecruitsClientConfig.CLIENT);
        RecruitsClientConfig.loadConfig(RecruitsClientConfig.CLIENT, FMLPaths.CONFIGDIR.get().resolve("recruits-client.toml"));

        ForgePlatformEvents.register();
        ForgeCapabilities.register();

        Main.init();

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ForgeClientPlatformEvents.register(modEventBus);
            RecruitsClient.init();
            ClientLifecycleEvent.CLIENT_SETUP.register(minecraft -> RecruitsClient.setup());
        }
    }
}

package com.talhanation.recruits.neoforge;

import com.talhanation.recruits.Main;
import com.talhanation.recruits.client.RecruitsClient;
import com.talhanation.recruits.config.RecruitsClientConfig;
import com.talhanation.recruits.config.RecruitsServerConfig;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.world.chunk.RegisterTicketControllersEvent;

import static com.talhanation.recruits.Main.MOD_ID;

@Mod(MOD_ID)
public class RecruitsNeoForge {
    public RecruitsNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, RecruitsServerConfig.SERVER);
        modContainer.registerConfig(ModConfig.Type.CLIENT, RecruitsClientConfig.CLIENT);

        NeoForgePlatformEvents.register();
        modEventBus.addListener(NeoForgeCapabilities::register);
        modEventBus.addListener((RegisterTicketControllersEvent event) -> event.register(NeoForgePlatformHooks.CHUNK_TICKETS));

        Main.init();

        if (FMLEnvironment.dist == Dist.CLIENT) {
            NeoForgeClientPlatformEvents.register(modEventBus);
            RecruitsClient.init();
            ClientLifecycleEvent.CLIENT_SETUP.register(minecraft -> RecruitsClient.setup());
        }
    }
}

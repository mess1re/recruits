package com.talhanation.recruits.fabric;

import com.talhanation.recruits.Main;
import com.talhanation.recruits.config.RecruitsClientConfig;
import com.talhanation.recruits.config.RecruitsServerConfig;
import net.fabricmc.api.ModInitializer;
//? if >=1.21 {
/*import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.neoforged.fml.config.ModConfig;
*///?} else {
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.minecraftforge.fml.config.ModConfig;
//?}

import static com.talhanation.recruits.Main.MOD_ID;

public class RecruitsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        //? if >=1.21 {
        /*NeoForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.SERVER, RecruitsServerConfig.SERVER);
        NeoForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.CLIENT, RecruitsClientConfig.CLIENT);
        *///?} else {
        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.SERVER, RecruitsServerConfig.SERVER);
        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.CLIENT, RecruitsClientConfig.CLIENT);
        //?}

        FabricAttributes.register();
        FabricPlatformEvents.register();

        Main.init();
    }
}

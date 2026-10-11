package com.talhanation.recruits.fabric;

import com.talhanation.recruits.client.RecruitsClient;
import net.fabricmc.api.ClientModInitializer;

public class RecruitsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FabricClientPlatformEvents.register();
        RecruitsClient.init();
        // Architectury fires CLIENT_SETUP from its own client entrypoint, which may run before this one.
        RecruitsClient.setup();
    }
}

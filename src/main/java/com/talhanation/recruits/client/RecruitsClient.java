package com.talhanation.recruits.client;

import com.talhanation.recruits.UpdateChecker;
import com.talhanation.recruits.client.events.ClientEvent;
import com.talhanation.recruits.client.events.ClientPlayerEvents;
import com.talhanation.recruits.client.events.CommandCategoryManager;
import com.talhanation.recruits.client.events.KeyEvents;
import com.talhanation.recruits.client.gui.overlay.ClaimOverlayManager;
import com.talhanation.recruits.client.gui.commandscreen.CombatCategory;
import com.talhanation.recruits.client.gui.commandscreen.MovementCategory;
import com.talhanation.recruits.client.gui.commandscreen.OtherCategory;
import com.talhanation.recruits.init.ModScreens;
import com.talhanation.recruits.init.ModShortcuts;

public class RecruitsClient {
    /**
     * Called by every loader when the mod is constructed on the client.
     */
    public static void init() {
        ModShortcuts.registerBindings();
        ClientEvent.register();
        ModScreens.registerMenus();
    }

    /**
     * Called by every loader once the client is set up.
     */
    public static void setup() {
        new UpdateChecker().registerClient();
        new KeyEvents().register();
        new ClientPlayerEvents().register();
        new ClaimOverlayManager().register();

        CommandCategoryManager.register(new MovementCategory(), -2);
        CommandCategoryManager.register(new CombatCategory(), -3);
        CommandCategoryManager.register(new OtherCategory(), -1);
    }
}

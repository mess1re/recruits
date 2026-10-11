package com.talhanation.recruits.client.events;

import com.talhanation.recruits.network.RecruitsNetwork;

import com.talhanation.recruits.CommandEvents;
import com.talhanation.recruits.Main;
import com.talhanation.recruits.client.gui.worldmap.WorldMapScreen;
import com.talhanation.recruits.client.gui.faction.FactionMainScreen;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import com.talhanation.recruits.init.ModShortcuts;
import com.talhanation.recruits.network.MessageWriteSpawnEgg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import com.talhanation.recruits.platform.ClientPlatformEvents;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;

@Environment(EnvType.CLIENT)

public class KeyEvents {
    public void register() {
        ClientRawInputEvent.KEY_PRESSED.register(this::onKeyInput);
        ClientPlatformEvents.PICK_BLOCK.register(this::onPlayerPick);
    }

    public EventResult onKeyInput(Minecraft minecraft, int keyCode, int scanCode, int action, int modifiers) {
        LocalPlayer clientPlayerEntity = minecraft.player;
        if (clientPlayerEntity == null)
            return EventResult.pass();

        if (ModShortcuts.COMMAND_SCREEN_KEY.isDown()) {
            CommandEvents.openCommandScreen(clientPlayerEntity);
        }

        if (ModShortcuts.TEAM_SCREEN_KEY.isDown()) {
            minecraft.setScreen(new FactionMainScreen(clientPlayerEntity));
        }

        if (ModShortcuts.MAP_SCREEN_KEY.isDown()) {
            if (minecraft.level != null && minecraft.level.dimension() == Level.OVERWORLD) {
                minecraft.setScreen(new WorldMapScreen());
            }
        }
        return EventResult.pass();
    }

    public EventResult onPlayerPick(){
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer clientPlayerEntity = minecraft.player;
        if (clientPlayerEntity == null || !clientPlayerEntity.isCreative())
            return EventResult.pass();


        Entity target = ClientEvent.getEntityByLooking();
        if(target instanceof AbstractRecruitEntity recruitEntity){
            RecruitsNetwork.sendToServer(new MessageWriteSpawnEgg(recruitEntity.getUUID()));
            return EventResult.interruptFalse();
        }
        return EventResult.pass();
    }

}

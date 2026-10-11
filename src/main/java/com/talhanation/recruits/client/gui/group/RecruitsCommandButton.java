package com.talhanation.recruits.client.gui.group;

import net.minecraft.network.chat.Component;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import com.talhanation.recruits.client.gui.widgets.ExtendedButton;

@Environment(EnvType.CLIENT)
public class RecruitsCommandButton extends ExtendedButton {

    public RecruitsCommandButton(int xPos, int yPos, Component displayString, OnPress handler) {
        super(xPos - 40, yPos - 10, 80, 20, displayString, handler);
    }
}

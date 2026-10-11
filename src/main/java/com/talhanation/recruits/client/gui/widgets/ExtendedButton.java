package com.talhanation.recruits.client.gui.widgets;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

public class ExtendedButton extends Button {

    public ExtendedButton(int x, int y, int width, int height, Component message, OnPress onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
    }

    @Override
    public void renderString(GuiGraphics guiGraphics, Font font, int color) {
        FormattedText text = getMessage();
        int maxWidth = width - 6;
        if (font.width(text) > maxWidth) {
            text = FormattedText.composite(font.substrByWidth(text, maxWidth - font.width("...")), FormattedText.of("..."));
        }
        guiGraphics.drawCenteredString(font, Language.getInstance().getVisualOrder(text), getX() + width / 2, getY() + (height - 8) / 2, getFGColor());
    }

    public int getFGColor() {
        return active ? 0xFFFFFF : 0xA0A0A0;
    }
}

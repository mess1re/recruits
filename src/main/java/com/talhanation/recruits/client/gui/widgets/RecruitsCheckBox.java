package com.talhanation.recruits.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class RecruitsCheckBox extends AbstractButton {

    //? if <1.21 {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/checkbox.png");
    //?}
    private static final int TEXT_COLOR = 14737632;
    private final Consumer<Boolean> onToggle;
    private final boolean showLabel;
    private boolean selected;

    public RecruitsCheckBox(int x, int y, int width, int height, Component label, boolean selected, Consumer<Boolean> onToggle) {
        this(x, y, width, height, label, selected, true, onToggle);
    }

    public RecruitsCheckBox(int x, int y, int width, int height, Component label, boolean selected, boolean showLabel, Consumer<Boolean> onToggle) {
        super(x, y, width, height, label);
        this.selected = selected;
        this.showLabel = showLabel;
        this.onToggle = onToggle;

    }

    @Override
    public void onPress() {
        this.selected = !this.selected;
        if (onToggle != null) {
            onToggle.accept(this.selected());
        }
    }

    public boolean selected() {
        return this.selected;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        RenderSystem.enableDepthTest();

        int alpha = Mth.ceil(this.alpha * 255.0F);
        int bgColor = (0x80 << 24); // 0x80 = 128 = 50% Alpha
        graphics.fill(getX(), getY(), getX() + width, getY() + height, bgColor);

        graphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        //? if <1.21 {
        graphics.blit(TEXTURE, this.getX(), this.getY(), this.isFocused() ? 20.0F : 0.0F, this.selected() ? 20.0F : 0.0F, 20, this.height, 64, 64);
        //?} else {
        /*String sprite = selected ? "widget/checkbox_selected" : "widget/checkbox";
        if (this.isHoveredOrFocused()) sprite += "_highlighted";
        graphics.blitSprite(ResourceLocation.withDefaultNamespace(sprite), getX(), getY(), 20, this.height);
        *///?}
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        if (this.showLabel) {
            int textX = this.getX() + 24;
            int textY = this.getY() + (this.height - 8) / 2;
            graphics.drawString(font, this.getMessage(), textX, textY, TEXT_COLOR | (alpha << 24));
        }
    }
}


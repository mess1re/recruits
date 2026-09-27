package com.talhanation.recruits.client.gui.widgets;


import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;

public abstract class ListScreenListBase<T extends ListScreenEntryBase<T>> extends ContainerObjectSelectionList<T> {

    public ListScreenListBase(int width, int height, int y0, int y1, int size) {
        //? if forge {
        super(Minecraft.getInstance(), width, height, y0, y1, size);
        //?} else {
        /*super(Minecraft.getInstance(), width, y1 - y0, y0, size);
        *///?}
    }

    //? if neoforge {
    /*public void updateSize(int width, int height, int y0, int y1) {
        this.setRectangle(width, y1 - y0, 0, y0);
    }
    *///?}

    @Override
    //? if forge {
    public void render(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
    //?} else {
    /*public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
    *///?}
        //? if forge {
        double scale = minecraft.getWindow().getGuiScale();
        RenderSystem.enableScissor((int) ((double) getRowLeft() * scale), (int) ((double) (height - y1) * scale), (int) ((double) (getScrollbarPosition() + 6) * scale), (int) ((double) (height - (height - y1) - y0 - 4) * scale));
        //?} else {
        /*guiGraphics.enableScissor(getRowLeft(), getY(), getScrollbarPosition() + 6, getY() + getHeight() - 4);
        *///?}
        //? if forge {
        super.render(guiGraphics, x, y, partialTicks);
        //?} else {
        /*super.renderWidget(guiGraphics, x, y, partialTicks);
        *///?}
        //? if forge {
        RenderSystem.disableScissor();
        //?} else {
        /*guiGraphics.disableScissor();
        *///?}
    }

}

package com.talhanation.recruits.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.LivingEntity;
//? if >=1.21 {
/*import org.joml.Quaternionf;
import org.joml.Vector3f;
*///?}
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/** Common rendering helpers for Recruits' container screens. */
public class ScreenBase<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

    public static final int FONT_COLOR = 4210752;

    protected ResourceLocation texture;
    protected List<HoverArea> hoverAreas;

    protected ScreenBase(ResourceLocation texture, T container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
        this.texture = texture;
        this.hoverAreas = new ArrayList<>();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
        //? if <1.21 {
        renderBackground(guiGraphics);
        //?}
        super.render(guiGraphics, x, y, partialTicks);
        renderTooltip(guiGraphics, x, y);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        guiGraphics.blit(texture, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    public void drawHoverAreas(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        for (HoverArea hoverArea : hoverAreas) {
            if (hoverArea.tooltip != null && hoverArea.isHovered(leftPos, topPos, mouseX, mouseY)) {
                //? if <1.21 {
                guiGraphics.renderTooltip(font, hoverArea.tooltip.get(), mouseX - leftPos, mouseY - topPos);
                //?} else {
                /*guiGraphics.renderTooltip(font, hoverArea.tooltip.get(), mouseX, mouseY);
                *///?}
            }
        }
    }

    protected void renderRecruitPreview(GuiGraphics guiGraphics, int x, int y, int width, int height,
                                        int mouseX, int mouseY, LivingEntity entity) {
        int scale = Math.max(1, (int) Math.min(29, Math.min(
                (height - 10) / Math.max(2.0F, entity.getBbHeight()),
                (width - 8) / Math.max(1.0F, entity.getBbWidth()))));
        int centreX = x + width / 2;
        int feetY = y + height - 5;
        float lookY = feetY - entity.getBbHeight() * scale / 2.0F - mouseY;
        guiGraphics.enableScissor(x, y, x + width, y + height);
        try {
            renderEntityFollowsMouse(guiGraphics, centreX, feetY, scale, centreX - mouseX, lookY, entity);
        } finally {
            guiGraphics.disableScissor();
        }
    }

    /**
     * Renders the entity with its feet at x, y, looking towards the given offset from the mouse, like 1.20.1 did.
     * Since 1.21 the vanilla method centres the entity in a scissored box and measures the look direction from there.
     */
    public static void renderEntityFollowsMouse(GuiGraphics guiGraphics, int x, int y, int scale, float lookX, float lookY, LivingEntity entity) {
        //? if <1.21 {
        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, x, y, scale, lookX, lookY, entity);
        //?} else {
        /*float yaw = (float) Math.atan(lookX / 40.0F);
        float pitch = (float) Math.atan(lookY / 40.0F);
        Quaternionf pose = new Quaternionf().rotateZ((float) Math.PI);
        Quaternionf cameraOrientation = new Quaternionf().rotateX(pitch * 20.0F * ((float) Math.PI / 180F));
        pose.mul(cameraOrientation);
        float yBodyRot = entity.yBodyRot;
        float yRot = entity.getYRot();
        float xRot = entity.getXRot();
        float yHeadRotO = entity.yHeadRotO;
        float yHeadRot = entity.yHeadRot;
        entity.yBodyRot = 180.0F + yaw * 20.0F;
        entity.setYRot(180.0F + yaw * 40.0F);
        entity.setXRot(-pitch * 20.0F);
        entity.yHeadRot = entity.getYRot();
        entity.yHeadRotO = entity.getYRot();
        InventoryScreen.renderEntityInInventory(guiGraphics, x, y, scale, new Vector3f(), pose, cameraOrientation, entity);
        entity.yBodyRot = yBodyRot;
        entity.setYRot(yRot);
        entity.setXRot(xRot);
        entity.yHeadRotO = yHeadRotO;
        entity.yHeadRot = yHeadRot;
        *///?}
    }

    public int getBlitSize(int amount, int max, int size) {
        return size - (int) (((float) amount / (float) max) * (float) size);
    }

    public void drawCentered(GuiGraphics guiGraphics, Component text, int y, int color) {
        drawCentered(guiGraphics, text, imageWidth / 2, y, color);
    }

    public void drawCentered(GuiGraphics guiGraphics, Component text, int x, int y, int color) {
        drawCentered(font, guiGraphics, text, x, y, color);
    }

    public static void drawCentered(Font font, GuiGraphics guiGraphics, Component text, int x, int y, int color) {
        int width = font.width(text);
        guiGraphics.drawString(font, text, x - width / 2, y, color, false);
    }

    public static class HoverArea {
        private final int posX;
        private final int posY;
        private final int width;
        private final int height;
        @Nullable
        private final Supplier<List<FormattedCharSequence>> tooltip;

        public HoverArea(int posX, int posY, int width, int height) {
            this(posX, posY, width, height, null);
        }

        public HoverArea(int posX, int posY, int width, int height,
                         @Nullable Supplier<List<FormattedCharSequence>> tooltip) {
            this.posX = posX;
            this.posY = posY;
            this.width = width;
            this.height = height;
            this.tooltip = tooltip;
        }

        public int getPosX() {
            return posX;
        }

        public int getPosY() {
            return posY;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        @Nullable
        public Supplier<List<FormattedCharSequence>> getTooltip() {
            return tooltip;
        }

        public boolean isHovered(int guiLeft, int guiTop, int mouseX, int mouseY) {
            return mouseX >= guiLeft + posX && mouseX < guiLeft + posX + width
                    && mouseY >= guiTop + posY && mouseY < guiTop + posY + height;
        }
    }
}

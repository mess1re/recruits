package com.talhanation.recruits.client.gui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import javax.annotation.Nullable;

@Environment(EnvType.CLIENT)
public class ImageToast implements Toast {
    //? if >=1.21 {
    /*private static final ResourceLocation BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("toast/advancement");
    *///?}
    @Nullable
    protected final ResourceLocation image;
    private final Component title;
    private final Component description;
    private long lastChanged;
    private boolean hasStarted;
    public final long SHOW_TIME = 20000L;

    public ImageToast(@Nullable ResourceLocation image, Component title, Component description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    @Override
    public Visibility render(GuiGraphics guiGraphics, ToastComponent toastComponent, long deltaTime) {
        //? if <1.21 {
        guiGraphics.blit(TEXTURE, 0, 0, 0, 0, this.width(), this.height());
        //?} else {
        /*guiGraphics.blitSprite(BACKGROUND_SPRITE, 0, 0, this.width(), this.height());
        *///?}

        if (image != null) {
            guiGraphics.blit(this.image, 5, 5, 0, 0, 21, 21, 21, 21);
        }

        if (this.title != null) {
            guiGraphics.drawString(toastComponent.getMinecraft().font, this.title, 30, 7, 0xFFFFFF, false);
        }

        if (this.description != null) {
            guiGraphics.pose().pushPose();
            try {
                guiGraphics.pose().translate(30, 18, 0);
                guiGraphics.pose().scale(0.5f, 0.5f, 1.0f);
                guiGraphics.drawString(toastComponent.getMinecraft().font, this.description, 0, 0, 0xCCCCCC, false);
            } finally {
                guiGraphics.pose().popPose();
            }
        }

        if (!this.hasStarted) {
            this.lastChanged = deltaTime;
            this.hasStarted = true;
        }
        return deltaTime - this.lastChanged >= SHOW_TIME ? Visibility.HIDE : Visibility.SHOW;
    }

    @Override
    public int width() {
        return 160;
    }

    @Override
    public int height() {
        return 32;
    }
}

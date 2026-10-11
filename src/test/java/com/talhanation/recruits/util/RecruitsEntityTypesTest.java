package com.talhanation.recruits.util;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecruitsEntityTypesTest {
    @Test
    void humanoidDimensionsUseVanillaPlayerSeatAttachment() throws ReflectiveOperationException {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        var builder = RecruitsEntityTypes.humanoid(Pig::new);
        // Bootstrap freezes the vanilla registry, so inspect the registration recipe without registering a new type.
        var dimensionsField = EntityType.Builder.class.getDeclaredField("dimensions");
        dimensionsField.setAccessible(true);
        EntityDimensions dimensions = (EntityDimensions) dimensionsField.get(builder);
        //? if <1.21 {
        assertEquals(0.6F, dimensions.width);
        assertEquals(1.95F, dimensions.height);
        //?} else {
        /*assertEquals(0.6F, dimensions.width());
        assertEquals(1.95F, dimensions.height());
        assertEquals(1.95F * 0.98F, dimensions.eyeHeight());
        *///?}
        //? if >=1.21 {
        /*var attachmentsField = EntityType.Builder.class.getDeclaredField("attachments");
        attachmentsField.setAccessible(true);
        dimensions = dimensions.withAttachments((net.minecraft.world.entity.EntityAttachments.Builder) attachmentsField.get(builder));
        assertEquals(net.minecraft.world.entity.player.Player.DEFAULT_VEHICLE_ATTACHMENT,
                dimensions.attachments().get(net.minecraft.world.entity.EntityAttachment.VEHICLE, 0, 0.0F));
        *///?}
    }

    @Test
    void customEyeHeightRatioIsRetained() throws ReflectiveOperationException {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        var builder = RecruitsEntityTypes.humanoid(Pig::new, 0.9F);
        var field = EntityType.Builder.class.getDeclaredField("dimensions");
        field.setAccessible(true);
        EntityDimensions dimensions = (EntityDimensions) field.get(builder);
        //? if <1.21 {
        assertEquals(1.95F, dimensions.height);
        //?} else {
        /*assertEquals(1.95F * 0.9F, dimensions.eyeHeight());
        *///?}
    }
}

package com.talhanation.recruits.network;

import io.netty.buffer.Unpooled;
import net.minecraft.SharedConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HireStateMessageTest {
    @BeforeAll
    static void bootstrap() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    void hireStateCarriesCurrencyWithoutFactionSync() {
        //? if <1.21 {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        //?} else {
        /*FriendlyByteBuf buffer = new net.minecraft.network.RegistryFriendlyByteBuf(Unpooled.buffer(),
                net.minecraft.core.RegistryAccess.fromRegistryOfRegistries(net.minecraft.core.registries.BuiltInRegistries.REGISTRY));
        *///?}
        try {
            MessageToClientUpdateHireState sent = new MessageToClientUpdateHireState();
            sent.canHire = true;
            sent.currency = new ItemStack(Items.DIAMOND);
            sent.toBytes(buffer);
            MessageToClientUpdateHireState received = new MessageToClientUpdateHireState().fromBytes(buffer);
            assertTrue(received.canHire);
            assertTrue(received.currency.is(Items.DIAMOND));
            assertEquals(1, received.currency.getCount());
            assertEquals(0, buffer.readableBytes());
        } finally {
            buffer.release();
        }
    }
}

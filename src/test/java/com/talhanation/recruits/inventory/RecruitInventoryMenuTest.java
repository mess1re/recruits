package com.talhanation.recruits.inventory;

import net.minecraft.SharedConstants;
import net.minecraft.core.NonNullList;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecruitInventoryMenuTest {
    private RecruitInventoryMenu menu() throws ReflectiveOperationException {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        // Exercise the real transfer algorithm without requiring a loader's registered menu factory.
        RecruitInventoryMenu menu = mock(RecruitInventoryMenu.class, CALLS_REAL_METHODS);
        NonNullList<Slot> slots = NonNullList.create();
        SimpleContainer container = new SimpleContainer(51);
        for (int index = 0; index < 51; index++) {
            int slotIndex = index;
            slots.add(new Slot(container, index, 0, 0) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return slotIndex < 36 || slotIndex >= 42
                            || (slotIndex == 38 && stack.is(Items.WHITE_BANNER));
                }

                @Override
                public int getMaxStackSize() {
                    return slotIndex >= 36 && slotIndex < 42 ? 1 : 64;
                }
            });
        }
        var field = AbstractContainerMenu.class.getDeclaredField("slots");
        field.setAccessible(true);
        field.set(menu, slots);
        return menu;
    }

    @Test
    void recruitItemCanReachLastPlayerSlot() throws ReflectiveOperationException {
        RecruitInventoryMenu menu = menu();
        for (int index = 0; index < 35; index++) menu.getSlot(index).set(new ItemStack(Items.COBBLESTONE, 64));
        menu.getSlot(42).set(new ItemStack(Items.DIAMOND, 3));
        assertEquals(3, menu.quickMoveStack(null, 42).getCount());
        assertTrue(menu.getSlot(42).getItem().isEmpty());
        assertEquals(3, menu.getSlot(35).getItem().getCount());
        assertTrue(menu.getSlot(35).getItem().is(Items.DIAMOND));
    }

    @Test
    void equipmentTransferStaysWithinOneSlot() throws ReflectiveOperationException {
        RecruitInventoryMenu menu = menu();
        menu.getSlot(0).set(new ItemStack(Items.WHITE_BANNER, 3));
        assertEquals(3, menu.quickMoveStack(null, 0).getCount());
        assertEquals(1, menu.getSlot(38).getItem().getCount());
        assertEquals(2, menu.getSlot(0).getItem().getCount());
        for (int index = 39; index < 51; index++) assertFalse(menu.getSlot(index).hasItem());
        assertTrue(menu.quickMoveStack(null, -1).isEmpty());
        assertTrue(menu.quickMoveStack(null, 51).isEmpty());
    }
}

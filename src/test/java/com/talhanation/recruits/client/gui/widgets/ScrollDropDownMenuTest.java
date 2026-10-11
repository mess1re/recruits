package com.talhanation.recruits.client.gui.widgets;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class ScrollDropDownMenuTest {
    @Test
    void expandedRowsAreClickableOutsideTheDisplay() {
        AtomicReference<String> selected = new AtomicReference<>();
        ScrollDropDownMenu<String> menu = new ScrollDropDownMenu<>(null, 10, 10, 80, 20,
                List.of("first", "second"), value -> value, selected::set);
        assertFalse(menu.isMouseOver(20, 40));
        menu.onMouseClick(20, 20);
        assertTrue(menu.isMouseOver(20, 40));
        menu.onMouseClick(20, 40);
        assertEquals("first", selected.get());
        assertFalse(menu.isMouseOver(20, 40));
        menu.visible = false;
        assertFalse(menu.isMouseOver(20, 20), "Hidden owner-only dropdown must not intercept clicks");
    }
}

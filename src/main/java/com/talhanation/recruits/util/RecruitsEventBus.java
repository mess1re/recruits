package com.talhanation.recruits.util;

import com.talhanation.recruits.RecruitsCancellableEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

public final class RecruitsEventBus {

    private RecruitsEventBus() {
    }

    public static <T extends Event & RecruitsCancellableEvent> boolean postCancelable(T event) {
        //? if forge {
        return MinecraftForge.EVENT_BUS.post(event);
        //?} else {
        /*MinecraftForge.EVENT_BUS.post(event);
        return event.isCanceled();
        *///?}
    }
}

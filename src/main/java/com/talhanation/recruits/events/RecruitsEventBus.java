package com.talhanation.recruits.events;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Event-Bus für Recruits-Events, unabhängig vom Mod-Loader.
 *
 * <pre>
 *   RecruitsEventBus.register(RecruitEvent.Hired.class, event -> {
 *       AbstractRecruitEntity recruit = event.getRecruit();
 *   });
 * </pre>
 *
 * Listener einer Event-Klasse erhalten auch alle Unterklassen.
 * Abgebrochene Events werden nicht an weitere Listener verteilt.
 */
public final class RecruitsEventBus {

    private static final Map<Class<?>, List<Consumer<Object>>> LISTENERS = new ConcurrentHashMap<>();

    private RecruitsEventBus() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends Event> void register(Class<T> eventType, Consumer<? super T> listener) {
        LISTENERS.computeIfAbsent(eventType, type -> new CopyOnWriteArrayList<>()).add((Consumer<Object>) listener);
    }

    /**
     * @return {@code true}, wenn das Event abgebrochen wurde.
     */
    public static boolean post(Event event) {
        for (Class<?> type = event.getClass(); type != Object.class; type = type.getSuperclass()) {
            List<Consumer<Object>> listeners = LISTENERS.get(type);
            if (listeners == null) continue;

            for (Consumer<Object> listener : listeners) {
                listener.accept(event);
                if (event.isCanceled()) return true;
            }
        }
        return event.isCanceled();
    }
}

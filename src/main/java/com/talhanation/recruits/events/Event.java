package com.talhanation.recruits.events;

/**
 * Basis aller Recruits-Events. Events werden über {@link RecruitsEventBus} gepostet.
 * Nur Events mit {@link Cancelable} können abgebrochen werden.
 */
public abstract class Event {

    private boolean canceled;

    public boolean isCancelable() {
        return getClass().isAnnotationPresent(Cancelable.class);
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        if (!isCancelable()) {
            throw new UnsupportedOperationException("Event " + getClass().getName() + " is not cancelable");
        }
        this.canceled = canceled;
    }
}

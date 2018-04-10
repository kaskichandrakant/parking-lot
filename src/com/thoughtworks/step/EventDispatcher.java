package com.thoughtworks.step;

import java.util.ArrayList;

public class EventDispatcher {
    private ArrayList<Listener> listeners;
    public EventDispatcher() {
        this.listeners=new ArrayList<>();
    }
    public void announceFull() {
        for (Listener listener : listeners) {
            listener.full();
        }
    }

    public void announceHasSpace() {
        for (Listener listener : listeners) {
            listener.notFull();
        }
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }
}

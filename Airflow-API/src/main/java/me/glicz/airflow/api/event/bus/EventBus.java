package me.glicz.airflow.api.event.bus;

import me.glicz.airflow.api.event.Event;
import me.glicz.airflow.api.plugin.Plugin;

public interface EventBus {
    Plugin getPlugin();

    default <E extends Event> void subscribe(Class<E> event, EventHandler<E> handler) {
        subscribe(event, Event.Priority.NORMAL, handler);
    }

    <E extends Event> void subscribe(Class<E> event, Event.Priority priority, EventHandler<E> handler);

    <E extends Event> void dispatch(E event);
}

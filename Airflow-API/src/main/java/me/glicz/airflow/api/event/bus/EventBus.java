package me.glicz.airflow.api.event.bus;

import me.glicz.airflow.api.event.Event;
import me.glicz.airflow.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface EventBus {
    Plugin getPlugin();

    default <E extends Event> EventHandler<E> subscribe(@NotNull Class<E> event, @NotNull EventHandler<E> handler) {
        return subscribe(event, Event.Priority.NORMAL, handler);
    }

    <E extends Event> EventHandler<E> subscribe(@NotNull Class<E> event, @NotNull Event.Priority priority, @NotNull EventHandler<E> handler);

    <E extends Event> void unsubscribe(@NotNull Class<E> event, @NotNull EventHandler<E> handler);

    <E extends Event> E dispatch(@NotNull E event);
}

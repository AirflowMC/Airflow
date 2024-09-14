package me.glicz.airflow.api.event.bus;

import me.glicz.airflow.api.event.Event;
import me.glicz.airflow.api.event.EventPriority;
import me.glicz.airflow.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface EventBus {
    @NotNull Plugin getPlugin();

    default <E extends Event> @NotNull EventHandler<E> subscribe(@NotNull Class<E> event, @NotNull EventHandler<E> handler) {
        return subscribe(event, EventPriority.NORMAL, handler);
    }

    <E extends Event> @NotNull EventHandler<E> subscribe(@NotNull Class<E> event, @NotNull EventPriority priority, @NotNull EventHandler<E> handler);

    <E extends Event> void unsubscribe(@NotNull Class<E> event, @NotNull EventHandler<E> handler);

    <E extends Event> @NotNull E dispatch(@NotNull E event);
}

package me.glicz.airflow.event.bus;

import me.glicz.airflow.api.event.Event;
import me.glicz.airflow.api.event.EventPriority;
import me.glicz.airflow.api.event.bus.EventBus;
import me.glicz.airflow.api.event.bus.EventHandler;
import me.glicz.airflow.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AirEventBus implements EventBus {
    final Map<Class<? extends Event>, EventHandlers<? extends Event>> handlersMap = new HashMap<>();
    private final Plugin plugin;

    public AirEventBus(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return plugin;
    }

    @Override
    public <E extends Event> @NotNull EventHandler<E> subscribe(@NotNull Class<E> event, @NotNull EventPriority priority, @NotNull EventHandler<E> handler) {
        //noinspection unchecked
        EventHandlers<E> handlers = (EventHandlers<E>) handlersMap.computeIfAbsent(event, $ -> new EventHandlers<E>(plugin));
        handlers.add(priority, handler);
        return handler;
    }

    @Override
    public <E extends Event> void unsubscribe(@NotNull Class<E> event, @NotNull EventHandler<E> handler) {
        //noinspection unchecked
        EventHandlers<E> handlers = (EventHandlers<E>) handlersMap.get(event);
        handlers.remove(handler);
    }

    @Override
    public <E extends Event> @NotNull E dispatch(@NotNull E event) {
        //noinspection unchecked
        EventHandlers<E> handlers = (EventHandlers<E>) handlersMap.get(event.getClass());
        if (handlers != null) {
            handlers.dispatch(event);
        }

        return event;
    }
}

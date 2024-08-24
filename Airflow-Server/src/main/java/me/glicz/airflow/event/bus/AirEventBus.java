package me.glicz.airflow.event.bus;

import me.glicz.airflow.api.event.Event;
import me.glicz.airflow.api.event.bus.EventBus;
import me.glicz.airflow.api.event.bus.EventHandler;
import me.glicz.airflow.api.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class AirEventBus implements EventBus {
    private final Map<Class<? extends Event>, EventHandlers<? extends Event>> eventHandlerMap = new HashMap<>();
    private final Plugin plugin;

    public AirEventBus(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public <E extends Event> void subscribe(Class<E> event, Event.Priority priority, EventHandler<E> handler) {
        //noinspection unchecked
        EventHandlers<E> eventHandlers = (EventHandlers<E>) this.eventHandlerMap.computeIfAbsent(event, $ -> new EventHandlers<E>());
        eventHandlers.add(priority, handler);
    }

    @Override
    public <E extends Event> void dispatch(E event) {
        //noinspection unchecked
        EventHandlers<E> eventHandlers = (EventHandlers<E>) this.eventHandlerMap.get(event.getClass());
        eventHandlers.dispatch(this.plugin, event);
    }
}

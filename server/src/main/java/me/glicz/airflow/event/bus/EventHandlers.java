package me.glicz.airflow.event.bus;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import me.glicz.airflow.api.event.Event;
import me.glicz.airflow.api.event.EventPriority;
import me.glicz.airflow.api.event.bus.EventHandler;
import me.glicz.airflow.api.plugin.Plugin;

import java.util.List;

public class EventHandlers<E extends Event> {
    final Plugin plugin;
    private final Multimap<EventPriority, EventHandler<E>> handlerMap = MultimapBuilder
            .enumKeys(EventPriority.class)
            .arrayListValues()
            .build();

    public EventHandlers(Plugin plugin) {
        this.plugin = plugin;
    }

    public void add(EventPriority priority, EventHandler<E> handler) {
        handlerMap.put(priority, handler);
    }

    public void remove(EventHandler<E> handler) {
        handlerMap.values().remove(handler);
    }

    public void dispatch(E event) {
        for (EventPriority priority : handlerMap.keySet()) {
            dispatch(priority, event);
        }
    }

    public void dispatch(EventPriority priority, E event) {
        List.copyOf(handlerMap.get(priority)).forEach(handler -> {
            try {
                handler.handle(event);
            } catch (Throwable e) {
                plugin.getLogger().atError()
                        .setCause(e)
                        .log("Failed to handle {}", event.getClass().getSimpleName());
            }
        });
    }
}

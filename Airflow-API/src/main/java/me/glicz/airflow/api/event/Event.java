package me.glicz.airflow.api.event;

public interface Event {
    enum Priority {
        LOW,
        LOWEST,
        NORMAL,
        HIGH,
        HIGHEST,
        MONITOR
    }
}

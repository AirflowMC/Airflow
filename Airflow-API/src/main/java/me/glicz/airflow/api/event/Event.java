package me.glicz.airflow.api.event;

public abstract class Event {
    public enum Priority {
        LOW,
        LOWEST,
        NORMAL,
        HIGH,
        HIGHEST,
        MONITOR
    }
}

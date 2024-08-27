package me.glicz.airflow.api.util;

public interface Version {
    String getName();

    int getProtocolVersion();

    boolean isStable();

    String getBranch();

    String getCommit();

    default String getShortCommit() {
        return getCommit().substring(0, 7);
    }
}

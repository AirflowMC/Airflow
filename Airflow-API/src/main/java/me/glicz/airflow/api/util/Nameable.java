package me.glicz.airflow.api.util;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Nameable {
    @NotNull String getName();

    @NotNull Component getDisplayName();

    default boolean hasCustomName() {
        return getCustomName() != null;
    }

    default @Nullable Component getCustomName() {
        return null;
    }
}

package me.glicz.airflow.api.permission;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public record PermissionInfo(
        @NotNull Key permission,
        boolean value,
        @NotNull Permission.DefaultValue defaultValue,
        @NotNull PermissionsHolder holder,
        @NotNull PermissionsSource source
) {
}

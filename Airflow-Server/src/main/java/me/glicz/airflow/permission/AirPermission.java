package me.glicz.airflow.permission;

import me.glicz.airflow.api.permission.Permission;
import net.kyori.adventure.key.Key;

public record AirPermission(Key key, DefaultValue getDefaultValue) implements Permission {
}

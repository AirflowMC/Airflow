package me.glicz.airflow.permission;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import me.glicz.airflow.api.permission.Permission;
import me.glicz.airflow.api.permission.Permissions;
import me.glicz.airflow.api.plugin.Plugin;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirPermissions implements Permissions {
    private final Multimap<Plugin, Key> pluginPermissionMap = MultimapBuilder
            .hashKeys()
            .arrayListValues()
            .build();
    private final Map<Key, Permission> permissionMap = new HashMap<>();

    @Override
    public @NotNull Collection<Permission> getPermissions() {
        return List.copyOf(permissionMap.values());
    }

    @Override
    public @Nullable Permission getPermission(@NotNull Key permission) {
        return permissionMap.get(permission);
    }

    @Override
    public void registerPermission(@NotNull Key permission, Permission.@NotNull DefaultValue defaultValue) {
        permissionMap.put(permission, new AirPermission(permission, defaultValue));
    }

    @Override
    public void registerPermission(@NotNull Plugin plugin, @NotNull Key permission, Permission.@NotNull DefaultValue defaultValue) {
        pluginPermissionMap.put(plugin, permission);
        registerPermission(permission, defaultValue);
    }

    @Override
    public void unregisterAll(@NotNull Plugin plugin) {
        pluginPermissionMap.removeAll(plugin).forEach(permissionMap::remove);
    }
}

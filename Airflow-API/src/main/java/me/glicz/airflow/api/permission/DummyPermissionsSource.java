package me.glicz.airflow.api.permission;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import me.glicz.airflow.api.command.sender.CommandSender;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Dummy {@link PermissionsSource} implementation.
 *
 * @see Permission
 * @see PermissionsSource
 */
public class DummyPermissionsSource implements PermissionsSource {
    private final Map<Key, Boolean> permissionMap = new HashMap<>();
    private final Multimap<PermissionSourcePriority, PermissionsSource> permissionSourceMap = MultimapBuilder
            .hashKeys()
            .arrayListValues()
            .build();
    private final CommandSender holder;

    public DummyPermissionsSource(CommandSender holder) {
        this.holder = holder;
    }

    public void addPermission(Key key, boolean state) {
        permissionMap.put(key, state);
    }

    public void removePermission(Key key) {
        permissionMap.remove(key);
    }

    @Override
    public boolean isPermissionSet(@NotNull Key permission) {
        return permissionMap.containsKey(permission);
    }

    @Override
    public boolean hasPermission(@NotNull Key permission) {
        Permission perm = holder.getServer().getPermissions().getPermission(permission);
        if (perm != null) {
            return hasPermission(perm);
        }

        return Boolean.TRUE.equals(hasPermission0(permission));
    }

    @Override
    public boolean hasPermission(@NotNull Permission permission) {
        return Objects.requireNonNullElseGet(hasPermission0(permission.key()), () -> permission.getDefaultValue().test(holder));
    }

    protected Boolean hasPermission0(Key permission) {
        if (isPermissionSet(permission)) {
            return permissionMap.get(permission.key());
        }

        PermissionSourcePriority[] priorities = PermissionSourcePriority.values();
        for (int i = priorities.length - 1; i >= 0; i--) {
            PermissionSourcePriority priority = priorities[i];

            for (PermissionsSource source : permissionSourceMap.get(priority)) {
                if (source.isPermissionSet(permission)) {
                    return source.hasPermission(permission);
                }
            }
        }

        return null;
    }

    @Override
    public void addPermissionsSource(@NotNull PermissionSourcePriority priority, @NotNull PermissionsSource source) {
        permissionSourceMap.put(priority, source);
    }

    @Override
    public void removePermissionsSource(@NotNull PermissionsSource source) {
        permissionSourceMap.values().remove(source);
    }
}

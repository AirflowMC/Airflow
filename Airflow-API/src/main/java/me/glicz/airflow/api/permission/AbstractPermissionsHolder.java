package me.glicz.airflow.api.permission;

import com.google.common.collect.Multimap;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractPermissionsHolder implements PermissionsHolder {
    protected final Multimap<PermissionSourcePriority, PermissionsSource> permissionSourceMap;

    protected AbstractPermissionsHolder(Multimap<PermissionSourcePriority, PermissionsSource> permissionSourceMap) {
        this.permissionSourceMap = permissionSourceMap;
    }

    @Override
    public @NotNull Collection<PermissionInfo> getPermissions() {
        //noinspection ComparatorMethodParameterNotUsed
        return permissionInfoStream()
                .collect(Collectors.toCollection(() ->
                        new TreeSet<>((info1, info2) -> info1.permission().equals(info2.permission()) ? 0 : 1)
                ));
    }

    protected Stream<PermissionInfo> permissionInfoStream() {
        return permissionSourceMap.entries().stream()
                .map(entry -> Map.entry(
                        entry.getKey(),
                        entry.getValue().getPermissions()
                ))
                .sorted(Map.Entry.<PermissionSourcePriority, Collection<PermissionInfo>>comparingByKey().reversed())
                .flatMap(entry -> entry.getValue().stream());
    }

    @Override
    public boolean isPermissionSet(@NotNull Key permission) {
        return permissionSourceMap.values().stream().anyMatch(source -> source.isPermissionSet(permission));
    }

    protected Boolean hasPermission0(Key permission) {
        synchronized (permissionSourceMap) {
            PermissionSourcePriority[] priorities = PermissionSourcePriority.values();
            for (int i = priorities.length - 1; i >= 0; i--) {
                PermissionSourcePriority priority = priorities[i];

                for (PermissionsSource source : permissionSourceMap.get(priority)) {
                    if (source.isPermissionSet(permission)) {
                        return source.hasPermission(permission);
                    }
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

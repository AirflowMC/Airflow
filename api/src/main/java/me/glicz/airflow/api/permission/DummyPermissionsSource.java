package me.glicz.airflow.api.permission;

import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Dummy {@link PermissionsSource} implementation.
 *
 * @see Permission
 * @see PermissionsSource
 */
public class DummyPermissionsSource extends AbstractPermissionsHolder implements PermissionsSource {
    private final Map<Key, Boolean> permissionMap = new HashMap<>();
    private final PermissionsHolder holder;

    public DummyPermissionsSource(PermissionsHolder holder) {
        super(Multimaps.synchronizedMultimap(
                MultimapBuilder.hashKeys().arrayListValues().build()
        ));

        this.holder = holder;
    }

    public void addPermission(Key key, boolean state) {
        permissionMap.put(key, state);
    }

    public void removePermission(Key key) {
        permissionMap.remove(key);
    }

    @Override
    protected Stream<PermissionInfo> permissionInfoStream() {
        return Stream.concat(
                permissionMap.entrySet().stream()
                        .map(entry -> {
                            Permission permission = getServer().getPermissions().getPermission(entry.getKey());
                            return new PermissionInfo(
                                    entry.getKey(),
                                    entry.getValue(),
                                    permission != null ? permission.getDefaultValue() : Permission.DefaultValue.FALSE,
                                    holder,
                                    this
                            );
                        }),
                super.permissionInfoStream()
        );
    }

    @Override
    public boolean isPermissionSet(@NotNull Key permission) {
        return permissionMap.containsKey(permission) || super.isPermissionSet(permission);
    }

    @Override
    protected Boolean hasPermission0(Key permission) {
        if (permissionMap.containsKey(permission)) {
            return permissionMap.get(permission);
        }

        return super.hasPermission0(permission);
    }

    @Override
    public @NotNull PermissionsHolder getHolder() {
        return holder;
    }
}

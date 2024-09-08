package me.glicz.airflow.api.permission;

/**
 * Represents permissions source created by plugins.
 * <p>
 * This class is safe (and expected) to be implemented.
 * <br>
 * Airflow API only provides simple {@link DummyPermissionsSource} implementation.
 *
 * @see PermissionsHolder
 * @see DummyPermissionsSource
 */
public interface PermissionsSource extends PermissionsHolder {
}

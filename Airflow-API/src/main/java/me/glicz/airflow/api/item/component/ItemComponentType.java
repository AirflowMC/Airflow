package me.glicz.airflow.api.item.component;

import net.kyori.adventure.key.Keyed;

public interface ItemComponentType extends Keyed {
    boolean isPersistent();

    interface Valued<T> extends ItemComponentType {
    }

    interface NonValued extends ItemComponentType {
    }
}

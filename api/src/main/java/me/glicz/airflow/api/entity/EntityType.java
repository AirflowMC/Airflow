package me.glicz.airflow.api.entity;

import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.translation.Translatable;

public interface EntityType<T extends Entity> extends Keyed, Translatable {
}

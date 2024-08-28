package me.glicz.airflow.api.block;

import me.glicz.airflow.api.item.ItemType;
import net.kyori.adventure.translation.Translatable;

public interface BlockType extends Translatable {
    ItemType getItemType();
}

package me.glicz.airflow.item.lore;

import me.glicz.airflow.api.item.lore.ItemLore;
import me.glicz.airflow.api.item.lore.ItemLoreProvider;
import net.kyori.adventure.text.Component;

import java.util.List;

public class AirItemLoreProvider extends ItemLoreProvider {
    @Override
    protected ItemLore create(List<Component> lines) {
        return new AirItemLore(lines);
    }
}

package me.glicz.airflow.api.item.lore;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static me.glicz.airflow.api.item.lore.ItemLoreProvider.provider;

public interface ItemLore {
    @Contract(value = "_ -> new", pure = true)
    static @NotNull ItemLore itemLore(Component @NotNull ... lines) {
        return itemLore(List.of(lines));
    }

    @Contract(value = "_ -> new", pure = true)
    static @NotNull ItemLore itemLore(@NotNull List<Component> lines) {
        return provider().create(lines);
    }

    @NotNull List<Component> getLines();
}

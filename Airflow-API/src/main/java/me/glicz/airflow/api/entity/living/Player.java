package me.glicz.airflow.api.entity.living;

import me.glicz.airflow.api.inventory.menu.MenuType;
import me.glicz.airflow.api.inventory.menu.view.MenuView;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface Player extends Humanoid {
    default <T extends MenuView> @NotNull T openMenu(@NotNull MenuType<T> menuType, @NotNull Component title) {
        return openMenu(menuType, title, null);
    }

    <T extends MenuView> @NotNull T openMenu(@NotNull MenuType<T> menuType, @NotNull Component title, @Nullable Consumer<T> consumer);
}

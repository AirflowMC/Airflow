package me.glicz.airflow.api.event.inventory.menu;

import me.glicz.airflow.api.entity.living.Humanoid;
import me.glicz.airflow.api.event.Event;
import me.glicz.airflow.api.inventory.menu.view.MenuView;
import org.jetbrains.annotations.NotNull;

public abstract class MenuEvent extends Event {
    private final Humanoid viewer;
    private final MenuView menuView;

    public MenuEvent(@NotNull Humanoid viewer, @NotNull MenuView menuView) {
        this.viewer = viewer;
        this.menuView = menuView;
    }

    public @NotNull Humanoid getViewer() {
        return viewer;
    }

    public @NotNull MenuView getMenuView() {
        return menuView;
    }
}

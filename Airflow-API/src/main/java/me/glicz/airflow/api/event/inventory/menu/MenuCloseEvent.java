package me.glicz.airflow.api.event.inventory.menu;

import me.glicz.airflow.api.entity.living.Humanoid;
import me.glicz.airflow.api.inventory.menu.view.MenuView;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class MenuCloseEvent extends MenuEvent {
    @ApiStatus.Internal
    public MenuCloseEvent(@NotNull Humanoid viewer, @NotNull MenuView menuView) {
        super(viewer, menuView);
    }
}

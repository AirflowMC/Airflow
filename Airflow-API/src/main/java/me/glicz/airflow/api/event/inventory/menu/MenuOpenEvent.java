package me.glicz.airflow.api.event.inventory.menu;

import me.glicz.airflow.api.entity.living.Humanoid;
import me.glicz.airflow.api.event.Cancellable;
import me.glicz.airflow.api.inventory.menu.view.MenuView;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class MenuOpenEvent extends MenuEvent implements Cancellable {
    private boolean cancelled;

    @ApiStatus.Internal
    public MenuOpenEvent(@NotNull Humanoid viewer, @NotNull MenuView menuView) {
        super(viewer, menuView);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}

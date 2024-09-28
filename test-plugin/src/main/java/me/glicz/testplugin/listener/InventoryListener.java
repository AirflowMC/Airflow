package me.glicz.testplugin.listener;

import me.glicz.airflow.api.event.inventory.InventoryClickEvent;
import me.glicz.airflow.api.event.inventory.menu.MenuCloseEvent;
import me.glicz.airflow.api.event.inventory.menu.MenuOpenEvent;
import me.glicz.testplugin.TestPlugin;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class InventoryListener {
    private final TestPlugin plugin;

    public InventoryListener(TestPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        plugin.getEventBus().subscribe(MenuCloseEvent.class, this::handleMenuClose);
        plugin.getEventBus().subscribe(MenuOpenEvent.class, this::handleMenuOpen);
        plugin.getEventBus().subscribe(InventoryClickEvent.class, this::handleInventoryClick);
    }

    private void handleInventoryClick(@NotNull InventoryClickEvent e) {
        e.getViewer().sendMessage(Component.text("Click: ")
                .append(e.getMenuView().getTitle())
                .appendSpace()
                .append(Component.text(e.getSlot()))
                .appendSpace()
                .append(Component.text(String.valueOf(e.getInventory())))
        );
    }

    private void handleMenuClose(@NotNull MenuCloseEvent e) {
        e.getViewer().sendMessage(Component.text("Closed: ").append(e.getMenuView().getTitle()));
    }

    private void handleMenuOpen(@NotNull MenuOpenEvent e) {
        e.getViewer().sendMessage(Component.text("Opened: ").append(e.getMenuView().getTitle()));
    }
}

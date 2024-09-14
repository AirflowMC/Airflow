package me.glicz.testplugin.listener;

import me.glicz.airflow.api.event.bus.EventHandler;
import me.glicz.airflow.api.event.player.PlayerJoinEvent;
import me.glicz.airflow.api.inventory.entity.EquipmentSlot;
import me.glicz.airflow.api.inventory.entity.EquipmentSlotGroup;
import me.glicz.airflow.api.item.ItemTypes;
import me.glicz.airflow.api.item.component.ItemComponentTypes;
import me.glicz.airflow.api.item.lore.ItemLore;
import me.glicz.airflow.api.permission.DummyPermissionsSource;
import me.glicz.airflow.api.permission.PermissionSourcePriority;
import me.glicz.testplugin.TestPlugin;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

public class JoinListener implements EventHandler<PlayerJoinEvent> {
    private final TestPlugin plugin;

    public JoinListener(TestPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(@NotNull PlayerJoinEvent e) {
        e.setMessage(null);
        e.getPlayer().sendMessage("Hello! This server runs Airflow!");
        e.getPlayer().sendMessage("You're a " + e.getPlayer().getType().key().asMinimalString());

        DummyPermissionsSource permissionsSource = new DummyPermissionsSource(e.getPlayer());
        permissionsSource.addPermission(Key.key("command"), true);
        permissionsSource.addPermission(Key.key("command/say"), false);
        permissionsSource.addPermission(Key.key("command/msg"), false);
        e.getPlayer().addPermissionsSource(PermissionSourcePriority.NORMAL, permissionsSource);

        e.getPlayer().getInventory().clear();
        e.getPlayer().getInventory().addItem(ItemTypes.DIAMOND_SWORD.newItemStack(itemStack -> {
            itemStack.getItemComponentMap().set(ItemComponentTypes.ITEM_NAME, Component.text("Air Sword", NamedTextColor.GOLD));
            itemStack.getItemComponentMap().set(ItemComponentTypes.LORE, ItemLore.itemLore(
                    Component.text("Welcome to Airflow!", NamedTextColor.WHITE)
            ));
        }));
        e.getPlayer().getInventory().setItem(EquipmentSlot.HEAD, ItemTypes.DIAMOND_HELMET.newItemStack());
        e.getPlayer().getInventory().setSelectedSlot(1);
        e.getPlayer().sendMessage("Your equipment: " + e.getPlayer().getInventory().getItems(EquipmentSlotGroup.ANY));

        this.plugin.getScheduler()
                .taskBuilder(() -> {
                    e.getPlayer().getInventory().setSelectedSlot(2);
                    e.getPlayer().getInventory().setItem(1, ItemTypes.DIRT.newItemStack());
                })
                .delay(5 * 20)
                .schedule();
    }
}

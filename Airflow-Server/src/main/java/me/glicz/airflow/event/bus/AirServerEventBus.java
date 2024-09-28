package me.glicz.airflow.event.bus;

import me.glicz.airflow.Airflow;
import me.glicz.airflow.api.command.Commands;
import me.glicz.airflow.api.event.Event;
import me.glicz.airflow.api.event.EventPriority;
import me.glicz.airflow.api.event.bus.ServerEventBus;
import me.glicz.airflow.api.event.command.CommandsRegisterEvent;
import me.glicz.airflow.api.event.inventory.InventoryClickEvent;
import me.glicz.airflow.api.event.inventory.menu.MenuCloseEvent;
import me.glicz.airflow.api.event.inventory.menu.MenuOpenEvent;
import me.glicz.airflow.api.event.packet.ItemStackDecodeEvent;
import me.glicz.airflow.api.event.packet.ItemStackEncodeEvent;
import me.glicz.airflow.api.event.player.PlayerJoinEvent;
import me.glicz.airflow.api.event.player.PlayerQuitEvent;
import me.glicz.airflow.api.inventory.Inventory;
import me.glicz.airflow.inventory.menu.view.AirMenuView;
import me.glicz.airflow.util.MinecraftComponentSerializer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class AirServerEventBus implements ServerEventBus {
    private final Airflow airflow;

    public AirServerEventBus(Airflow airflow) {
        this.airflow = airflow;
    }

    @Override
    public <E extends Event> @NotNull E dispatch(@NotNull E event) {
        //noinspection unchecked
        List<EventHandlers<E>> eventHandlers = this.airflow.pluginLoader.getPlugins().stream()
                .map(plugin -> (EventHandlers<E>) ((AirEventBus) plugin.getEventBus()).handlersMap.get(event.getClass()))
                .filter(Objects::nonNull)
                .toList();

        for (EventPriority priority : EventPriority.values()) {
            eventHandlers.forEach(handlers -> handlers.dispatch(priority, event));
        }

        return event;
    }

    public void dispatchCommandsRegister(Commands commands) {
        dispatch(new CommandsRegisterEvent(commands));
    }

    public void dispatchMenuClose(Player player, AbstractContainerMenu containerMenu) {
        dispatch(new MenuCloseEvent(player.getAirEntity(), containerMenu.getAirMenuView()));
    }

    public MenuOpenEvent dispatchMenuOpen(Player player, AbstractContainerMenu containerMenu) {
        return dispatch(new MenuOpenEvent(player.getAirEntity(), containerMenu.getAirMenuView()));
    }

    public InventoryClickEvent dispatchInventoryClick(Player player, AbstractContainerMenu containerMenu, int slot) {
        AirMenuView menuView = containerMenu.getAirMenuView();
        Inventory inventory = slot >= 0 && slot < menuView.getComposedInventory().getSize() + menuView.getViewerInventory().getSize()
                ? menuView.getInventoryForSlot(slot)
                : null;
        return dispatch(new InventoryClickEvent(player.getAirEntity(), menuView, inventory, slot));
    }

    public ItemStackDecodeEvent dispatchItemStackDecode(ItemStack itemStack) {
        return dispatch(new ItemStackDecodeEvent(itemStack.airItemStack));
    }

    public ItemStackEncodeEvent dispatchItemStackEncode(ItemStack itemStack) {
        return dispatch(new ItemStackEncodeEvent(itemStack.copy().airItemStack));
    }

    public PlayerJoinEvent dispatchPlayerJoin(ServerPlayer player, MutableComponent message) {
        return dispatch(new PlayerJoinEvent(player.getAirEntity(), MinecraftComponentSerializer.INSTANCE.deserialize(message)));
    }

    public PlayerQuitEvent dispatchPlayerQuit(ServerPlayer player, MutableComponent message) {
        return dispatch(new PlayerQuitEvent(player.getAirEntity(), MinecraftComponentSerializer.INSTANCE.deserialize(message)));
    }
}

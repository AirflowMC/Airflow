package me.glicz.airflow.event.bus;

import me.glicz.airflow.Airflow;
import me.glicz.airflow.api.command.Commands;
import me.glicz.airflow.api.event.Event;
import me.glicz.airflow.api.event.EventPriority;
import me.glicz.airflow.api.event.bus.ServerEventBus;
import me.glicz.airflow.api.event.command.CommandsRegisterEvent;
import me.glicz.airflow.api.event.packet.ItemStackDecodeEvent;
import me.glicz.airflow.api.event.packet.ItemStackEncodeEvent;
import me.glicz.airflow.api.event.player.PlayerJoinEvent;
import me.glicz.airflow.api.event.player.PlayerQuitEvent;
import me.glicz.airflow.util.MinecraftComponentSerializer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
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

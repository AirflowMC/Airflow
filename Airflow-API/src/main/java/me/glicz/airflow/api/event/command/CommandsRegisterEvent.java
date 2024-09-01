package me.glicz.airflow.api.event.command;

import me.glicz.airflow.api.command.Commands;
import me.glicz.airflow.api.event.Event;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class CommandsRegisterEvent extends Event {
    private final Commands commands;

    @ApiStatus.Internal
    public CommandsRegisterEvent(Commands commands) {
        this.commands = commands;
    }

    public @NotNull Commands getCommands() {
        return commands;
    }
}

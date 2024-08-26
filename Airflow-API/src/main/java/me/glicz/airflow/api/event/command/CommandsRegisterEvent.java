package me.glicz.airflow.api.event.command;

import me.glicz.airflow.api.command.Commands;
import me.glicz.airflow.api.event.Event;

public class CommandsRegisterEvent extends Event {
    private final Commands commands;

    public CommandsRegisterEvent(Commands commands) {
        this.commands = commands;
    }

    public Commands getCommands() {
        return commands;
    }
}

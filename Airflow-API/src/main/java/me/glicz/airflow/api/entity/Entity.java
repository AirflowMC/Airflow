package me.glicz.airflow.api.entity;

import me.glicz.airflow.api.command.sender.CommandSender;

public interface Entity extends CommandSender {
    EntityType getType();
}

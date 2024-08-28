package me.glicz.airflow.api.entity;

import me.glicz.airflow.api.command.sender.CommandSender;
import me.glicz.airflow.api.util.Typed;

public interface Entity extends CommandSender, Typed<EntityType> {
}

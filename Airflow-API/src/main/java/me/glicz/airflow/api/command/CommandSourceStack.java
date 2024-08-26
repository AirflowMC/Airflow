package me.glicz.airflow.api.command;

import me.glicz.airflow.api.command.sender.CommandSender;
import me.glicz.airflow.api.entity.Entity;

public interface CommandSourceStack {
    CommandSender getSender();

    Entity getExecutor();
}

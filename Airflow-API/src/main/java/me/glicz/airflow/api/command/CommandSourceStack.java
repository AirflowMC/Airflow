package me.glicz.airflow.api.command;

import me.glicz.airflow.api.command.sender.CommandSender;
import me.glicz.airflow.api.entity.Entity;

import java.util.Objects;

public interface CommandSourceStack {
    CommandSender getSender();

    Entity getExecutor();

    default CommandSender getTarget() {
        return Objects.requireNonNullElse(getExecutor(), getSender());
    }
}

package me.glicz.airflow.api.command.sender;

import me.glicz.airflow.api.ServerAware;
import me.glicz.airflow.api.command.CommandSourceStack;
import me.glicz.airflow.api.message.MessageReceiver;
import me.glicz.airflow.api.permission.Operator;
import me.glicz.airflow.api.permission.PermissionsHolder;
import me.glicz.airflow.api.util.Nameable;
import org.jetbrains.annotations.NotNull;

public interface CommandSender extends MessageReceiver, Nameable, Operator, PermissionsHolder, ServerAware {
    @NotNull CommandSourceStack createCommandSourceStack();
}

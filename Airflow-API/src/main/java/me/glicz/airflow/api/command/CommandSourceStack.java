package me.glicz.airflow.api.command;

import me.glicz.airflow.api.command.sender.CommandSender;
import me.glicz.airflow.api.entity.Entity;
import me.glicz.airflow.api.world.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface CommandSourceStack {
    @NotNull CommandSender getSender();

    @Nullable Entity getExecutor();

    default @NotNull CommandSender getTarget() {
        return Objects.requireNonNullElse(getExecutor(), getSender());
    }

    @NotNull Location getLocation();
}

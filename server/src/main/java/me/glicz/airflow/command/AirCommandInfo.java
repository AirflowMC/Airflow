package me.glicz.airflow.command;

import me.glicz.airflow.api.command.CommandInfo;
import me.glicz.airflow.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public final class AirCommandInfo implements CommandInfo {
    private final Plugin plugin;
    private final String name;
    private final String description;
    private final Collection<String> aliases;
    private final Collection<String> usages;

    public AirCommandInfo(Plugin plugin, String name, String description, Collection<String> aliases, Collection<String> usages) {
        this.plugin = plugin;
        this.name = name;
        this.description = description;
        this.aliases = aliases;
        this.usages = usages;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public @NotNull Collection<String> getAliases() {
        return aliases;
    }

    @Override
    public @NotNull Collection<String> getUsages() {
        return usages;
    }
}

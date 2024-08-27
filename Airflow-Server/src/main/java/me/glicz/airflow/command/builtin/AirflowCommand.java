package me.glicz.airflow.command.builtin;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.command.CommandSourceStack;
import me.glicz.airflow.api.command.Commands;
import me.glicz.airflow.api.plugin.Plugin;
import me.glicz.airflow.api.util.Version;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import java.util.Collection;
import java.util.List;

public class AirflowCommand {
    public void register(Commands commands) {
        commands.register(
                "airflow",
                Commands.literal("airflow")
                        .then(Commands.literal("plugins")
                                .executes(this::executePlugins)
                        )
                        .then(Commands.literal("version")
                                .executes(this::executeVersion)
                        )
                        .build(),
                List.of("air")
        );
    }

    private int executePlugins(CommandContext<CommandSourceStack> ctx) {
        Collection<Plugin> plugins = ctx.getSource().getSender().getServer().getPluginsLoader().getPlugins();

        TextComponent.Builder builder = Component.text()
                .append(Component.text("Server plugins:", NamedTextColor.AQUA));
        plugins.forEach(plugin -> builder
                .appendNewline()
                .append(Component
                        .text(plugin.getPluginMeta().getName(), plugin.isEnabled() ? NamedTextColor.GREEN : NamedTextColor.RED)
                        .hoverEvent(Component.text()
                                .append(Component.text(plugin.getPluginMeta().getName(), NamedTextColor.AQUA))
                                .appendNewline()
                                .append(Component.text("Version: "))
                                .append(Component.text(plugin.getPluginMeta().getVersion(), NamedTextColor.AQUA))
                                .asComponent()
                                .asHoverEvent()
                        )
                )
        );

        ctx.getSource().getSender().sendMessage(builder);

        return Command.SINGLE_SUCCESS;
    }

    private int executeVersion(CommandContext<CommandSourceStack> ctx) {
        Server server = ctx.getSource().getSender().getServer();
        Version version = server.getServerVersion();

        ctx.getSource().getSender().sendMessage(
                "This server is running <brand> version <version>-<branch>@<commit>",
                Placeholder.parsed("brand", server.getServerBrandName()),
                Placeholder.parsed("version", version.getName()),
                Placeholder.parsed("branch", version.getBranch()),
                Placeholder.parsed("commit", version.getShortCommit())
        );

        return Command.SINGLE_SUCCESS;
    }
}

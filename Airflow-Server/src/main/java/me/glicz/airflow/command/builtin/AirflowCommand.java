package me.glicz.airflow.command.builtin;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.command.CommandSourceStack;
import me.glicz.airflow.api.command.Commands;
import me.glicz.airflow.api.plugin.Plugin;
import me.glicz.airflow.api.util.Version;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import java.util.Collection;
import java.util.List;

public class AirflowCommand {
    private static final String COMMIT_URL = "https://github.com/AirflowMC/Airflow/commit";

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
                .append(Component.text("Server plugins:", NamedTextColor.GOLD))
                .appendNewline()
                .append(Component.text("- ", NamedTextColor.DARK_GRAY));

        builder.append(Component.join(
                JoinConfiguration.separator(Component.text(", ", NamedTextColor.DARK_GRAY)),
                plugins.stream()
                        .map(this::asComponent)
                        .toList()
        ));

        ctx.getSource().getSender().sendMessage(builder);

        return Command.SINGLE_SUCCESS;
    }

    private Component asComponent(Plugin plugin) {
        return Component.text(plugin.getPluginMeta().getName(), getDisplayColor(plugin))
                .hoverEvent(asHoverEvent(plugin));
    }

    private TextColor getDisplayColor(Plugin plugin) {
        return plugin.isEnabled() ? NamedTextColor.GREEN : NamedTextColor.RED;
    }

    private HoverEvent<Component> asHoverEvent(Plugin plugin) {
        return Component.text()
                .append(Component.text(plugin.getPluginMeta().getName(), NamedTextColor.AQUA))
                .appendNewline()
                .append(Component.text("Version: "))
                .append(Component.text(plugin.getPluginMeta().getVersion(), NamedTextColor.AQUA))
                .asComponent()
                .asHoverEvent();
    }

    private int executeVersion(CommandContext<CommandSourceStack> ctx) {
        Server server = ctx.getSource().getSender().getServer();
        Version version = server.getServerVersion();

        ctx.getSource().getSender().sendMessage(
                "This server is running <brand> version <click:open_url:'<commit_url>/<commit>'><u><version>-<branch>@<short_commit>",
                Placeholder.parsed("brand", server.getServerBrandName()),
                Placeholder.parsed("version", version.getName()),
                Placeholder.parsed("branch", version.getBranch()),
                Placeholder.parsed("commit", version.getCommit()),
                Placeholder.parsed("short_commit", version.getShortCommit()),
                Placeholder.parsed("commit_url", COMMIT_URL)
        );

        return Command.SINGLE_SUCCESS;
    }
}

package me.glicz.airflow.command.builtin;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import me.glicz.airflow.api.command.CommandSourceStack;
import me.glicz.airflow.api.command.Commands;
import me.glicz.airflow.api.command.sender.CommandSender;
import me.glicz.airflow.api.permission.Permission;
import me.glicz.airflow.api.permission.Permissions;
import me.glicz.airflow.api.plugin.Plugin;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import java.util.Collection;
import java.util.List;

public class PluginsCommand {
    public static final Key PERMISSION = Key.key("airflow", "command/plugins");

    public void register(Permissions permissions, Commands commands) {
        permissions.registerPermission(PERMISSION, Permission.DefaultValue.TRUE);

        commands.register(
                "airflow",
                Commands.literal("plugins")
                        .executes(this::execute)
                        .build(),
                null,
                List.of("pl")
        );
    }

    private int execute(CommandContext<CommandSourceStack> ctx) {
        CommandSender sender = ctx.getSource().getSender();
        Collection<Plugin> plugins = sender.getServer().getPluginsLoader().getPlugins();

        sender.sendMessage("Server plugins<dark_gray>:");

        TextComponent.Builder builder = Component.text()
                .append(Component.text("● ", NamedTextColor.DARK_GRAY));

        builder.append(Component.join(
                JoinConfiguration.separator(Component.text(", ", NamedTextColor.DARK_GRAY)),
                plugins.stream()
                        .map(this::asComponent)
                        .toList()
        ));

        sender.sendMessage(builder);

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
                .append(Component.text(plugin.getPluginMeta().getName(), NamedTextColor.GRAY))
                .appendNewline()
                .append(Component.text("Version: "))
                .append(Component.text(plugin.getPluginMeta().getVersion(), NamedTextColor.GRAY))
                .asComponent()
                .asHoverEvent();
    }
}

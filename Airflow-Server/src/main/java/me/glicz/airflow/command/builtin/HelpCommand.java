package me.glicz.airflow.command.builtin;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.glicz.airflow.api.command.CommandInfo;
import me.glicz.airflow.api.command.CommandSourceStack;
import me.glicz.airflow.api.command.Commands;
import me.glicz.airflow.api.command.sender.CommandSender;
import me.glicz.airflow.api.permission.Permission;
import me.glicz.airflow.api.permission.Permissions;
import me.glicz.airflow.api.plugin.Plugin;
import me.glicz.airflow.api.plugin.PluginMeta;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class HelpCommand {
    public static final Key PERMISSION = Key.key("airflow", "command/help");
    private static final int TITLE_LENGTH = 44;
    private static final int LIMIT_PER_PAGE = 10;
    private static final String NO_DESCRIPTION = "<i>No description available.";

    public void register(Permissions permissions, Commands commands) {
        permissions.registerPermission(PERMISSION, Permission.DefaultValue.TRUE);

        commands.register(
                "airflow",
                Commands.literal("help")
                        .then(Commands.argument("page", IntegerArgumentType.integer(1))
                                .executes(ctx -> executePage(ctx, ctx.getArgument("page", Integer.class)))
                        )
                        .then(Commands.literal("command")
                                .then(Commands.argument("command", StringArgumentType.greedyString())
                                        .suggests((ctx, builder) -> {
                                            ctx.getSource().getSender().getServer().getCommands().getCommandInfos().forEach(info ->
                                                    builder.suggest(info.getName())
                                            );
                                            return builder.buildFuture();
                                        })
                                        .executes(this::executeCommand)
                                )
                        )
                        .then(Commands.literal("plugin")
                                .then(Commands.argument("plugin", StringArgumentType.greedyString())
                                        .suggests((ctx, builder) -> {
                                            ctx.getSource().getSender().getServer().getPluginsLoader().getPlugins().forEach(plugin ->
                                                    builder.suggest(plugin.getPluginMeta().getName())
                                            );
                                            return builder.buildFuture();
                                        })
                                        .executes(this::executePlugin)
                                )
                        )
                        .executes(ctx -> executePage(ctx, 1))
                        .build(),
                "Displays this message.",
                List.of()
        );
    }


    private int executePage(CommandContext<CommandSourceStack> ctx, int page) throws CommandSyntaxException {
        CommandSender sender = ctx.getSource().getSender();

        List<CommandInfo> allCommands = sender.getServer().getCommands().getCommandInfos().stream()
                .sorted(Comparator.comparing(CommandInfo::getName))
                .toList();

        int maxPage = (int) Math.ceil((double) allCommands.size() / LIMIT_PER_PAGE);

        if (page > maxPage) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.integerTooHigh().create(page, maxPage);
        }

        List<CommandInfo> commands = allCommands.subList(
                (page - 1) * LIMIT_PER_PAGE,
                Math.min(page * LIMIT_PER_PAGE, allCommands.size())
        );

        Component title = buildTitle(
                "Help <dark_gray>[<aqua><page></aqua>/<dark_aqua><max_page></dark_aqua>]",
                Placeholder.parsed("page", String.valueOf(page)),
                Placeholder.parsed("max_page", String.valueOf(maxPage))
        );

        sender.sendMessage(title);

        if (page == 1) {
            sender.sendMessage(" Welcome to Airflow!");
            sender.sendMessage("");
        }

        commands.forEach(info -> sender.sendMessage(
                " <dark_gray>● <white>/<command></white> - <gray><description>",
                Placeholder.parsed("command", info.getName()),
                Placeholder.parsed("description", Objects.requireNonNullElse(info.getDescription(), NO_DESCRIPTION))
        ));

        sender.sendMessage(title);

        return Command.SINGLE_SUCCESS;
    }

    private int executeCommand(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        String command = ctx.getArgument("command", String.class);

        CommandSender sender = ctx.getSource().getSender();
        CommandInfo info = sender.getServer().getCommands().getCommandInfo(command);
        if (info == null) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().create();
        }

        Component title = buildTitle(
                "Help <gray>/<command>",
                Placeholder.parsed("command", command)
        );

        sender.sendMessage(title);

        if (info.getPlugin() != null) {
            sender.sendMessage(
                    " Plugin<dark_gray>: <gray><plugin>",
                    Placeholder.parsed("plugin", info.getPlugin().getPluginMeta().getName())
            );
        }

        sender.sendMessage(
                " Description<dark_gray>: <gray><description>",
                Placeholder.parsed("description", Objects.requireNonNullElse(info.getDescription(), NO_DESCRIPTION))
        );

        if (!info.getUsages().isEmpty()) {
            sender.sendMessage(" Usages<dark_gray>:");
            info.getUsages().forEach(usage -> sender.sendMessage(
                    " <dark_gray>● <white>/<command> <usage>",
                    Placeholder.parsed("command", command),
                    Placeholder.parsed("usage", usage)
            ));
        }

        if (!info.getAliases().isEmpty()) {
            sender.sendMessage(" Aliases<dark_gray>:");
            info.getAliases().forEach(alias -> sender.sendMessage(
                    " <dark_gray>● <white>/<alias>",
                    Placeholder.parsed("alias", alias)
            ));
        }

        sender.sendMessage(title);

        return Command.SINGLE_SUCCESS;
    }

    private int executePlugin(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        String pluginName = ctx.getArgument("plugin", String.class);

        CommandSender sender = ctx.getSource().getSender();
        Plugin plugin = sender.getServer().getPluginsLoader().getPlugin(pluginName);
        if (plugin == null) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().create();
        }

        PluginMeta meta = plugin.getPluginMeta();

        Component title = buildTitle(
                "Help <gray><plugin>",
                Placeholder.parsed("plugin", pluginName)
        );

        sender.sendMessage(title);

        if (!meta.getAuthors().isEmpty()) {
            sender.sendMessage(
                    " Authors<dark_gray>: <gray><authors>",
                    Placeholder.parsed("authors", String.join("<dark_gray>,</dark_gray> ", meta.getAuthors()))
            );
        }

        if (!meta.getContributors().isEmpty()) {
            sender.sendMessage(
                    " Contributors<dark_gray>: <gray><contributors>",
                    Placeholder.parsed("contributors", String.join("<dark_gray>,</dark_gray> ", meta.getContributors()))
            );
        }

        sender.sendMessage(
                " Version<dark_gray>: <gray><version>",
                Placeholder.parsed("version", meta.getVersion())
        );

        sender.sendMessage(
                " Description<dark_gray>: <gray><description>",
                Placeholder.parsed("description", Objects.requireNonNullElse(meta.getDescription(), NO_DESCRIPTION))
        );


        sender.sendMessage(title);

        return Command.SINGLE_SUCCESS;
    }

    private Component buildTitle(String text, TagResolver... tagResolvers) {
        Component component = MiniMessage.miniMessage().deserialize(
                text, tagResolvers
        );
        String plain = PlainTextComponentSerializer.plainText().serialize(component);

        int totalPadding = TITLE_LENGTH - plain.length() - 2;
        int leftLength = totalPadding / 2;
        int rightLength = totalPadding - leftLength;

        String leftBar = "-".repeat(leftLength);
        String rightBar = "-".repeat(rightLength);

        return MiniMessage.miniMessage().deserialize(
                "<gold><left_bar><white> <title> </white><right_bar>",
                Placeholder.component("title", component),
                Placeholder.parsed("left_bar", leftBar),
                Placeholder.parsed("right_bar", rightBar)
        );
    }
}

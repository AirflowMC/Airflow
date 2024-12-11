package me.glicz.airflow.command.builtin;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.command.CommandSourceStack;
import me.glicz.airflow.api.command.Commands;
import me.glicz.airflow.api.permission.Permission;
import me.glicz.airflow.api.permission.Permissions;
import me.glicz.airflow.api.util.Version;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import java.util.List;

public class VersionCommand {
    public static final Key PERMISSION = Key.key("airflow", "command/version");
    private static final String COMMIT_URL = "https://github.com/AirflowMC/Airflow/commit";

    public void register(Permissions permissions, Commands commands) {
        permissions.registerPermission(PERMISSION, Permission.DefaultValue.TRUE);

        commands.register(
                "airflow",
                Commands.literal("version")
                        .executes(this::execute)
                        .build(),
                null,
                List.of("ver")
        );
    }

    private int execute(CommandContext<CommandSourceStack> ctx) {
        Server server = ctx.getSource().getSender().getServer();
        Version version = server.getServerVersion();

        ctx.getSource().getSender().sendMessage(
                "This server is running <brand> version <version>-<click:open_url:'<commit_url>/<commit>'><u><branch>@<short_commit>",
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

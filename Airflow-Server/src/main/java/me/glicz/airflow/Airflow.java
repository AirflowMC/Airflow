package me.glicz.airflow;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import me.glicz.airflow.api.command.Commands;
import me.glicz.airflow.api.permission.Permission;
import me.glicz.airflow.api.permission.Permissions;
import me.glicz.airflow.api.properties.ServerProperties;
import me.glicz.airflow.command.builtin.HelpCommand;
import me.glicz.airflow.command.builtin.PluginsCommand;
import me.glicz.airflow.command.builtin.VersionCommand;
import me.glicz.airflow.event.bus.AirServerEventBus;
import me.glicz.airflow.item.component.adapter.ItemComponentAdapters;
import me.glicz.airflow.permission.AirPermissions;
import me.glicz.airflow.plugin.loader.AirPluginsLoader;
import me.glicz.airflow.properties.AirServerProperties;
import me.glicz.airflow.service.AirServices;
import me.glicz.airflow.util.AirServerReference;
import me.glicz.airflow.util.AirVersion;
import net.kyori.adventure.key.Key;
import net.minecraft.SharedConstants;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerSettings;
import org.fusesource.jansi.AnsiConsole;

import java.io.File;

public class Airflow {
    public final AirServerReference serverRef;
    public final AirVersion version;
    public final ServerProperties serverProperties;
    public final AirPluginsLoader pluginLoader;
    public final AirServerEventBus serverEventBus;
    public final Permissions permissions;
    public final AirServices services;

    public Airflow(String[] args, DedicatedServerSettings settings) {
        OptionParser optionParser = new OptionParser();
        OptionSpec<File> pluginsFolder = optionParser.accepts("pluginsFolder").withRequiredArg().ofType(File.class);
        OptionSpec<Void> skipPluginLoader = optionParser.accepts("skipPluginLoader");
        optionParser.allowsUnrecognizedOptions();

        OptionSet optionSet = optionParser.parse(args);

        this.serverRef = new AirServerReference();
        this.version = new AirVersion(SharedConstants.getCurrentVersion());
        this.serverProperties = new AirServerProperties(settings);
        this.pluginLoader = new AirPluginsLoader(this, optionSet.valueOf(pluginsFolder), optionSet.has(skipPluginLoader));
        this.serverEventBus = new AirServerEventBus(this);
        this.permissions = new AirPermissions();
        this.services = new AirServices();

        ItemComponentAdapters.bootstrap();

        AnsiConsole.systemInstall();
    }

    public AirServer getServer() {
        return this.serverRef.getServer();
    }

    public void createServer(final DedicatedServer minecraftServer) {
        serverRef.setServer(() -> new AirServer(this, minecraftServer));
    }

    public void registerMinecraftPermission(LiteralArgumentBuilder<?> builder, boolean defaultValue) {
        if (builder.getRedirect() != null) return;

        //noinspection PatternValidation
        permissions.registerPermission(
                Key.key("command/" + builder.getLiteral()),
                defaultValue ? Permission.DefaultValue.TRUE : Permission.DefaultValue.FALSE
        );
    }

    public void registerAirflowCommands(Commands commands) {
        new HelpCommand().register(permissions, commands);
        new PluginsCommand().register(permissions, commands);
        new VersionCommand().register(permissions, commands);
    }
}

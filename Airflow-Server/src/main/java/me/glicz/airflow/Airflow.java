package me.glicz.airflow;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import me.glicz.airflow.api.properties.ServerProperties;
import me.glicz.airflow.event.bus.AirServerEventBus;
import me.glicz.airflow.plugin.AirPluginsLoader;
import me.glicz.airflow.properties.AirServerProperties;
import me.glicz.airflow.service.AirServices;
import me.glicz.airflow.util.AirServerReference;
import me.glicz.airflow.util.AirVersion;
import net.minecraft.SharedConstants;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerSettings;

import java.io.File;

public class Airflow {
    public final ServerProperties serverProperties;
    public final AirVersion version;
    public final AirPluginsLoader pluginLoader;
    public final AirServerEventBus serverEventBus;
    public final AirServices services;
    private final AirServerReference serverRef;

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
        this.services = new AirServices();
    }

    public AirServer getServer() {
        return this.serverRef.getServer();
    }

    public AirServerReference getServerRef() {
        return this.serverRef;
    }

    public void createServer(final DedicatedServer minecraftServer) {
        this.serverRef.setServer(() -> new AirServer(this, minecraftServer));
    }
}

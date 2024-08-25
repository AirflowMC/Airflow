package me.glicz.airflow.plugin;

import com.google.inject.Guice;
import com.mojang.logging.LogUtils;
import me.glicz.airflow.Airflow;
import me.glicz.airflow.api.plugin.Plugin;
import me.glicz.airflow.api.plugin.PluginMeta;
import me.glicz.airflow.api.plugin.PluginsLoader;
import me.glicz.airflow.inject.PluginModule;
import me.glicz.airflow.plugin.bootstrap.AirBootstrapContext;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.configurate.ConfigurateException;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AirPluginsLoader implements PluginsLoader {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String PLUGINS_FOLDER = "plugins";
    private final File pluginsFolder;
    private final boolean skip;
    private final Map<String, Plugin> plugins = new HashMap<>();
    private final Airflow airflow;

    public AirPluginsLoader(Airflow airflow, File pluginsFolder, boolean skip) {
        this.airflow = airflow;
        this.skip = skip;
        this.pluginsFolder = Objects.requireNonNullElse(pluginsFolder, new File(PLUGINS_FOLDER)).getAbsoluteFile();
        //noinspection ResultOfMethodCallIgnored
        this.pluginsFolder.mkdirs();
    }

    @Override
    public @Nullable Plugin getPlugin(String name) {
        return this.plugins.get(name);
    }

    @Override
    public @NotNull File getPluginsFolder() {
        return this.pluginsFolder;
    }

    public void preloadPlugins() {
        if (skip) return;

        Collection<File> plugins = FileUtils.listFiles(pluginsFolder, new String[]{"jar"}, false);
        LOGGER.info("Found {} server plugins...", plugins.size());

        plugins.forEach(file -> {
            try {
                AirPluginClassLoader classLoader = new AirPluginClassLoader(file);
                PluginMeta pluginMeta = new AirPluginMeta(classLoader.getResourceAsStream("airflow.yml"));

                Plugin plugin = createPluginInstance(classLoader, pluginMeta);
                if (plugin == null) {
                    return;
                }

                Guice.createInjector(new PluginModule(airflow, plugin, pluginMeta))
                        .injectMembers(plugin);
                classLoader.setPlugin(plugin);

                this.plugins.put(pluginMeta.getName(), plugin);
            } catch (MalformedURLException e) {
                LOGGER.atError()
                        .setCause(e)
                        .log("Cannot load plugin {}, because of malformed URL", file.getName());
            } catch (ConfigurateException e) {
                LOGGER.atError()
                        .setCause(e)
                        .log("Cannot load plugin {}, because of invalid plugin meta file (airflow.yml)", file.getName());
            }
        });
    }

    private Plugin createPluginInstance(AirPluginClassLoader classLoader, PluginMeta pluginMeta) {
        String mainClass = pluginMeta.getMainClass();

        try {
            Class<?> clazz = classLoader.loadClass(mainClass);

            if (clazz.isAssignableFrom(Plugin.class)) {
                throw new IllegalArgumentException(clazz + " does not extend Plugin");
            }

            return (Plugin) clazz.getConstructor().newInstance();
        } catch (Exception e) {
            LOGGER.atError()
                    .setCause(e)
                    .log("Failed to create an instance of {}", mainClass);
            return null;
        }
    }

    public void bootstrapPlugins() {
        plugins.values().forEach(plugin -> {
            try {
                plugin.bootstrap(new AirBootstrapContext(airflow));
            } catch (Throwable e) {
                plugin.getLogger().atError()
                        .setCause(e)
                        .log("Failed to bootstrap {}", plugin.getPluginMeta().getName());
            }
        });
    }

    public void loadPlugins() {
        plugins.values().forEach(plugin -> {
            try {
                plugin.getLogger().info("Loading...");
                plugin.onLoad();
            } catch (Throwable e) {
                plugin.getLogger().atError()
                        .setCause(e)
                        .log("Failed to load {}", plugin.getPluginMeta().getName());
            }
        });
    }

    private void enablePlugin(Plugin plugin) {
        try {
            plugin.getLogger().info("Enabling...");
            plugin.setEnabled(true);
        } catch (Throwable e) {
            plugin.getLogger().atError()
                    .setCause(e)
                    .log("Failed to enable {}", plugin.getPluginMeta().getName());
            disablePlugin(plugin);
        }
    }

    public void enablePlugins() {
        plugins.values().forEach(this::enablePlugin);
    }

    private void disablePlugin(Plugin plugin) {
        try {
            plugin.getLogger().info("Disabling...");
            plugin.setEnabled(false);
        } catch (Throwable e) {
            plugin.getLogger().atError()
                    .setCause(e)
                    .log("Failed to disable {}", plugin.getPluginMeta().getName());
        }
    }

    public void disablePlugins() {
        plugins.values().forEach(this::disablePlugin);
    }
}

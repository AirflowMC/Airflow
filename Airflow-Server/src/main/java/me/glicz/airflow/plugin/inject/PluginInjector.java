package me.glicz.airflow.plugin.inject;

import me.glicz.airflow.Airflow;
import me.glicz.airflow.api.plugin.Plugin;
import me.glicz.airflow.api.plugin.PluginMeta;
import me.glicz.airflow.event.bus.AirEventBus;
import me.glicz.airflow.scheduler.AirScheduler;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;

public class PluginInjector {
    private static final Field SERVER_REF = getField("serverRef");
    private static final Field PLUGIN_META = getField("pluginMeta");
    private static final Field DATA_FOLDER = getField("dataFolder");
    private static final Field EVENT_BUS = getField("eventBus");
    private static final Field SCHEDULER = getField("scheduler");
    private static final Field LOGGER = getField("logger");

    private PluginInjector() {
    }

    private static Field getField(String name) {
        try {
            Field field = Plugin.class.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static void inject(Plugin plugin, Airflow airflow, PluginMeta pluginMeta) throws IllegalAccessException {
        injectField(plugin, SERVER_REF, airflow.serverRef);
        injectField(plugin, PLUGIN_META, pluginMeta);
        injectField(plugin, DATA_FOLDER, new File(airflow.pluginLoader.getPluginsFolder(), pluginMeta.getName()));
        injectField(plugin, EVENT_BUS, new AirEventBus(plugin));
        injectField(plugin, SCHEDULER, new AirScheduler(plugin));
        injectField(plugin, LOGGER, LoggerFactory.getLogger(pluginMeta.getName()));
    }

    private static void injectField(Plugin plugin, Field field, Object value) throws IllegalAccessException {
        field.set(plugin, value);
    }
}

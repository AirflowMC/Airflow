package me.glicz.airflow.api.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ServiceLoader;

public final class ServiceUtils {
    private ServiceUtils() {
    }

    @Contract(value = "_ -> new", pure = true)
    public static <T> @NotNull LazyReference<T> lazyLoadService(@NotNull Class<T> clazz) {
        return LazyReference.lazy(() -> ServiceLoader.load(clazz).findFirst().orElseThrow());
    }
}

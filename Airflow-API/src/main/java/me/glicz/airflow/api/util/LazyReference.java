package me.glicz.airflow.api.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class LazyReference<T> {
    private final Supplier<T> getter;
    private T value;

    private LazyReference(Supplier<T> getter) {
        this.getter = Objects.requireNonNull(getter, "getter == null");
    }

    private LazyReference(T value) {
        this.getter = null;
        this.value = Objects.requireNonNull(value, "value == null");
    }

    @Contract(value = "_ -> new", pure = true)
    public static <T> @NotNull LazyReference<T> lazy(@NotNull Supplier<T> getter) {
        return new LazyReference<>(getter);
    }

    public static <T> @NotNull LazyReference<T> resolved(@NotNull T value) {
        return new LazyReference<>(value);
    }

    public T get() {
        //noinspection DataFlowIssue
        return isResolved() ? value : (value = getter.get());
    }

    public T getIfResolved() {
        return value;
    }

    public Optional<T> asOptional() {
        return Optional.ofNullable(get());
    }

    public boolean isResolved() {
        return value != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LazyReference<?> that)) return false;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}

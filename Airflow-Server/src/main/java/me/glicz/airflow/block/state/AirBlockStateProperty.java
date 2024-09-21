package me.glicz.airflow.block.state;

import me.glicz.airflow.api.block.state.BlockStateProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

public abstract class AirBlockStateProperty<T> implements BlockStateProperty<T> {
    private final Property<?> handle;

    public AirBlockStateProperty(Property<?> handle) {
        this.handle = handle;
    }

    public Property<?> getHandle() {
        return handle;
    }

    @Override
    public @NotNull String getName() {
        return this.handle.getName();
    }

    public static class Boolean extends AirBlockStateProperty<java.lang.Boolean> implements BlockStateProperty.Boolean {
        public Boolean(Property<?> handle) {
            super(handle);
        }
    }

    public static class Integer extends AirBlockStateProperty<java.lang.Integer> implements BlockStateProperty.Integer {
        public Integer(Property<?> handle) {
            super(handle);
        }

        @Override
        public IntegerProperty getHandle() {
            return (IntegerProperty) super.getHandle();
        }

        @Override
        public int getMin() {
            return getHandle().min;
        }

        @Override
        public int getMax() {
            return getHandle().max;
        }
    }
}

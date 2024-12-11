package me.glicz.airflow.block.state;

import me.glicz.airflow.api.block.state.BlockStateProperty;
import me.glicz.airflow.api.block.state.BlockStatePropertyProvider;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;

public class AirBlockStatePropertyProvider extends BlockStatePropertyProvider {
    @Override
    protected BlockStateProperty.Boolean getBoolean(String name) {
        return (BlockStateProperty.Boolean) get(name);
    }

    @Override
    protected BlockStateProperty.Integer getInteger(String name) {
        return (BlockStateProperty.Integer) get(name);
    }

    private BlockStateProperty<?> get(String name) {
        try {
            Property<?> property = (Property<?>) BlockStateProperties.class.getField(name).get(null);
            return property.airBlockStateProperty;
        } catch (Exception e) {
            return null;
        }
    }
}

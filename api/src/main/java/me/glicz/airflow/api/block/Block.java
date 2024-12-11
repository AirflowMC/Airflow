package me.glicz.airflow.api.block;

import me.glicz.airflow.api.block.state.BlockState;
import me.glicz.airflow.api.util.Typed;
import me.glicz.airflow.api.util.math.Vector3d;
import me.glicz.airflow.api.util.math.Vector3i;
import me.glicz.airflow.api.world.Location;
import me.glicz.airflow.api.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;

public interface Block extends Typed<BlockType> {
    @NotNull World getWorld();

    @NotNull Vector3i getPosition();

    default @NotNull Location getLocation() {
        return new Location(getWorld(), new Vector3d(getPosition()));
    }

    default void setType(@NotNull BlockType type) {
        setState(type.createBlockState());
    }

    @NotNull BlockState getState();

    void setState(@NotNull BlockState state);

    default void mapState(@NotNull UnaryOperator<BlockState> mapper) {
        setState(mapper.apply(getState()));
    }
}

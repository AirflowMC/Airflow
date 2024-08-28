package me.glicz.airflow.block;

import me.glicz.airflow.api.block.BlockType;
import me.glicz.airflow.api.block.BlockTypeProvider;
import net.kyori.adventure.key.Key;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class AirBlockTypeProvider extends BlockTypeProvider {
    @Override
    protected BlockType get(Key key) {
        return BuiltInRegistries.BLOCK.get(ResourceLocation.parse(key.toString())).airBlockType;
    }
}

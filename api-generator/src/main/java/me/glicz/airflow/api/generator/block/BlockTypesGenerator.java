package me.glicz.airflow.api.generator.block;

import me.glicz.airflow.api.block.BlockType;
import me.glicz.airflow.api.block.BlockTypeProvider;
import me.glicz.airflow.api.generator.Generator;
import net.minecraft.core.registries.BuiltInRegistries;

public class BlockTypesGenerator extends Generator {
    public BlockTypesGenerator() {
        super(BuiltInRegistries.BLOCK, "me.glicz.airflow.api.block", "BlockTypes", BlockType.class, BlockTypeProvider.class);
    }
}

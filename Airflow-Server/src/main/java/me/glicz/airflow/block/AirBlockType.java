package me.glicz.airflow.block;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.glicz.airflow.api.block.BlockType;
import me.glicz.airflow.api.block.state.BlockState;
import me.glicz.airflow.api.item.ItemType;
import net.kyori.adventure.key.Key;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class AirBlockType implements BlockType {
    private final Block handle;

    public AirBlockType(Block handle) {
        this.handle = handle;
    }

    @Override
    public @NotNull ItemType getItemType() {
        return this.handle.asItem().airItemType;
    }

    @Override
    public @NotNull BlockState createBlockState() {
        return this.handle.defaultBlockState().airBlockState;
    }

    @Override
    public @NotNull BlockState createBlockState(String state) throws CommandSyntaxException {
        return BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK, key() + state, false).blockState().airBlockState;
    }

    @Override
    public @NotNull Key key() {
        //noinspection PatternValidation
        return Key.key(BuiltInRegistries.BLOCK.getKey(this.handle).toString());
    }

    @Override
    public @NotNull String translationKey() {
        return this.handle.getDescriptionId();
    }
}

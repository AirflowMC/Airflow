package me.glicz.airflow.api.block;

import me.glicz.airflow.api.block.state.BlockState;
import me.glicz.airflow.api.item.ItemTypeLike;
import me.glicz.airflow.api.item.stack.ItemStack;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.translation.Translatable;

public interface BlockType extends ItemTypeLike, Keyed, Translatable {
    BlockState createBlockState();

    @Override
    default ItemStack asItemStack(int amount) {
        return getItemType().asItemStack(amount);
    }
}

package net.dndats.world_orbs.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractWorldOrbBlock<T extends BlockEntity> extends BaseEntityBlock {

    private final DeferredHolder<Block, Block> typeSupplier;

    public AbstractWorldOrbBlock(Properties properties, DeferredHolder<Block, Block> typeSupplier) {
        super(properties);
        this.typeSupplier = typeSupplier;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return createBlockEntity(pos, state);
    }

    protected abstract T createBlockEntity(BlockPos pos, BlockState state);

}

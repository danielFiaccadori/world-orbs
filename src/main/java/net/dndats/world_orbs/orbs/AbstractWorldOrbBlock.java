package net.dndats.world_orbs.orbs;

import net.dndats.world_orbs.util.ActiveGlobalEffectHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractWorldOrbBlock<T extends BlockEntity> extends BaseEntityBlock {

    public AbstractWorldOrbBlock(Properties properties, DeferredHolder<Block, Block> typeSupplier) {
        super(properties);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        //TODO: finish implementation of interacting with the orb
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof AbstractWorldOrbBlockEntity<?> orbBlockEntity) {
                if (orbBlockEntity.hasUser()) {
                    return InteractionResult.CONSUME;
                } else {
                    orbBlockEntity.setOrbUserId(player.getUUID());

                    ActiveGlobalEffectHelper.activateOrbEffectWithIdForPlayer(
                            orbBlockEntity.getAbilityId(),
                            player,
                            orbBlockEntity
                    );

                    player.sendSystemMessage(Component.literal("You have activated the orb! Now its user UUID is: " + orbBlockEntity.getOrbUserId()));
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        }
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return createBlockEntity(pos, state);
    }

    protected abstract T createBlockEntity(BlockPos pos, BlockState state);

}

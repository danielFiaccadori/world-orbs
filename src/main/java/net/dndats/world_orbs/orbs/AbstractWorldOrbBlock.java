package net.dndats.world_orbs.orbs;

import net.dndats.world_orbs.registry.ModData;
import net.dndats.world_orbs.util.ActiveGlobalEffectHelper;
import net.dndats.world_orbs.util.TickScheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractWorldOrbBlock<T extends BlockEntity> extends BaseEntityBlock {

    private static final VoxelShape SHAPE = Block.box(
            4.0, 0.0, 4.0,
            12.0, 12.0, 12.0
    );
    public AbstractWorldOrbBlock(Properties properties, DeferredHolder<Block, Block> typeSupplier) {
        super(properties);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof AbstractWorldOrbBlockEntity<?> orbBlockEntity) {
                if (orbBlockEntity.hasUser()) {
                    return InteractionResult.CONSUME;
                } else {
                    if (orbBlockEntity.getLevel() != null) {
                        orbBlockEntity.playSound(orbBlockEntity.getLevel(), orbBlockEntity.getBlockPos(), SoundEvents.BEACON_ACTIVATE);
                    }

                    orbBlockEntity.triggerActivateAnimation();

                    TickScheduler.schedule(() -> {
                        orbBlockEntity.setOrbUserId(player.getUUID());

                        ActiveGlobalEffectHelper.activateOrbEffectWithIdForPlayer(
                                orbBlockEntity.getAbilityId(),
                                player,
                                orbBlockEntity
                        );

                    }, 40);
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        }
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public @NotNull BlockState playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        super.playerWillDestroy(level, pos, state, player);
        this.spawnDestroyParticles(level, player, pos, state);

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof AbstractWorldOrbBlockEntity<?> orbBlockEntity) {
            if (orbBlockEntity.hasUser()) {
                player.getData(ModData.ACTIVE_GLOBAL_EFFECT.get()).setOrbPos(null);
                player.getData(ModData.ACTIVE_GLOBAL_EFFECT.get()).syncData(player);
            }
        }

        return state;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return createBlockEntity(pos, state);
    }

    protected abstract T createBlockEntity(BlockPos pos, BlockState state);

}

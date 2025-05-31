package net.dndats.world_orbs.blocks.test;

import com.mojang.serialization.MapCodec;
import net.dndats.world_orbs.blocks.AbstractWorldOrbBlock;
import net.dndats.world_orbs.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ApotheosisOrbBlock extends AbstractWorldOrbBlock<ApotheosisOrbBlockEntity> {

    public ApotheosisOrbBlock() {
        super(Properties.of()
                        .noOcclusion()
                        .isSuffocating((s, g, p) -> false)
                        .isViewBlocking((s, g, p) -> false)
                        .strength(2.0f)
                        .sound(SoundType.STONE)
                        .lightLevel(state -> 2),
                ModBlockEntities.APOTHEOSIS_ORB);
    }

    @Override
    protected ApotheosisOrbBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ApotheosisOrbBlockEntity(pos, state);
    }



    public static final MapCodec<ApotheosisOrbBlock> CODEC = simpleCodec((t) -> new ApotheosisOrbBlock());

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

}

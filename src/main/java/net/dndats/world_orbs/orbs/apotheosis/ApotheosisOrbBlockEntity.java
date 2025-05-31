package net.dndats.world_orbs.orbs.apotheosis;

import net.dndats.world_orbs.Worldorbs;
import net.dndats.world_orbs.orbs.AbstractWorldOrbBlockEntity;
import net.dndats.world_orbs.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class ApotheosisOrbBlockEntity extends AbstractWorldOrbBlockEntity<ApotheosisOrbBlockEntity> {

    public static final ResourceLocation ORB_POWER = ResourceLocation.withDefaultNamespace("apotheosis_orb");

    public ApotheosisOrbBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.APOTHEOSIS_ORB_BLOCK_ENTITY.get(), pos, state, ORB_POWER);
    }

    @Override
    public ResourceLocation getTexture() {
        return ResourceLocation.fromNamespaceAndPath(Worldorbs.MODID, "textures/block/apotheosis_orb.png");
    }

}

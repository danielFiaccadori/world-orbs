package net.dndats.world_orbs.blocks.test;

import net.dndats.world_orbs.Worldorbs;
import net.dndats.world_orbs.blocks.AbstractWorldOrbBlockEntity;
import net.dndats.world_orbs.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class ApotheosisOrbBlockEntity extends AbstractWorldOrbBlockEntity<ApotheosisOrbBlockEntity> {


    public ApotheosisOrbBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.APOTHEOSIS_ORB_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public ResourceLocation getTexture() {
        return ResourceLocation.fromNamespaceAndPath(Worldorbs.MODID, "textures/block/apotheosis_orb.png");
    }

}

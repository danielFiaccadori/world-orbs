package net.dndats.world_orbs.blocks.test;

import net.dndats.world_orbs.blocks.AbstractWorldOrbModel;
import net.dndats.world_orbs.blocks.AbstractWorldOrbRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class ApotheosisOrbRenderer extends AbstractWorldOrbRenderer<ApotheosisOrbBlockEntity> {

    public ApotheosisOrbRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new AbstractWorldOrbModel<>());
    }

}

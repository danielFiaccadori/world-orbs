package net.dndats.world_orbs.orbs.apotheosis;

import net.dndats.world_orbs.orbs.AbstractWorldOrbModel;
import net.dndats.world_orbs.orbs.AbstractWorldOrbRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class ApotheosisOrbRenderer extends AbstractWorldOrbRenderer<ApotheosisOrbBlockEntity> {

    public ApotheosisOrbRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new AbstractWorldOrbModel<>());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

}

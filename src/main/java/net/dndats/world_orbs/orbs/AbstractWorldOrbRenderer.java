package net.dndats.world_orbs.orbs;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public abstract class AbstractWorldOrbRenderer<T extends AbstractWorldOrbBlockEntity<T>> extends GeoBlockRenderer<T> {

    public AbstractWorldOrbRenderer(BlockEntityRendererProvider.Context context, AbstractWorldOrbModel<T> model) {
        super(model);
    }
}


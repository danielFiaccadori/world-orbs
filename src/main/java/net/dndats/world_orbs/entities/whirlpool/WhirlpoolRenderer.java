package net.dndats.world_orbs.entities.whirlpool;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WhirlpoolRenderer extends GeoEntityRenderer<WhirlpoolEntity> {

    public WhirlpoolRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new WhirlpoolModel());
        this.withScale(5.0f, 5.0f);
    }

}

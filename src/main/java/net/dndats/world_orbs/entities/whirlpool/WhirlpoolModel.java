package net.dndats.world_orbs.entities.whirlpool;

import net.dndats.world_orbs.Worldorbs;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WhirlpoolModel extends GeoModel<WhirlpoolEntity> {

    @Override
    public ResourceLocation getModelResource(WhirlpoolEntity object) {
        return ResourceLocation.fromNamespaceAndPath(Worldorbs.MODID, "geo/whirlpool.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WhirlpoolEntity object) {
        return ResourceLocation.fromNamespaceAndPath(Worldorbs.MODID, "textures/entity/whirlpool_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WhirlpoolEntity object) {
        return ResourceLocation.fromNamespaceAndPath(Worldorbs.MODID, "animations/whirlpool.animation.json");
    }
}

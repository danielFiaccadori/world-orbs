package net.dndats.world_orbs.blocks;

import net.dndats.world_orbs.Worldorbs;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AbstractWorldOrbModel<T extends AbstractWorldOrbBlockEntity<T>> extends GeoModel<T> {

    @Override
    public ResourceLocation getModelResource(T orb) {
        return ResourceLocation.fromNamespaceAndPath(Worldorbs.MODID, "geo/world_orb.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T orb) {
        return orb.getTexture();
    }

    @Override
    public ResourceLocation getAnimationResource(T orb) {
        return ResourceLocation.fromNamespaceAndPath(Worldorbs.MODID, "animations/world_orb.animation.json");
    }

}

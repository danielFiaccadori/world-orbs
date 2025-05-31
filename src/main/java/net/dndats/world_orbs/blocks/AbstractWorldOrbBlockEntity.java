package net.dndats.world_orbs.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class AbstractWorldOrbBlockEntity<T extends AbstractWorldOrbBlockEntity<T>> extends BlockEntity implements GeoBlockEntity {

    protected static final RawAnimation DEPLOY_ANIM = RawAnimation.begin()
            .thenPlay("0");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AbstractWorldOrbBlockEntity(BlockEntityType<T> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public abstract ResourceLocation getTexture();

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>(this, "deploy_controller", 0, this::deployAnimController)
                        .triggerableAnim("deploy", DEPLOY_ANIM)
        );
    }

    protected <E extends BlockEntity & GeoAnimatable> PlayState deployAnimController(AnimationState<E> state) {
        return state.setAndContinue(DEPLOY_ANIM);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


}

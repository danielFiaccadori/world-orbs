package net.dndats.world_orbs.orbs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public abstract class AbstractWorldOrbBlockEntity<T extends AbstractWorldOrbBlockEntity<T>> extends BlockEntity implements GeoBlockEntity {

    private UUID orbUserId;
    private final ResourceLocation abilityId;

    public ResourceLocation getAbilityId() {
        return abilityId;
    }
    public UUID getOrbUserId() {
        return orbUserId;
    }

    public void setOrbUserId(UUID orbUserId) {
        this.orbUserId = orbUserId;
    }

    public boolean hasUser() {
        return orbUserId != null;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        if (orbUserId != null) {
            tag.putUUID("orbUserId", orbUserId);
        }
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.hasUUID("orbUserId")) {
            orbUserId = tag.getUUID("orbUserId");
        } else {
            orbUserId = null;
        }
    }

    protected static final RawAnimation DEPLOY_ANIM = RawAnimation.begin()
            .thenPlay("0");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AbstractWorldOrbBlockEntity(BlockEntityType<T> type, BlockPos pos, BlockState blockState, ResourceLocation abilityId) {
        super(type, pos, blockState);
        this.abilityId = abilityId;
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

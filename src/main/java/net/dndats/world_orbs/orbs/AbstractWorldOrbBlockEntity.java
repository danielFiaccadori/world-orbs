package net.dndats.world_orbs.orbs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
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

    public void playSound(Level level, BlockPos pos, SoundEvent sound) {
        level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
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

    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin()
            .thenPlay("idle");
    protected static final RawAnimation USE_ANIM = RawAnimation.begin()
            .thenPlay("use");
    protected static final RawAnimation ACTIVATE_ANIM = RawAnimation.begin()
            .thenPlay("activate");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AbstractWorldOrbBlockEntity(BlockEntityType<T> type, BlockPos pos, BlockState blockState, ResourceLocation abilityId) {
        super(type, pos, blockState);
        this.abilityId = abilityId;
    }

    public abstract ResourceLocation getTexture();

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>(this, "orb_anim_controller", 0, this::orbAnimController)
                        .triggerableAnim("idle", IDLE_ANIM)
                        .triggerableAnim("use", USE_ANIM)
                        .triggerableAnim("activate", ACTIVATE_ANIM)
        );
    }

    public void triggerUseAnimation() {
        this.triggerAnim("orb_anim_controller", "use");
    }

    public void triggerActivateAnimation() {
        this.triggerAnim("orb_anim_controller", "activate");
    }

    protected <E extends BlockEntity & GeoAnimatable> PlayState orbAnimController(AnimationState<E> state) {
        return state.setAndContinue(IDLE_ANIM);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}

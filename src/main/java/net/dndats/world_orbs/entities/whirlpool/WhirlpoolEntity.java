package net.dndats.world_orbs.entities.whirlpool;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class WhirlpoolEntity extends PathfinderMob implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private Player owner;

    public WhirlpoolEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canCollideWith(@NotNull Entity entity) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public boolean isInWall() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.owner != null) {
            this.teleportTo(this.owner.getX(), this.owner.getY(), this.owner.getZ());
        }

        if (level().isClientSide) {
            spawnSplashOnNearbyEntities();
        }

        if (this.tickCount > 20) {
            this.discard();
        }
    }


    private void spawnSplashOnNearbyEntities() {
        double radius = 1;

        AABB area = this.getBoundingBox().inflate(radius);

        List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, area);

        for (LivingEntity entity : entities) {
            for (int i = 0; i < 3; i++) {
                double offsetX = (random.nextDouble() - 0.5) * entity.getBbWidth();
                double offsetY = random.nextDouble() * entity.getBbHeight();
                double offsetZ = (random.nextDouble() - 0.5) * entity.getBbWidth();

                double px = entity.getX() + offsetX;
                double py = entity.getY() + offsetY;
                double pz = entity.getZ() + offsetZ;

                level().addParticle(ParticleTypes.SPLASH, px, py, pz, 0, 0.02, 0);
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0,
                state -> state.setAndContinue(RawAnimation.begin().thenLoop("activate"))));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D);
    }

}

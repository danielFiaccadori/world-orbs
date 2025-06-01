package net.dndats.world_orbs.orbs.apotheosis;

import net.dndats.world_orbs.abilities.IOrbActiveEffect;
import net.dndats.world_orbs.entities.whirlpool.WhirlpoolEntity;
import net.dndats.world_orbs.registry.ModEntities;
import net.dndats.world_orbs.util.TickScheduler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ApotheosisActiveEffect implements IOrbActiveEffect {

    private static final int RADIUS = 5;
    private static final float DAMAGE_AMOUNT = 4.0F;

    @Override
    public void onActivate(ServerPlayer player) {
        player.sendSystemMessage(Component.literal(
                "You have activated the Apotheosis Orb! This orb grants you a powerful effect that enhances your abilities. Use it wisely!"));
    }

    @Override
    public void onUse(ServerPlayer player) {
        Level level = player.level();

        player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 15, 0));

        if (!level.isClientSide) {
            WhirlpoolEntity whirlpool = new WhirlpoolEntity(ModEntities.WHIRLPOOL.get(), level);
            whirlpool.setOwner(player);
            whirlpool.setPos(player.getX(), player.getY(), player.getZ());
            level.playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.PLAYER_SPLASH_HIGH_SPEED,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );
            level.addFreshEntity(whirlpool);
        }

        AABB area = new AABB(player.blockPosition()).inflate(RADIUS);

        List<LivingEntity> mobs = level.getEntitiesOfClass(LivingEntity.class, area,
                e -> e != player &&
                        e.isAlive() &&
                        !(e instanceof Player) &&
                        !(e instanceof WhirlpoolEntity)
        );

        if (mobs.isEmpty()) {
            return;
        }

        for (LivingEntity mob : mobs) {
            if (!level.isClientSide) {
                mob.moveTo(player.position().add(0, 1, 0));
                TickScheduler.schedule(() -> {
                    if (mob.isAlive() && mob.distanceToSqr(player) <= RADIUS * RADIUS) {
                        player.heal(DAMAGE_AMOUNT);
                        mob.hurt(level.damageSources().playerAttack(player), DAMAGE_AMOUNT);
                        level.playSound(
                                null,
                                player.blockPosition(),
                                SoundEvents.GENERIC_SPLASH,
                                SoundSource.PLAYERS,
                                0.5F,
                                1.0F
                        );
                    }
                }, 10);
            }
        }
    }

}

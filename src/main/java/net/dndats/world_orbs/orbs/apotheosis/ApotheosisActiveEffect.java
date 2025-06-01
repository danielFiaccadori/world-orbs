package net.dndats.world_orbs.orbs.apotheosis;

import net.dndats.world_orbs.abilities.IOrbActiveEffect;
import net.dndats.world_orbs.util.TickScheduler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

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

        AABB area = new AABB(player.blockPosition()).inflate(RADIUS);

        List<LivingEntity> mobs = level.getEntitiesOfClass(LivingEntity.class, area,
                e -> e != player && e.isAlive() && !(e instanceof Player));

        if (mobs.isEmpty()) {
            return;
        }

        for (LivingEntity mob : mobs) {

            if (!level.isClientSide) {
                TickScheduler.schedule(() -> {
                    if (mob.isAlive() && mob.distanceToSqr(player) <= RADIUS * RADIUS) {
                        mob.moveTo(player.position());
                        mob.hurt(level.damageSources().playerAttack(player), DAMAGE_AMOUNT);
                    }
                }, 10);
            }
        }
    }

}

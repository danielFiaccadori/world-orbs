package net.dndats.world_orbs.util;

import net.dndats.world_orbs.orbs.AbstractWorldOrbBlockEntity;
import net.dndats.world_orbs.registry.ActiveEffectRegistry;
import net.dndats.world_orbs.registry.ModData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerActiveExecutor {

    public static void useOrbActiveEffectForPlayer(Level level, BlockPos orbPos, ServerPlayer serverPlayer) {
        if (!(level.getBlockEntity(orbPos) instanceof AbstractWorldOrbBlockEntity<?> orb)) return;

        var abilityId = orb.getAbilityId();;
        var ability = ActiveEffectRegistry.get(abilityId);

        if (ability != null) {
            var data = serverPlayer.getData(ModData.ACTIVE_GLOBAL_EFFECT.get());

            serverPlayer.displayClientMessage(Component.literal(
                    "Cooldown: " + data.getCurrentCooldownTicks() + " of max: " + data.getMaxCooldownTicks()
            ), true);

            if (data.isInCooldown()) return;

            ability.onUse(serverPlayer);
        }
    }

}

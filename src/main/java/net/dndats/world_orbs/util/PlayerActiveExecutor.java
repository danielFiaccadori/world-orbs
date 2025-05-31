package net.dndats.world_orbs.util;

import net.dndats.world_orbs.Worldorbs;
import net.dndats.world_orbs.orbs.AbstractWorldOrbBlockEntity;
import net.dndats.world_orbs.registry.ActiveEffectRegistry;
import net.minecraft.core.BlockPos;
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
            Worldorbs.LOGGER.info("Executing active effect for player: {} with ability: {}", serverPlayer.getName().getString(), abilityId);
            ability.onUse(serverPlayer);
        }
    }

}

package net.dndats.world_orbs.util;

import net.dndats.world_orbs.orbs.AbstractWorldOrbBlockEntity;
import net.dndats.world_orbs.registry.ModData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class ActiveGlobalEffectHelper {

    public static void activateOrbEffectWithIdForPlayer(ResourceLocation orbId, Player player, AbstractWorldOrbBlockEntity<?> orbBlock) {
        player.getData(ModData.ACTIVE_GLOBAL_EFFECT.get()).setActiveOrbId(orbId);
        player.getData(ModData.ACTIVE_GLOBAL_EFFECT.get()).setOrbPos(orbBlock.getBlockPos());
        player.getData(ModData.ACTIVE_GLOBAL_EFFECT.get()).syncData(player);

    }

    public static void deactivateExistingOrbEffects(Player player) {
        player.getData(ModData.ACTIVE_GLOBAL_EFFECT.get()).clear();
    }

}

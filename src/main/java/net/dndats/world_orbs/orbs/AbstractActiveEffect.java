package net.dndats.world_orbs.orbs;

import net.dndats.world_orbs.registry.ModData;
import net.minecraft.server.level.ServerPlayer;

public abstract class AbstractActiveEffect {

    private final int abilityCooldown;

    protected AbstractActiveEffect(int abilityCooldown) {
        this.abilityCooldown = abilityCooldown;
    }

    public int getAbilityCooldown() {
        return abilityCooldown;
    }

    public abstract void onActivate(ServerPlayer player);

    public void onUse(ServerPlayer player) {
        player.getData(ModData.ACTIVE_GLOBAL_EFFECT.get()).fillCurrentCooldownTicks();
        player.getData(ModData.ACTIVE_GLOBAL_EFFECT.get()).syncData(player);
    }

}

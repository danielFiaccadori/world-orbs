package net.dndats.world_orbs.abilities;

import net.minecraft.server.level.ServerPlayer;

public interface IOrbActiveEffect {

    void onActivate(ServerPlayer player);
    void onUse(ServerPlayer player);

}

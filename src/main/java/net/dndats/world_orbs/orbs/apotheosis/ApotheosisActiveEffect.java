package net.dndats.world_orbs.orbs.apotheosis;

import net.dndats.world_orbs.abilities.IOrbActiveEffect;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ApotheosisActiveEffect implements IOrbActiveEffect {

    @Override
    public void onActivate(ServerPlayer player) {
        player.sendSystemMessage(Component.literal(
                "You have activated the Apotheosis Orb! This orb grants you a powerful effect that enhances your abilities. Use it wisely!"));
    }

    @Override
    public void onUse(ServerPlayer player) {
        player.sendSystemMessage(Component.literal(
                "You have used the Apotheosis Orb!"));
    }

}

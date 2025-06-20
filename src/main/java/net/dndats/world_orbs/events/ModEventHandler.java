package net.dndats.world_orbs.events;

import net.dndats.world_orbs.Worldorbs;
import net.dndats.world_orbs.data.ActiveGlobalEffectData;
import net.dndats.world_orbs.registry.ModData;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = Worldorbs.MODID)
public class ModEventHandler {

    @SubscribeEvent
    public static void handleActiveEffectCooldown(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ActiveGlobalEffectData data = player.getData(ModData.ACTIVE_GLOBAL_EFFECT);

            int current = data.getCurrentCooldownTicks();
            int max = data.getMaxCooldownTicks();

            if (current > 0 && max > 0) {
                data.decrementCurrentCooldownTicks(1);

                int newCurrent = data.getCurrentCooldownTicks();

                if ((newCurrent * 10) % max == 0 || newCurrent == 0) {
                    data.syncData(player);
                }

                if (newCurrent >= max) {
                    data.refreshCurrentCooldownTicks();
                    data.syncData(player);
                }
            }
        }
    }

}

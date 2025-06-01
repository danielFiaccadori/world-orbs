package net.dndats.world_orbs.data;

import net.dndats.world_orbs.Worldorbs;
import net.dndats.world_orbs.registry.ModData;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = Worldorbs.MODID)
public class SyncHandler {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        var data = player.getData(ModData.ACTIVE_GLOBAL_EFFECT.get());

        data.syncData(player);
    }

}

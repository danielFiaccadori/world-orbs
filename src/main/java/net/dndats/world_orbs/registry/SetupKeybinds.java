package net.dndats.world_orbs.registry;

import net.dndats.world_orbs.Worldorbs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = Worldorbs.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SetupKeybinds {

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(ModKeybinds.USE_ORB);
    }

}

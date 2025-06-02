package net.dndats.world_orbs.client;

import net.dndats.world_orbs.Worldorbs;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = Worldorbs.MODID)
public class RenderOverlay {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void renderOverlay(RenderGuiEvent.Pre event) {
        Overlay.renderActiveEffectOverlay(event);
    }

}

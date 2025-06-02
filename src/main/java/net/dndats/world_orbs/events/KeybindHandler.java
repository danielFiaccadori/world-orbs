package net.dndats.world_orbs.events;

import net.dndats.world_orbs.Worldorbs;
import net.dndats.world_orbs.data.packets.PacketTriggerPlayerAbility;
import net.dndats.world_orbs.registry.ModData;
import net.dndats.world_orbs.registry.ModKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = Worldorbs.MODID, value = Dist.CLIENT)
public class KeybindHandler {

    @SubscribeEvent
    public static void onUseOrbKeybind(InputEvent.Key event) {
        if (ModKeybinds.USE_ORB.consumeClick()) {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            var data = player.getData(ModData.ACTIVE_GLOBAL_EFFECT.get());

            ResourceLocation orbId = data.getActiveOrbId();
            BlockPos orbPos = data.getOrbPos();

            if (orbId == null || orbPos == null) return;

            PacketTriggerPlayerAbility packet = new PacketTriggerPlayerAbility(orbId, orbPos);
            PacketDistributor.sendToServer(packet);
        }
    }

}

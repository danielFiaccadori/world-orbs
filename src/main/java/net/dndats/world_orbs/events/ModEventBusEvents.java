package net.dndats.world_orbs.events;

import net.dndats.world_orbs.Worldorbs;
import net.dndats.world_orbs.entities.whirlpool.WhirlpoolEntity;
import net.dndats.world_orbs.registry.ModEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = Worldorbs.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntities.WHIRLPOOL.get(), WhirlpoolEntity.createAttributes().build());
    }
}
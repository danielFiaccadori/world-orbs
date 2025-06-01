package net.dndats.world_orbs.registry;

import net.dndats.world_orbs.Worldorbs;
import net.dndats.world_orbs.entities.whirlpool.WhirlpoolEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Worldorbs.MODID);

    public static ResourceKey<EntityType<?>> WHIRLWIND_KEY = ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.withDefaultNamespace("whirlwind"));

    public static final Supplier<EntityType<WhirlpoolEntity>> WHIRLPOOL =
            ENTITIES.register("whirlpool", () -> EntityType.Builder.of(WhirlpoolEntity::new, MobCategory.MISC)
                    .sized(5, 1).build(String.valueOf(WHIRLWIND_KEY)));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }

}

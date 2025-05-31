package net.dndats.world_orbs.registry;

import net.dndats.world_orbs.abilities.IOrbActiveEffect;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ActiveEffectRegistry {

    public static Map<ResourceLocation, Supplier<IOrbActiveEffect> > REGISTRY = new HashMap<>();

    public static void register(ResourceLocation id, Supplier<IOrbActiveEffect> factory) {
        if (REGISTRY.containsKey(id)) {
            throw new IllegalArgumentException("Active effect with ID " + id + " is already registered.");
        }
        REGISTRY.put(id, factory);
    }

    public static IOrbActiveEffect get(ResourceLocation id) {
        Supplier<IOrbActiveEffect> factory = REGISTRY.get(id);
        return factory != null ? factory.get() : null;
    }

}

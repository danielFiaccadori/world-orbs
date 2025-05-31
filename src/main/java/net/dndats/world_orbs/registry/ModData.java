package net.dndats.world_orbs.registry;

import net.dndats.world_orbs.Worldorbs;
import net.dndats.world_orbs.data.ActiveGlobalEffectData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModData {

    // Players
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Worldorbs.MODID);

    public static final Supplier<AttachmentType<ActiveGlobalEffectData>> ACTIVE_GLOBAL_EFFECT = ATTACHMENT_TYPES.register("active_global_effect", () ->
            AttachmentType.serializable(ActiveGlobalEffectData::new).build());

}
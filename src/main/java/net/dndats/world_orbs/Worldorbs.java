package net.dndats.world_orbs;

import com.mojang.logging.LogUtils;
import net.dndats.world_orbs.blocks.test.ApotheosisOrbBlockEntity;
import net.dndats.world_orbs.blocks.test.ApotheosisOrbRenderer;
import net.dndats.world_orbs.registry.ModBlockEntities;
import net.dndats.world_orbs.registry.ModData;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(Worldorbs.MODID)
public class Worldorbs {
    public static final String MODID = "world_orbs";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Worldorbs(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);

        // Registers blocks
        ModBlockEntities.register(modEventBus);

        // Registers data
        ModData.ATTACHMENT_TYPES.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
        }

        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.APOTHEOSIS_ORB_BLOCK_ENTITY.get(),
                    (BlockEntityRendererProvider<? super ApotheosisOrbBlockEntity>) ApotheosisOrbRenderer::new);
        }

    }
}

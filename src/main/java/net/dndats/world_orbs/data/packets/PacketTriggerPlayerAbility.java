package net.dndats.world_orbs.data.packets;

import net.dndats.world_orbs.Worldorbs;
import net.dndats.world_orbs.data.NetworkHandler;
import net.dndats.world_orbs.util.PlayerActiveExecutor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = Worldorbs.MODID, bus = EventBusSubscriber.Bus.MOD)
public record PacketTriggerPlayerAbility(ResourceLocation orbId, BlockPos orbPos) implements CustomPacketPayload {

    public static final Type<PacketTriggerPlayerAbility> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Worldorbs.MODID, "player_use_ability_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PacketTriggerPlayerAbility> STREAM_CODEC =
            StreamCodec.of(
                    (buf, packet) -> {
                        buf.writeResourceLocation(packet.orbId());
                        buf.writeBlockPos(packet.orbPos());
                    },
                    buf -> {
                        ResourceLocation orbId = buf.readResourceLocation();
                        BlockPos orbPos = buf.readBlockPos();
                        return new PacketTriggerPlayerAbility(orbId, orbPos);
                    }
            );

    public static void handleData(PacketTriggerPlayerAbility message, IPayloadContext context) {
        if (!context.flow().isServerbound()) return;

        context.enqueueWork(() -> {
            Worldorbs.LOGGER.info("Sent message to execute active effect for player: {}", context.player().getName().getString());
            ServerPlayer player = (ServerPlayer) context.player();
            Level level = player.level();
            PlayerActiveExecutor.useOrbActiveEffectForPlayer(level, message.orbPos(), player);
        });
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        NetworkHandler.addNetworkMessage(
                PacketTriggerPlayerAbility.TYPE,
                PacketTriggerPlayerAbility.STREAM_CODEC,
                PacketTriggerPlayerAbility::handleData
        );
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}


package net.dndats.world_orbs.data.packets;

import net.dndats.world_orbs.Worldorbs;
import net.dndats.world_orbs.data.ActiveGlobalEffectData;
import net.dndats.world_orbs.data.NetworkHandler;
import net.dndats.world_orbs.registry.ModData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@EventBusSubscriber(modid = Worldorbs.MODID, bus = EventBusSubscriber.Bus.MOD)
public record PacketSetActiveEffectData(ActiveGlobalEffectData data) implements CustomPacketPayload {

    public static final Type<PacketSetActiveEffectData> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Worldorbs.MODID, "player_active_effect_data_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PacketSetActiveEffectData> STREAM_CODEC = StreamCodec
            .of((RegistryFriendlyByteBuf buffer, PacketSetActiveEffectData packet) ->
                    buffer.writeNbt(packet.data().serializeNBT(buffer.registryAccess())), (RegistryFriendlyByteBuf buffer) -> {
                PacketSetActiveEffectData message = new PacketSetActiveEffectData(new ActiveGlobalEffectData());
                message.data().deserializeNBT(buffer.registryAccess(), Objects.requireNonNull(buffer.readNbt()));
                return message;
            });

    public static void handleData(final PacketSetActiveEffectData message, final IPayloadContext context) {
        if (context.flow().isClientbound() && message.data() != null) {
            context.enqueueWork(() -> {
                context.player().getData(ModData.ACTIVE_GLOBAL_EFFECT).deserializeNBT(context.player().registryAccess(),
                        message.data().serializeNBT(context.player().registryAccess()));
                context.player().setData((ModData.ACTIVE_GLOBAL_EFFECT), message.data());
            }).exceptionally(e -> {
                context.connection().disconnect(Component.literal(e.getMessage()));
                return null;
            });
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        NetworkHandler.addNetworkMessage(
                PacketSetActiveEffectData.TYPE,
                PacketSetActiveEffectData.STREAM_CODEC,
                PacketSetActiveEffectData::handleData
        );
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
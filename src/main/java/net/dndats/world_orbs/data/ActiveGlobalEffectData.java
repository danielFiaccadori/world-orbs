package net.dndats.world_orbs.data;

import net.dndats.world_orbs.data.packets.PacketSetActiveEffectData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class ActiveGlobalEffectData implements INBTSerializable<CompoundTag> {

    private BlockPos orbPos;
    private int currentCooldownTicks = 0;
    private int maxCooldownTicks = 0;
    private ResourceLocation activeOrbId;

    // --- Orb position ---

    public BlockPos getOrbPos() {
        return orbPos;
    }

    public void setOrbPos(BlockPos orbPos) {
        this.orbPos = orbPos;
    }

    // --- Cooldowns ---

    public int getMaxCooldownTicks() {
        return maxCooldownTicks;
    }

    public void setMaxCooldownTicks(int maxCooldownTicks) {
        this.maxCooldownTicks = maxCooldownTicks;
    }

    public int getCurrentCooldownTicks() {
        return currentCooldownTicks;
    }

    public void refreshCurrentCooldownTicks() {
        this.currentCooldownTicks = 0;
    }

    public void fillCurrentCooldownTicks() {
        this.currentCooldownTicks = maxCooldownTicks;
    }

    public void decrementCurrentCooldownTicks(int toDecrement) {
        this.currentCooldownTicks -= toDecrement;
    }

    public boolean isInCooldown() {
        return currentCooldownTicks > 0;
    }

    // --- Identifiers ---

    public ResourceLocation getActiveOrbId() {
        return activeOrbId;
    }

    public void setActiveOrbId(ResourceLocation activeOrbId) {
        this.activeOrbId = activeOrbId;
    }

    public void clear() {
        this.orbPos = null;
        this.currentCooldownTicks = -1;
        this.activeOrbId = null;
    }

    // --- Serialization ---

    @Override
    public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = new CompoundTag();
        if (orbPos != null) {
            tag.putInt("orbPosX", orbPos.getX());
            tag.putInt("orbPosY", orbPos.getY());
            tag.putInt("orbPosZ", orbPos.getZ());
        }
        tag.putInt("currentCooldownTicks", currentCooldownTicks);
        tag.putInt("maxCooldownTicks", maxCooldownTicks);

        if (activeOrbId != null) {
            tag.putString("activeOrbId", activeOrbId.toString());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, CompoundTag tag) {
        if (tag.contains("orbPosX")) {
            orbPos = new BlockPos(
                    tag.getInt("orbPosX"),
                    tag.getInt("orbPosY"),
                    tag.getInt("orbPosZ")
            );
        }

        if (tag.contains("currentCooldownTicks")) {
            currentCooldownTicks = tag.getInt("currentCooldownTicks");
        }

        if (tag.contains("maxCooldownTicks")) {
            maxCooldownTicks = tag.getInt("maxCooldownTicks");
        }

        if (tag.contains("activeOrbId")) {
            activeOrbId = ResourceLocation.tryParse(tag.getString("activeOrbId"));
        }
    }

    public void syncData(Entity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new PacketSetActiveEffectData(this));
        }
    }

}

package net.dndats.world_orbs.data;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

public class ActiveGlobalEffectData implements INBTSerializable<CompoundTag> {

    private BlockPos orbPos;
    private long maxCooldown = -1;
    private ResourceLocation activeOrbId;

    public BlockPos getOrbPos() {
        return orbPos;
    }

    public void setOrbPos(BlockPos orbPos) {
        this.orbPos = orbPos;
    }

    public long getMaxCooldown() {
        return maxCooldown;
    }

    public void setMaxCooldown(long maxCooldown) {
        this.maxCooldown = maxCooldown;
    }

    public ResourceLocation getActiveOrbId() {
        return activeOrbId;
    }

    public void setActiveOrbId(ResourceLocation activeOrbId) {
        this.activeOrbId = activeOrbId;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = new CompoundTag();
        if (orbPos != null) {
            tag.putInt("orbPosX", orbPos.getX());
            tag.putInt("orbPosY", orbPos.getY());
            tag.putInt("orbPosZ", orbPos.getZ());
        }
        tag.putLong("maxCooldown", maxCooldown);

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

        if (tag.contains("maxCooldown")) {
            maxCooldown = tag.getLong("maxCooldown");
        }

        if (tag.contains("activeOrbId")) {
            activeOrbId = ResourceLocation.tryParse(tag.getString("activeOrbId"));
        }
    }
}

package net.dndats.world_orbs.client;

import net.dndats.world_orbs.registry.ModData;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@OnlyIn(Dist.CLIENT)
public class Overlay {

    public static void renderActiveEffectOverlay(RenderGuiEvent.Pre event) {
        Player player = Minecraft.getInstance().player;

        if (player != null && canRender(player)) {
            var data = player.getData(ModData.ACTIVE_GLOBAL_EFFECT.get());
            int currentCooldown = data.getCurrentCooldownTicks();
            int totalCooldown = data.getMaxCooldownTicks();

            if (totalCooldown <= 0) return;

            double cooldownPercent = 100.0 - ((currentCooldown * 100.0) / totalCooldown);

            cooldownPercent = Mth.clamp(cooldownPercent, 0, 100);

            String texturePath = getCooldownOverlayPercent(cooldownPercent);

            OverlayHelper.renderCooldownOverlay(event, texturePath, player);
        }
    }

    private static boolean canRender(Player player) {
        return player.getData(ModData.ACTIVE_GLOBAL_EFFECT.get()).getOrbPos() != null;
    }

    private static String getCooldownOverlayPercent(double cooldown) {
        int[] thresholds = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        String basePath = "world_orbs:textures/screens/cooldown/active_ability_cd_";

        for (int threshold : thresholds) {
            if (cooldown <= threshold) {
                return basePath + threshold + ".png";
            }
        }

        return basePath + "100.png";
    }

}

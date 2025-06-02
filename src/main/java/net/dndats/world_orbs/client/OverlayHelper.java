package net.dndats.world_orbs.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

public class OverlayHelper {

    private static void renderOverlay(RenderGuiEvent.Pre event, String filepath, Player player, int posX, int posY, int textureSize, float alpha, float[] color, Runnable blendFunc) {
        if (player == null) return;

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        blendFunc.run();

        RenderSystem.setShaderColor(color[0], color[1], color[2], alpha);

        event.getGuiGraphics().blit(
                ResourceLocation.parse(filepath),
                posX, posY, 0, 0,
                textureSize, textureSize,
                textureSize, textureSize
        );

        RenderSystem.depthMask(true);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void renderCooldownOverlay(RenderGuiEvent.Pre event, String filepath, Player player) {
        Window window = Minecraft.getInstance().getWindow();
        double scale = window.getGuiScale();
        int width = (int) (window.getScreenWidth() / scale);
        int height = (int) (window.getScreenHeight() / scale);

        int textureSize = 32;
        int halfTexture = textureSize / 2;
        int posX = (width / 2) - halfTexture;
        int posY = ((height / 2) - textureSize) + 80;

        float[] color = new float[]{1.0f, 1.0f, 1.0f};

        renderOverlay(event, filepath, player, posX, posY, textureSize, 1.0f, color, () ->
                RenderSystem.blendFunc(
                        GlStateManager.SourceFactor.SRC_ALPHA,
                        GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA
                )
        );
    }

}

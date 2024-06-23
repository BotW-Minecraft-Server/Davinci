package link.botwmcs.davinci.client.gui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class ColorPanel {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("textures/gui/demo_background.png");

    public static void renderPanel(GuiGraphics guiGraphics, int startX, int startY, int height, int width, int color) {
        int defaultPanelHeight = 166;
        int defaultPanelWidth = 248;
        int sliceWidth = 5;
        int sliceHeight = 5;

        float alpha = 1.0F;
        guiGraphics.setColor(((color >> 16) & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, (color & 0xFF) / 255f, alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        guiGraphics.blitNineSliced(BACKGROUND_TEXTURE, startX, startY, width, height, sliceWidth, sliceHeight, defaultPanelWidth, defaultPanelHeight, 0, 0);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        RenderSystem.disableDepthTest();
    }

}

package link.botwmcs.davinci.client.gui.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

public class BadgeContent {
    private static final Minecraft CLIENT = Minecraft.getInstance();

    // code from https://github.com/TerraformersMC/ModMenu/blob/1.21/src/main/java/com/terraformersmc/modmenu/util/DrawingUtil.java
    public static void drawBadge(GuiGraphics guiGraphics, int x, int y, int tagWidth, FormattedCharSequence text, int outlineColor, int fillColor, int textColor) {
        guiGraphics.fill(x + 1, y - 1, x + tagWidth, y, outlineColor);
        guiGraphics.fill(x, y, x + 1, y + CLIENT.font.lineHeight, outlineColor);
        guiGraphics.fill(x + 1, y + 1 + CLIENT.font.lineHeight - 1, x + tagWidth, y + CLIENT.font.lineHeight + 1, outlineColor);
        guiGraphics.fill( x + tagWidth, y, x + tagWidth + 1, y + CLIENT.font.lineHeight, outlineColor);
        guiGraphics.fill( x + 1, y, x + tagWidth, y + CLIENT.font.lineHeight, fillColor);
        guiGraphics.drawString(CLIENT.font, text, (int) (x + 1 + (tagWidth - CLIENT.font.width(text)) / (float) 2), y + 1, textColor, false);
    }

    public static void drawBadge(GuiGraphics guiGraphics, int x, int y, Component component, int outlineColor, int fillColor, int textColor) {
        drawBadge(guiGraphics, x, y, CLIENT.font.width(component) + 4, FormattedCharSequence.forward(component.getString(), Style.EMPTY), outlineColor, fillColor, textColor);
    }

    public static void drawLoreLikeBadge(GuiGraphics guiGraphics, int x, int y, Component component) {
        drawBadge(guiGraphics, x, y, component, 0x380C99, 0x150C13, 0xFFFFFF);
    }

    public static void drawCreateLikeBadge(GuiGraphics guiGraphics, int x, int y, Component component) {
        drawBadge(guiGraphics, x, y, component, 0x312F2F, 0x0A0C08, 0xFFFFFF);
    }

    public static void drawDefaultPlayerBadge(GuiGraphics guiGraphics, int x, int y, Component component) {
        drawBadge(guiGraphics, x, y, component, 0xff2b4b7c, 0xff0e2a55, 0xFFFFFF);
    }

}

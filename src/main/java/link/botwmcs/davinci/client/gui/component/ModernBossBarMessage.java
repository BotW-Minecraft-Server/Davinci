package link.botwmcs.davinci.client.gui.component;

import com.mojang.blaze3d.vertex.PoseStack;
import link.botwmcs.davinci.Davinci;
import link.botwmcs.davinci.client.gui.component.util.LerpedFloatCalculator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameType;

@Environment(EnvType.CLIENT)
public class ModernBossBarMessage {
    private static Component currentComponent;
    private static boolean enabled;
    private static int tick = 0;
    private static LerpedFloatCalculator componentSize = LerpedFloatCalculator.linear();
    private static ModernBossBarMessage instance = new ModernBossBarMessage();

    public void onShowHUDMessage(Component component, int hiddenTick) {
        currentComponent = component;
        tick = hiddenTick;
    }

    public void tick() {
        if (tick > 0) {
            tick--;
        } else {
            currentComponent = null;
        }
        componentSize.chase(currentComponent != null ? Minecraft.getInstance().font.width(currentComponent) + 17 : 0.0F, 0.1F, LerpedFloatCalculator.Chaser.EXP);
        componentSize.tickChaser();
    }

    public void render(GuiGraphics guiGraphics, float tickDelta) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.options.hideGui || minecraft.gameMode.getPlayerMode() == GameType.SPECTATOR) {
            return;
        }
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(minecraft.getWindow().getGuiScaledWidth() / 2 - 91, 30, 0);
        int size = (int) componentSize.getValue(tickDelta);
        if (size > 1) {
            enabled = true;
            poseStack.pushPose();
            poseStack.translate(size / -2F + 91, -27, 100);
            // left blank
            ResourceLocation createWidgets = new ResourceLocation(Davinci.MODID, "textures/gui/widgets.png");
            guiGraphics.blit(createWidgets, -6, 0, 0, 0, 6, 20, 256, 256);
            // right blank
            guiGraphics.blit(createWidgets, size, 0, 6, 0, 6, 20, 256, 256);
            // component bg
            guiGraphics.blit(createWidgets, 0, 0, 0, 0 + (128 - size / 2F), 20, size, 20, 256, 256);
            poseStack.popPose();

            // render text
            Font font = minecraft.font;
            if (currentComponent != null && font.width(currentComponent) < size - 10) {
                poseStack.pushPose();
                poseStack.translate(font.width(currentComponent) / 2F + 82, -27, 100);
                guiGraphics.drawCenteredString(font, currentComponent, 9 - font.width(currentComponent) / 2, 6, 0xFFFFFF);
                poseStack.popPose();
            }
        } else {
            enabled = false;
        }
        poseStack.translate(91, -9, 0);
        poseStack.scale(0.925f, 0.925f, 1);
        poseStack.popPose();
    }

    public static ModernBossBarMessage getInstance() {
        return instance;
    }

    public static Component getCurrentComponent() {
        return currentComponent;
    }

    public static boolean isEnabled() {
        return enabled;
    }
}

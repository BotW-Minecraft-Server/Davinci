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
public class TrainBarMessage {
    private static Component currentComponent;
    private static boolean enabled;
    private static int tick = 0;
    private static LerpedFloatCalculator componentSize = LerpedFloatCalculator.linear();
    private static int x;
    private static int y;

    public void onShowHUDMessage(Component component, int hiddenTick) {
        currentComponent = component;
        tick = hiddenTick;
        x = 0;
        y = 0;
    }

    public void onShowHUDMessage(Component component, int hiddenTick, int guiX, int guiY) {
        currentComponent = component;
        tick = hiddenTick;
        x = guiX;
        y = guiY;
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
        poseStack.translate(minecraft.getWindow().getGuiScaledWidth() / 2 - 91 + x, minecraft.getWindow().getGuiScaledHeight() - 29 + y, 0);
//        poseStack.translate(x, y, 0);

        int size = (int) componentSize.getValue(tickDelta);
        if (size > 1) {
            enabled = true;
            poseStack.pushPose();
            if (DynamicIsland.isIslandEnabled()) {
                poseStack.translate(size / -2F + 91, -50, 100);
            } else {
                poseStack.translate(size / -2F + 91, -27, 100);
            }
            // left blank
            ResourceLocation createWidgets = new ResourceLocation(Davinci.MODID, "textures/gui/widgets.png");
            guiGraphics.blit(createWidgets, -3, 0, 8, 40, 3, 16, 256, 256);
            // right blank
            guiGraphics.blit(createWidgets, size, 0, 11, 40, 3, 16, 256, 256);
            // component bg
            guiGraphics.blit(createWidgets, 0, 0, 0, 0 + (128 - size / 2F), 61, size, 16, 256, 256);
            poseStack.popPose();

            // render text
            Font font = minecraft.font;
            if (currentComponent != null && font.width(currentComponent) < size - 10) {
                poseStack.pushPose();
                if (DynamicIsland.isIslandEnabled()) {
                    poseStack.translate(font.width(currentComponent) / 2F + 82, -50, 100);
                } else {
                    poseStack.translate(font.width(currentComponent) / 2F + 82, -27, 100);
                }
                guiGraphics.drawCenteredString(font, currentComponent, 9 - font.width(currentComponent) / 2, 4, 0xFFFFFF);
                poseStack.popPose();
            }
        } else {
            enabled = false;
        }
        poseStack.translate(91, -9, 0);
        poseStack.scale(0.925f, 0.925f, 1);
        poseStack.popPose();


    }
    public static TrainBarMessage getInstance() {
        return new TrainBarMessage();
    }

    public static boolean isEnabled() {
        return enabled;
    }
    public static Component getCurrentComponent() {
        return currentComponent;
    }
}

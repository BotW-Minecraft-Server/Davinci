package link.botwmcs.davinci.client.gui.screen.ltsx;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class LtsxServerInfoScreen extends Screen {
    private final boolean showBackground;
    private static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation("textures/block/bricks.png");

    public LtsxServerInfoScreen(Component title, boolean showBackground) {
        super(title);
        this.showBackground = showBackground;
    }

    private void renderBlockBackground(GuiGraphics guiGraphics) {
        if (this.minecraft.level != null || !this.showBackground) {
            guiGraphics.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
        } else {
            guiGraphics.setColor(0.25f, 0.25f, 0.25f, 1.0f);
            int i = 32;
            guiGraphics.blit(BACKGROUND_LOCATION, 0, 0, 0, 0.0f, 0.0f, this.width, this.height, 32, 32);
            guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBlockBackground(guiGraphics);
    }

    @Override
    public void tick() {
        super.tick();
    }
}

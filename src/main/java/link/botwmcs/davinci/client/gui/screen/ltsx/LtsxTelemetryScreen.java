package link.botwmcs.davinci.client.gui.screen.ltsx;

import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class LtsxTelemetryScreen extends Screen {
    private final Screen lastScreen;
    private final Options options;

    public LtsxTelemetryScreen(Screen lastScreen, Options options) {
        super(Component.literal("LTSX Telemetry Screen"));
        this.lastScreen = lastScreen;
        this.options = options;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.lastScreen);
    }
}

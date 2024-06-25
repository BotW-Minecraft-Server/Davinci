package link.botwmcs.davinci.client.gui.screen.ltsx;

import link.botwmcs.davinci.client.gui.component.ColorPanel;
import link.botwmcs.davinci.client.gui.component.ContentScrollWidget;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.telemetry.TelemetryEventWidget;
import net.minecraft.client.gui.screens.telemetry.TelemetryInfoScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.List;

public class LtsxTelemetryScreen extends Screen {
    private final Screen lastScreen;
    private final Options options;
    private ContentScrollWidget scrollWidget;
    private double savedScroll;

    public LtsxTelemetryScreen(Screen lastScreen, Options options) {
        super(Component.literal("LTSX Telemetry Screen"));
        this.lastScreen = lastScreen;
        this.options = options;
    }

    @Override
    protected void init() {
        FrameLayout frameLayout = new FrameLayout();
        frameLayout.defaultChildLayoutSetting().padding(8);
        frameLayout.setMinHeight(this.height);
        GridLayout gridLayout = frameLayout.addChild(new GridLayout(), frameLayout.newChildLayoutSettings().align(0.5f, 0.0f));
        gridLayout.defaultCellSetting().alignHorizontallyCenter().paddingBottom(8);
        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(1);
        this.scrollWidget = new ContentScrollWidget(0, 0, this.width - 40, this.height - 60);
        this.scrollWidget.buildOriginalContent(generateTelemetry());
        this.scrollWidget.setScrollAmount(this.savedScroll);
        this.scrollWidget.setOnScrolledListener(d -> this.savedScroll = d);
        this.setInitialFocus(this.scrollWidget);
        rowHelper.addChild(this.scrollWidget);
        frameLayout.arrangeElements();
        GridLayout gridLayout3 = this.twoButtonContainer(
                Button.builder(Component.translatable("menu.telemetry.minecraft"), this::openMinecraftTelemetry).build(),
                Button.builder(CommonComponents.GUI_DONE, this::openLastScreen).build());
        frameLayout.addChild(gridLayout3, frameLayout.newChildLayoutSettings().align(0.5f, 1.0f));
        FrameLayout.alignInRectangle(frameLayout, 0, 0, this.width, this.height, 0.5f, 0.0f);
        frameLayout.visitWidgets(guiEventListener -> {
            AbstractWidget cfr_ignored_0 = (AbstractWidget)this.addRenderableWidget(guiEventListener);
        });
        frameLayout.arrangeElements();
        // this.telemetryEventWidget = new TelemetryEventWidget(0, 0, this.width - 40, gridLayout3.getY() - (gridLayout2.getY() + gridLayout2.getHeight()) - 16, this.minecraft.font);



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

    private GridLayout twoButtonContainer(AbstractWidget button1, AbstractWidget button2) {
        GridLayout gridLayout = new GridLayout();
        gridLayout.defaultCellSetting().alignHorizontallyCenter().paddingHorizontal(4);
        gridLayout.addChild(button1, 0, 0);
        gridLayout.addChild(button2, 0, 1);
        return gridLayout;
    }

    private void openLastScreen(Button button) {
        this.minecraft.setScreen(this.lastScreen);
    }

    private void openMinecraftTelemetry(Button button) {
        this.minecraft.setScreen(new TelemetryInfoScreen(this.lastScreen, this.options));
    }

    private ContentScrollWidget.ContentBuilder generateTelemetry() {
        ContentScrollWidget.ContentBuilder contentBuilder = new ContentScrollWidget.ContentBuilder(this.scrollWidget.containerWidth());
        contentBuilder.addHeader(this.font, Component.translatable("menu.telemetry.content.title.1"));
        contentBuilder.addSpacer(10);
        contentBuilder.addHeader(this.font, Component.translatable("menu.telemetry.content.title.2"));
        contentBuilder.addSpacer(10);
        contentBuilder.addLine(this.font, Component.translatable("menu.telemetry.content.playLtsx.playerUserId"));
        contentBuilder.addLine(this.font, Component.translatable("menu.telemetry.content.playLtsx.basicPlayerInfo"));
        contentBuilder.addLine(this.font, Component.translatable("menu.telemetry.content.playLtsx.operatingSystem"));
        contentBuilder.addLine(this.font, Component.translatable("menu.telemetry.content.playLtsx.runtimeEnvironment"));
        contentBuilder.addLine(this.font, Component.translatable("menu.telemetry.content.playLtsx.launcherName"));
        contentBuilder.addLine(this.font, Component.translatable("menu.telemetry.content.playLtsx.clientModified"));
        contentBuilder.addSpacer(10);
        contentBuilder.addHeader(this.font, Component.translatable("menu.telemetry.content.title.3"));
        contentBuilder.addSpacer(10);
        contentBuilder.addLine(this.font, Component.translatable("menu.telemetry.content.antiCheat.title"));
        contentBuilder.addSpacer(5);
        contentBuilder.addLine(this.font, Component.translatable("menu.telemetry.content.antiCheat.inGameScreenshots"));
        contentBuilder.addLine(this.font, Component.translatable("menu.telemetry.content.antiCheat.replayModData"));
        contentBuilder.addLine(this.font, Component.translatable("menu.telemetry.content.antiCheat.blockInteractionData"));
        contentBuilder.addSpacer(5);
        contentBuilder.addLine(this.font, Component.translatable("menu.telemetry.content.mediaPlayer.title"));
        contentBuilder.addSpacer(5);
        contentBuilder.addLine(this.font, Component.translatable("menu.telemetry.content.mediaPlayer.songPlaylist"));
        contentBuilder.addLine(this.font, Component.translatable("menu.telemetry.content.mediaPlayer.musicPlatformInfo"));
        contentBuilder.addSpacer(5);
        contentBuilder.addLine(this.font, Component.translatable("menu.telemetry.content.clientUpdate.title"));
        contentBuilder.addSpacer(5);
        contentBuilder.addLine(this.font, Component.translatable("menu.telemetry.content.clientUpdate.clientVersion"));
        contentBuilder.addLine(this.font, Component.translatable("menu.telemetry.content.clientUpdate.clientFilePath"));
        contentBuilder.addSpacer(10);
        contentBuilder.addHeader(this.font, Component.translatable("menu.telemetry.content.conclusion"));
        contentBuilder.addSpacer(10);
        return contentBuilder;
    }
}

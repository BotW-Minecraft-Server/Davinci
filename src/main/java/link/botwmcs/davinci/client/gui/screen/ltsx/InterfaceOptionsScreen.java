package link.botwmcs.davinci.client.gui.screen.ltsx;

import link.botwmcs.davinci.client.gui.component.ColorButton;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screens.ChatOptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SkinCustomizationScreen;
import net.minecraft.network.chat.Component;

import java.util.function.Supplier;

public class InterfaceOptionsScreen extends Screen {
    private final Screen lastScreen;
    private final Options options;

    protected InterfaceOptionsScreen(Screen lastScreen, Options options) {
        super(Component.literal("Interface Options Screen"));
        this.lastScreen = lastScreen;
        this.options = options;
    }

    @Override
    protected void init() {
        GridLayout gridLayout = new GridLayout();
        gridLayout.defaultCellSetting().paddingHorizontal(5).paddingBottom(4).alignHorizontallyCenter();
        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(2);
        rowHelper.addChild(this.openScreenButton(Component.translatable("options.chat.title"), () -> new ChatOptionsScreen(this, this.options)));
        rowHelper.addChild(this.openScreenButton(Component.translatable("options.skinCustomisation"), () -> new SkinCustomizationScreen(this, this.options)));
        rowHelper.addChild(this.openScreenButton(Component.translatable("options.accessibility.title"), () -> new AccessibilityOptionsScreen(this, this.options)));


        gridLayout.arrangeElements();
        FrameLayout.alignInRectangle(gridLayout, 0, this.height / 6 - 12, this.width, this.height, 0.5f, 0.0f);
        gridLayout.visitWidgets(this::addRenderableWidget);
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

    private Button openScreenButton(Component text, int color, Supplier<Screen> screenSupplier) {
        return new ColorButton(0, 0, 150, 20, text, color, button -> this.minecraft.setScreen(screenSupplier.get()));
        // return Button.builder(text, button -> this.minecraft.setScreen((Screen)screenSupplier.get())).build();
    }

    private Button openScreenButton(Component text, Supplier<Screen> screenSupplier) {
        return this.openScreenButton(text, 0xFFFFFFFF, screenSupplier);
    }
}

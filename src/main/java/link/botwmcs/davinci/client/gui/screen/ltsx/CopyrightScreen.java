package link.botwmcs.davinci.client.gui.screen.ltsx;

import link.botwmcs.davinci.Davinci;
import link.botwmcs.davinci.client.gui.component.BotwmcsLogoRenderer;
import link.botwmcs.davinci.client.gui.component.ColorButton;
import link.botwmcs.davinci.client.gui.component.ColorPanel;
import link.botwmcs.davinci.client.gui.component.util.Accumulator;
import link.botwmcs.davinci.client.gui.component.util.RandomColorGenerator;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.CreditsAndAttributionScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Objects;

public class CopyrightScreen extends Screen {
    private static final Component TITLE = Component.translatable("menu.copyright.title");
    private static final ResourceLocation LINK_LOGO = new ResourceLocation(Davinci.MODID, "textures/gui/link.png");
    private static final int PANEL_WIDTH = 230;
    private static final int PANEL_HEIGHT = 100;
    private static int PANEL_COLOR = 0x505050;
    private final Accumulator accumulatorX;
    private final Accumulator accumulatorY;
    private final Screen lastScreen;
    protected CopyrightScreen(Screen lastScreen) {
        super(TITLE);
        this.lastScreen = lastScreen;
        this.accumulatorX = new Accumulator(1, true);
        this.accumulatorY = new Accumulator(1, true);
    }

    @Override
    public void init() {
        super.init();
        final AbstractWidget mojangButton = addRenderableWidget(new ColorButton(this.width / 2 - 98 - 2, this.height / 2 + 60, 98, 20, Component.translatable("menu.copyright.mojang"), 0xFFFFFFFF, (lambda) -> {
            this.minecraft.setScreen(new CreditsAndAttributionScreen(this));
        }));
        mojangButton.setTooltip(Tooltip.create(Component.translatable("menu.copyright.mojang.tooltip")));

        final AbstractWidget aboutButton = addRenderableWidget(new ColorButton(this.width / 2 + 2, this.height / 2 + 60, 98, 20, Component.translatable("menu.copyright.ltsx"), 0xFF008FE1, (lambda) -> {
            Util.getPlatform().openUri("https://persists.link/about");
        }));
        aboutButton.setTooltip(Tooltip.create(Component.translatable("menu.titlescreen.openWebsite.tooltip")));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics);
        renderPanel(guiGraphics);
        renderText(guiGraphics);
        renderBouncingItem(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.lastScreen);
    }

    @Override
    public void tick() {
        int appleSize = 16;
        accumulatorX.incrementTick();
        accumulatorY.incrementTick();

        if (accumulatorX.getValue() <= 0 || accumulatorX.getValue() >= this.width - appleSize) {
            accumulatorX.toggleMode();
            this.PANEL_COLOR = RandomColorGenerator.generateRandomColor();
        }
        if (accumulatorY.getValue() <= 0 || accumulatorY.getValue() >= this.height - appleSize) {
            accumulatorY.toggleMode();
            this.PANEL_COLOR = RandomColorGenerator.generateRandomColor();
        }
        super.tick();
    }

    private void renderPanel(GuiGraphics guiGraphics) {
        ColorPanel.renderPanel(guiGraphics, this.width / 2 - PANEL_WIDTH / 2, this.height / 2 - PANEL_HEIGHT / 2, PANEL_HEIGHT, PANEL_WIDTH, PANEL_COLOR);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        // Render logo
        guiGraphics.blit(LINK_LOGO, this.width / 2 - 130, this.height / 2 - 96 / 2, 0.0F, 0.0F, 96, 96, 96, 96);
    }

    private void renderText(GuiGraphics guiGraphics) {
        int baseLine = this.height / 2 - 25;
        int baseWidth = this.width / 2 + 20;
        int lineHeight = 10;
        guiGraphics.drawCenteredString(this.font, TITLE, this.width / 2, 20, 0xFFFFFF);
        guiGraphics.drawCenteredString(this.font, "© 2021-2022 BotWMCS LTSX", baseWidth, baseLine, 0xFFFFFF);
        baseLine += lineHeight;
        guiGraphics.drawCenteredString(this.font, "Persists Realms", baseWidth, baseLine, 0xFFFFFF);
        baseLine += lineHeight;
        guiGraphics.drawCenteredString(this.font, "Powered by RealmsHost", baseWidth, baseLine, 0xFFFFFF);
        baseLine += lineHeight;
        guiGraphics.drawCenteredString(this.font, "Version 1.0.0", baseWidth, baseLine, 0xFFFFFF);
        baseLine += lineHeight;
        guiGraphics.drawCenteredString(this.font, "Licensed under the MIT License", baseWidth, baseLine, 0xFFFFFF);
    }

    private void renderBouncingItem(GuiGraphics guiGraphics) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.renderItem(new ItemStack(Items.GOLDEN_APPLE), accumulatorX.getValue(), accumulatorY.getValue());
        guiGraphics.renderTooltip(this.font, Component.translatable("menu.copyright.apple.tooltip"), accumulatorX.getValue(), accumulatorY.getValue());
    }
}

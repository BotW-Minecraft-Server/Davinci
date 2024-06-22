package link.botwmcs.davinci.client.gui.screen;

import link.botwmcs.davinci.client.gui.component.*;
import link.botwmcs.davinci.client.gui.component.util.Accumulator;
import link.botwmcs.davinci.client.gui.component.util.RandomColorGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class DemoScreen extends Screen {
    private final List<ClickableSelector> itemList = GeneralSelector.createDefaultItemList();
    private int color = 0xFFFFFF;
    private final int buttonComponentWidth = 10 + 76 + 10 + 76 + 10 + 76 + 10;
    private int screenWidth;
    private int screenHeight;
    private final Accumulator accumulatorX;
    private final Accumulator accumulatorY;
    private ItemStack itemStack;

    public DemoScreen(Component title) {
        super(title);
        this.accumulatorX = new Accumulator(1, true);
        this.accumulatorY = new Accumulator(1, true);
    }

    private void renderBouncingItems(GuiGraphics guiGraphics, int x, int y, ItemStack itemStack) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.renderItem(itemStack, x, y);
    }

    @Override
    protected void init() {
        super.init();
        int buttonStartX = this.width / 2 - buttonComponentWidth / 2 + 10;
        int buttonStartY = this.height / 2 + 40 / 2 - 20 / 2;
        this.addRenderableWidget(new ColorButton(buttonStartX, buttonStartY, 76, 20, Component.literal("Open Selector"), 0x00008FE1, (button) -> {
            Minecraft.getInstance().setScreen(new GeneralSelector(Component.literal("Selector"), itemList, false));
        }));
        this.addRenderableWidget(new ColorButton(buttonStartX + 76 + 10, buttonStartY, 76, 20, Component.literal("Change color"), 0xFFFFFF, (button) -> {
            this.color = RandomColorGenerator.generateRandomColor();
            this.setFocused(false);
        }));
        this.addRenderableWidget(new ColorButton(buttonStartX + 76 + 10 + 76 + 10, buttonStartY, 76, 20, Component.literal("Close"), 0xFF0000, (button) -> {
            this.onClose();
        }));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics);
        int panelStartX = this.width / 2 - buttonComponentWidth / 2;
        int panelStartY = this.height / 2;
        int panelHeight = 40;
        int panelWidth = buttonComponentWidth;
        ColorPanel.renderPanel(guiGraphics, panelStartX, panelStartY, panelHeight, panelWidth, color);

        // Bouncing apple
        this.screenHeight = guiGraphics.guiHeight();
        this.screenWidth = guiGraphics.guiWidth();
        this.renderBouncingItems(guiGraphics, accumulatorX.getValue(), accumulatorY.getValue(), Items.APPLE.getDefaultInstance());

        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    @Override
    public void tick() {
        int appleSize = 16;
        accumulatorX.incrementTick();
        accumulatorY.incrementTick();

        if (accumulatorX.getValue() <= 0 || accumulatorX.getValue() >= screenWidth - appleSize) {
            accumulatorX.toggleMode();
            this.color = RandomColorGenerator.generateRandomColor();
        }
        if (accumulatorY.getValue() <= 0 || accumulatorY.getValue() >= screenHeight - appleSize) {
            accumulatorY.toggleMode();
            this.color = RandomColorGenerator.generateRandomColor();
        }
        super.tick();
    }
}

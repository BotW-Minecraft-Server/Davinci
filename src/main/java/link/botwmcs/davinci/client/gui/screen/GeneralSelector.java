package link.botwmcs.davinci.client.gui.screen;

import link.botwmcs.davinci.client.gui.component.ClickableSelector;
import link.botwmcs.davinci.client.gui.component.ColorButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GeneralSelector extends Screen {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("textures/gui/container/villager2.png");
    private final boolean showBackground;
    private final List<ClickableSelector> itemList;
    private final SelectorButton[] selectorButtons = new SelectorButton[7];
    private boolean isDragging;
    private int selectedItemIndex;
    private int scrollOffset;

    public GeneralSelector(Component component, List<ClickableSelector> itemList, boolean showBackground) {
        super(component);
        this.showBackground = showBackground;
        this.itemList = new ArrayList<>(itemList);
    }

    public static List<ClickableSelector> createDefaultItemList() {
        List<ClickableSelector> itemList = new ArrayList<>();
        itemList.add(new ClickableSelector("Item 1", List.of("This is item 1")));
        itemList.add(new ClickableSelector("Item 2", List.of("This is item 2")));
        itemList.add(new ClickableSelector("Item 3", List.of("This is item 3")));
        itemList.add(new ClickableSelector("Item 4", List.of("This is item 4")));
        itemList.add(new ClickableSelector("Item 5", List.of("This is item 5")));
        itemList.add(new ClickableSelector("Item 6", List.of("This is item 6")));
        itemList.add(new ClickableSelector("Item 7", List.of("This is item 7")));
        itemList.add(new ClickableSelector("Item 8", List.of("This is item 8")));
        itemList.add(new ClickableSelector("Item 9", List.of("This is item 9")));
        itemList.add(new ClickableSelector("Item 10", List.of("This is item 10")));
        itemList.add(new ClickableSelector("Item 11", List.of("This is item 11")));
        itemList.add(new ClickableSelector("Item 12", List.of("This is item 12")));
        itemList.add(new ClickableSelector("Item 13", List.of("This is item 13")));
        itemList.add(new ClickableSelector("Item 14", List.of("This is item 14")));
        itemList.add(new ClickableSelector("Item 15", List.of("This is item 15")));
        itemList.add(new ClickableSelector("Item 16", List.of("This is item 16")));
        itemList.add(new ClickableSelector("Item 17", List.of("This is item 17")));
        itemList.add(new ClickableSelector("Item 18", List.of("This is item 18")));
        itemList.add(new ClickableSelector("Item 19", List.of("This is item 19")));
        itemList.add(new ClickableSelector("Item 20", List.of("This is item 20")));
        return itemList;
    }

    @Override
    protected void init() {
        super.init();
        int centeredX = this.width / 2 - 108 / 2;
        int centeredY = this.height / 6;
        int xOffset = centeredX + 100;
        int buttonStartX = xOffset + 5;
        int buttonStartY = centeredY + 16 + 2;

        for (int i = 0; i < 7; ++i) {
            this.selectorButtons[i] = this.addRenderableWidget(new SelectorButton(buttonStartX, buttonStartY, i, 0xFFFFFF, (button) -> {
                if (button instanceof SelectorButton) {
                    this.selectedItemIndex = ((SelectorButton) button).getIndex() + this.scrollOffset;
                    this.onItemSelected(this.selectedItemIndex);
                }
            }));
            buttonStartY += 20;
        }
    }

    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        if (this.showBackground) {
            this.renderDirtBackground(guiGraphics);
        } else {
            this.renderTransparentBackground(guiGraphics);
        }
        int centeredX = this.width / 2 - 108 / 2;
        int centeredY = this.height / 6;
        int xOffset = centeredX + 100;
        guiGraphics.blit(BACKGROUND_TEXTURE, xOffset + 10, centeredY, 0, 180, 0.0F, 96, 165, 512, 256);
        guiGraphics.blit(BACKGROUND_TEXTURE, xOffset, centeredY, 0, 0.0F, 0.0F, 101, 165, 512, 256);
    }

    public void renderTransparentBackground(GuiGraphics guiGraphics) {
        guiGraphics.fillGradient(0, 0, this.width, this.height, -5, 1678774288, -2112876528);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        List<ClickableSelector> sortedList = new ArrayList<>(this.itemList);
        // Customize sorting if needed
        int combinedListSize = sortedList.size();
        if (!sortedList.isEmpty()) {
            int bgGuiStartX = this.width / 2 - 108 / 2 + 100;
            int bgGuiStartY = this.height / 6;
            int k = bgGuiStartY + 16 + 6;
            int bgGuiCentralX = bgGuiStartX + 108 / 2 - 5;
            int m = 0;
            this.renderScroller(guiGraphics, bgGuiStartX, bgGuiStartY);
            for (ClickableSelector item : sortedList) {
                if (this.canScroll(combinedListSize) && (m < this.scrollOffset || m >= 7 + this.scrollOffset)) {
                    m++;
                } else {
                    Component component = Component.literal(item.name()); // Convert item to a Component
                    guiGraphics.pose().pushPose();
                    guiGraphics.pose().translate(0.0F, 0.0F, 100.0F);
                    int n = k + 2;
                    guiGraphics.drawCenteredString(this.font, component, bgGuiCentralX, n, 0xFFFFFF);
                    guiGraphics.pose().popPose();
                    k += 20;
                    m++;
                }
            }
            for (SelectorButton selectorButton : this.selectorButtons) {
                if (selectorButton.isHoveredOrFocused()) {
                    selectorButton.renderToolTip(guiGraphics, mouseX, mouseY, selectorButton.index + this.scrollOffset);
                }
                selectorButton.visible = selectorButton.index < sortedList.size();
            }
        }
    }

    private void renderScroller(GuiGraphics guiGraphics, int posX, int posY) {
        int i = itemList.size() + 1 - 7;
        if (i > 1) {
            int j = 139 - (27 + (i - 1) * 139 / i);
            int k = 1 + j / i + 139 / i;
            int m = Math.min(113, this.scrollOffset * k);
            if (this.scrollOffset == i - 1) {
                m = 113;
            }
            guiGraphics.blit(BACKGROUND_TEXTURE, posX + 94, posY + 18 + m, 0, 0.0F, 199.0F, 6, 27, 512, 256);
        } else {
            guiGraphics.blit(BACKGROUND_TEXTURE, posX + 94, posY + 18, 0, 6.0F, 199.0F, 6, 27, 512, 256);
        }
    }

    private boolean canScroll(int numItems) {
        return numItems > 7;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int i = this.itemList.size();
        if (this.canScroll(i)) {
            int j = i - 7;
            this.scrollOffset = Mth.clamp((int)((double)this.scrollOffset - delta), 0, j);
        }
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        int i = this.itemList.size();
        if (this.isDragging) {
            int j = this.height / 6 + 18;
            int k = j + 139;
            int l = i - 7;
            float f = ((float)mouseY - (float)j - 13.5F) / ((float)(k - j) - 27.0F);
            f = f * (float)l + 0.5F;
            this.scrollOffset = Mth.clamp((int)f, 0, l);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int buttonIndex) {
        this.isDragging = false;
        int i = this.width / 2 - 108 / 2 + 100;
        int j = this.height / 6;
        if (this.canScroll(this.itemList.size()) && mouseX > (double)(i + 94) && mouseX < (double)(i + 94 + 6) && mouseY > (double)(j + 18) && mouseY <= (double)(j + 18 + 139 + 1)) {
            this.isDragging = true;
        }

        return super.mouseClicked(mouseX, mouseY, buttonIndex);
    }

    protected void onItemSelected(int index) {
        // Handle item selection logic here
    }

    private class SelectorButton extends ColorButton {
        final int index;
        final int color;

        public SelectorButton(int x, int y, int index, int color, OnPress onPress) {
            super(x, y, 88, 20, Component.empty(), color, onPress);
            this.index = index;
            this.color = color;
            this.visible = false;
        }

        public int getIndex() {
            return this.index;
        }

        public void renderToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY, int index) {
            if (this.isHovered) {
                List<Component> componentList = new ArrayList<>();
                if (index < itemList.size()) {
                    ClickableSelector item = itemList.get(index);
                    for (String tooltipLine : item.tooltip()) {
                        componentList.add(Component.literal(tooltipLine));
                    }
                    guiGraphics.renderTooltip(font, componentList, Optional.empty(), mouseX, mouseY);
                }
            }
        }
    }
}

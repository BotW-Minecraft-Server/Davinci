package link.botwmcs.davinci.client.gui.component;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.layouts.SpacerElement;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.telemetry.TelemetryEventWidget;
import net.minecraft.client.telemetry.TelemetryEventType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.AbstractDocument;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.DoubleConsumer;

@Environment(EnvType.CLIENT)
public class ContentScrollWidget extends AbstractScrollWidget {
    private ContentBuilder.Content content;
    private final Font font;
    @Nullable
    private DoubleConsumer onScrolledListener;


    public ContentScrollWidget(int x, int y, int width, int height, ContentBuilder.Content content) {
        super(x, y, width, height, Component.empty());
        this.font = Minecraft.getInstance().font;
        this.content = content;
    }

    public ContentScrollWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty());
        this.font = Minecraft.getInstance().font;
        this.content = new ContentBuilder(this.containerWidth()).build();
    }

    @Override
    public void setScrollAmount(double scrollAmount) {
        super.setScrollAmount(scrollAmount);
        if (this.onScrolledListener != null) {
            this.onScrolledListener.accept(this.scrollAmount());
        }
    }

    @Override
    protected int getInnerHeight() {
        return this.content.container().getHeight();
    }

    @Override
    protected double scrollRate() {
        return this.font.lineHeight;
    }

    @Override
    protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int i = this.getY() + this.innerPadding();
        int j = this.getX() + this.innerPadding();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate((double)j, (double)i, 0.0);
        this.content.container().visitWidgets(abstractWidget -> abstractWidget.render(guiGraphics, mouseX, mouseY, partialTick));
        guiGraphics.pose().popPose();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.TITLE, this.content.narration());
    }

    public int containerWidth() {
        return this.width - this.totalInnerPadding();
    }

    public void setOnScrolledListener(@Nullable DoubleConsumer onScrolledListener) {
        this.onScrolledListener = onScrolledListener;
    }

    public void buildOriginalContent(ContentBuilder content) {
        this.content = content.build();
    }

    public void buildContent(List<String> contentStrings) {
        ContentBuilder contentBuilder = new ContentBuilder(this.containerWidth());
        for (String contentString : contentStrings) {
            Component message = Component.literal(contentString);
            contentBuilder.addLine(this.font, message);
        }

        this.content = contentBuilder.build();
    }

    @Environment(value=EnvType.CLIENT)
    public static class ContentBuilder {
        private final int width;
        private final GridLayout grid;
        private final GridLayout.RowHelper helper;
        private final LayoutSettings alignHeader;
        private final MutableComponent narration = Component.empty();

        public ContentBuilder(int width) {
            this.width = width;
            this.grid = new GridLayout();
            this.grid.defaultCellSetting().alignHorizontallyLeft();
            this.helper = this.grid.createRowHelper(1);
            this.helper.addChild(SpacerElement.width(width));
            this.alignHeader = this.helper.newCellSettings().alignHorizontallyCenter().paddingHorizontal(32);
        }

        public void addLine(Font font, Component message) {
            this.addLine(font, message, 0);
        }

        public void addLine(Font font, Component message, int padding) {
            this.helper.addChild(new MultiLineTextWidget(message, font).setMaxWidth(this.width), this.helper.newCellSettings().paddingBottom(padding));
            this.narration.append(message).append("\n");
        }

        public void addHeader(Font font, Component message) {
            this.helper.addChild(new MultiLineTextWidget(message, font).setMaxWidth(this.width - 64).setCentered(true), this.alignHeader);
            this.narration.append(message).append("\n");
        }

        public void addSpacer(int height) {
            this.helper.addChild(SpacerElement.height(height));
        }

        @Environment(value=EnvType.CLIENT)
        public record Content(GridLayout container, Component narration) {
        }

        public Content build() {
            this.grid.arrangeElements();
            return new Content(this.grid, this.narration);
        }
    }
}

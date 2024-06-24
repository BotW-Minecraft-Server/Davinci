package link.botwmcs.davinci.client.gui.screen.ltsx;

import link.botwmcs.davinci.client.gui.component.ColorButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.LockIconButton;
import net.minecraft.client.gui.layouts.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.client.gui.screens.telemetry.TelemetryInfoScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ServerboundLockDifficultyPacket;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.Difficulty;

import java.util.function.Supplier;

public class LtsxOptionsScreen extends Screen {
    private static final Component SKIN_CUSTOMIZATION = Component.translatable("options.skinCustomisation");
    private static final Component SOUNDS = Component.translatable("options.sounds");
    private static final Component VIDEO = Component.translatable("options.video");
    private static final Component CONTROLS = Component.translatable("options.controls");
    private static final Component LANGUAGE = Component.translatable("options.language");
    private static final Component CHAT = Component.translatable("options.chat.title");
    private static final Component RESOURCEPACK = Component.translatable("options.resourcepack");
    private static final Component ACCESSIBILITY = Component.translatable("options.accessibility.title");
    private static final Component TELEMETRY = Component.translatable("options.telemetry");
    private static final Component CREDITS_AND_ATTRIBUTION = Component.translatable("options.credits_and_attribution");
    private static final int DEFAULT_COLOR = 0xFFFFFFFF;
    private final Screen lastScreen;
    private final Options options;
    private CycleButton<Difficulty> difficultyButton;
    private LockIconButton lockButton;

    public LtsxOptionsScreen(Screen lastScreen, Options options) {
        super(Component.literal("LTSX Options Screen"));
        this.lastScreen = lastScreen;
        this.options = options;
    }

    @Override
    protected void init() {
        GridLayout gridLayout = new GridLayout();
        gridLayout.defaultCellSetting().paddingHorizontal(5).paddingBottom(4).alignHorizontallyCenter();
        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(2);
        rowHelper.addChild(this.options.fov().createButton(this.minecraft.options, 0, 0, 150));
        rowHelper.addChild(this.openScreenButton(Component.translatable("menu.titlescreen.account"), 0xFF008FE1, () -> new LtsxOptionsScreen(this, this.options)));
        rowHelper.addChild(SpacerElement.height(26), 2);
//        rowHelper.addChild(this.openScreenButton(SKIN_CUSTOMIZATION, () -> new SkinCustomizationScreen(this, this.options)));
//        rowHelper.addChild(this.openScreenButton(SOUNDS, () -> new SoundOptionsScreen(this, this.options)));
        rowHelper.addChild(this.openScreenButton(Component.translatable("menu.options.interface"), DEFAULT_COLOR, () -> new InterfaceOptionsScreen(this, this.options)));
        rowHelper.addChild(this.openScreenButton(VIDEO, () -> new VideoSettingsScreen(this, this.options)));
        rowHelper.addChild(this.openScreenButton(CONTROLS, () -> new ControlsScreen(this, this.options)));
        rowHelper.addChild(this.openScreenButton(LANGUAGE, () -> new LanguageSelectScreen((Screen)this, this.options, this.minecraft.getLanguageManager())));
//        rowHelper.addChild(this.openScreenButton(CHAT, () -> new ChatOptionsScreen(this, this.options)));
        rowHelper.addChild(this.openScreenButton(RESOURCEPACK, () -> new PackSelectionScreen(this.minecraft.getResourcePackRepository(), this::applyPacks, this.minecraft.getResourcePackDirectory(), Component.translatable("resourcePack.title"))));
        rowHelper.addChild(this.openScreenButton(TELEMETRY, () -> new LtsxTelemetryScreen(this, this.options)));
        rowHelper.addChild(this.openScreenButton(CREDITS_AND_ATTRIBUTION, () -> new CopyrightScreen(this)));
        rowHelper.addChild(Button.builder(CommonComponents.GUI_DONE, button -> this.minecraft.setScreen(this.lastScreen)).width(200).build(), 2, rowHelper.newCellSettings().paddingTop(6));
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
    public void removed() {
        this.options.save();
    }

    private Button openScreenButton(Component text, int color, Supplier<Screen> screenSupplier) {
        return new ColorButton(0, 0, 150, 20, text, color, button -> this.minecraft.setScreen(screenSupplier.get()));
        // return Button.builder(text, button -> this.minecraft.setScreen((Screen)screenSupplier.get())).build();
    }

    private Button openScreenButton(Component text, Supplier<Screen> screenSupplier) {
        return this.openScreenButton(text, DEFAULT_COLOR, screenSupplier);
    }

    private void applyPacks(PackRepository packs) {
        this.options.updateResourcePacks(packs);
        this.minecraft.setScreen(this);
    }

    private LayoutElement createOnlineButton() {
        if (this.minecraft.level != null && this.minecraft.hasSingleplayerServer()) {
            this.difficultyButton = OptionsScreen.createDifficultyButton(0, 0, "options.difficulty", this.minecraft);
            if (!this.minecraft.level.getLevelData().isHardcore()) {
                this.lockButton = new LockIconButton(0, 0, button -> this.minecraft.setScreen(new ConfirmScreen(this::lockCallback, Component.translatable("difficulty.lock.title"), Component.translatable("difficulty.lock.question", this.minecraft.level.getLevelData().getDifficulty().getDisplayName()))));
                this.difficultyButton.setWidth(this.difficultyButton.getWidth() - this.lockButton.getWidth());
                this.lockButton.setLocked(this.minecraft.level.getLevelData().isDifficultyLocked());
                this.lockButton.active = !this.lockButton.isLocked();
                this.difficultyButton.active = !this.lockButton.isLocked();
                LinearLayout linearLayout = new LinearLayout(150, 0, LinearLayout.Orientation.HORIZONTAL);
                linearLayout.addChild(this.difficultyButton);
                linearLayout.addChild(this.lockButton);
                return linearLayout;
            }
            this.difficultyButton.active = false;
            return this.difficultyButton;
        }
        return Button.builder(Component.translatable("options.online"), button -> this.minecraft.setScreen(OnlineOptionsScreen.createOnlineOptionsScreen(this.minecraft, this, this.options))).bounds(this.width / 2 + 5, this.height / 6 - 12 + 24, 150, 20).build();
    }

    private void lockCallback(boolean value) {
        this.minecraft.setScreen(this);
        if (value && this.minecraft.level != null) {
            this.minecraft.getConnection().send(new ServerboundLockDifficultyPacket(true));
            this.lockButton.setLocked(true);
            this.lockButton.active = false;
            this.difficultyButton.active = false;
        }
    }
}

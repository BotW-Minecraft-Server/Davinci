package link.botwmcs.davinci.client.gui.screen.ltsx;

import com.mojang.authlib.minecraft.BanDetails;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
import link.botwmcs.davinci.client.gui.component.BotwmcsLogoRenderer;
import link.botwmcs.davinci.client.gui.component.ColorButton;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

public class LtsxTitleScreen extends Screen {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Component COPYRIGHT_TEXT = Component.literal("Copyright Mojang AB. MIT License 2022 RealmsHost.");
    public static final CubeMap CUBE_MAP = new CubeMap(new ResourceLocation("textures/gui/title/background/panorama"));
    private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
    @Nullable
    private SplashRenderer splash;
    private Button resetDemoButton;
    @Nullable
    private RealmsNotificationsScreen realmsNotificationsScreen;
    private final PanoramaRenderer panorama;
    private final boolean fading;
    private long fadeInStart;
    private final BotwmcsLogoRenderer logoRenderer;

    public LtsxTitleScreen() {
        this(false);
    }

    public LtsxTitleScreen(boolean fading) {
        this(fading, (BotwmcsLogoRenderer)null);
    }

    public LtsxTitleScreen(boolean fading, @Nullable BotwmcsLogoRenderer logoRenderer) {
        super(Component.literal("LTSX Title Screen"));
        this.panorama = new PanoramaRenderer(CUBE_MAP);
        this.fading = fading;
        this.logoRenderer = (BotwmcsLogoRenderer) Objects.requireNonNullElseGet(logoRenderer, () -> {
            return new BotwmcsLogoRenderer(false);
        });
    }

    private boolean realmsNotificationsEnabled() {
        return this.realmsNotificationsScreen != null;
    }

    public boolean isPauseScreen() {
        return false;
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    protected void init() {
        if (this.splash == null) {
            this.splash = this.minecraft.getSplashManager().getSplash();
        }

        int i = this.font.width(COPYRIGHT_TEXT);
        int j = this.width - i - 2;
        int l = this.height / 4 + 48;
//        if (this.minecraft.isDemo()) {
//            this.createDemoMenuOptions(l, 24);
//        } else {
//            this.createNormalMenuOptions(l, 24);
//        }
        this.createNormalMenuOptions(l, 24);

        final AbstractWidget languageButton = addRenderableWidget(new ImageButton(this.width / 2 - 124, l + 72 + 12, 20, 20, 0, 106, 20, Button.WIDGETS_LOCATION, 256, 256, (button) -> {
            this.minecraft.setScreen(new LanguageSelectScreen(this, this.minecraft.options, this.minecraft.getLanguageManager()));
        }, Component.translatable("narrator.button.language")));
        languageButton.setTooltip(Tooltip.create(Component.translatable("narrator.button.language")));

        final AbstractWidget accessibilityButton = addRenderableWidget(new ImageButton(this.width / 2 + 104, l + 72 + 12, 20, 20, 0, 0, 20, Button.ACCESSIBILITY_TEXTURE, 32, 64, (button) -> {
            this.minecraft.setScreen(new AccessibilityOptionsScreen(this, this.minecraft.options));
        }, Component.translatable("narrator.button.accessibility")));
        accessibilityButton.setTooltip(Tooltip.create(Component.translatable("narrator.button.accessibility")));

        final AbstractWidget optionsButton = addRenderableWidget(Button.builder(Component.translatable("menu.options"), (button) -> {
            this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
        }).bounds(this.width / 2 - 100, l + 72 + 12, 98, 20).build());

        final AbstractWidget quitButton = addRenderableWidget(new ColorButton(this.width / 2 + 2, l + 72 + 12, 98, 20, Component.translatable("menu.quit"), 0xFFFF0000, (button) -> {
            this.minecraft.stop();
        }));

        this.addRenderableWidget(new PlainTextButton(2, this.height - 20, i, 10, COPYRIGHT_TEXT, (button) -> {
//            this.minecraft.setScreen(new CreditsAndAttributionScreen(this));
            this.minecraft.setScreen(new CopyrightScreen(this));
        }, this.font));
        this.minecraft.setConnectedToRealms(false);
    }

    private void createNormalMenuOptions(int y, int rowHeight) {
        final AbstractWidget serverButton = addRenderableWidget(new ColorButton(this.width / 2 - 100, y, 98, 20, Component.translatable("menu.titlescreen.joinServer"), 0xFF008FE1, (button) -> {
//            this.minecraft.setScreen(new SelectWorldScreen(this));
            // TODO: join LTSX server
//            this.minecraft.setScreen(new LtsxServerInfoScreen(Component.literal("Info Screen"), true));
            this.minecraft.setScreen(new JoinMultiplayerScreen(this));
        }));
        serverButton.setTooltip(Tooltip.create(Component.translatable("menu.titlescreen.joinServer.tooltip")));

        final AbstractWidget websiteButton = addRenderableWidget(new ColorButton(this.width / 2 + 2, y, 98, 20, Component.translatable("menu.titlescreen.openWebsite"), 0xFF008FE1, (button) -> {
            Util.getPlatform().openUri("https://persists.link");
        }));
        websiteButton.setTooltip(Tooltip.create(Component.translatable("menu.titlescreen.openWebsite.tooltip")));

        final AbstractWidget socialButton = addRenderableWidget(new ColorButton(this.width / 2 - 100, y + rowHeight * 1, 98, 20, Component.translatable("menu.titlescreen.social"), 0xFFFFFFFF, (button) -> {
//            Util.getPlatform().openUri("https://persists.link/discord");
            // TODO: Open social menu
        }));
        socialButton.setTooltip(Tooltip.create(Component.translatable("menu.titlescreen.social.tooltip")));

        final AbstractWidget accountButton = addRenderableWidget(new ColorButton(this.width / 2 + 2, y + rowHeight * 1, 98, 20, Component.translatable("menu.titlescreen.account"), 0xFFFFFFFF, (button) -> {
//            Util.getPlatform().openUri("https://persists.link/account");
            // TODO: Open account menu
        }));
        accountButton.setTooltip(Tooltip.create(Component.translatable("menu.titlescreen.account.tooltip")));

        final AbstractWidget singleplayerButton = addRenderableWidget(new ColorButton(this.width / 2 - 100, y + rowHeight * 2, 200, 20, Component.translatable("menu.singleplayer"), 0xFFFFFFFF, (button) -> {
            this.minecraft.setScreen(new SelectWorldScreen(this));
        }));
        singleplayerButton.setTooltip(Tooltip.create(Component.translatable("menu.titlescreen.singleplayer.tooltip")));

//        this.addRenderableWidget(Button.builder(Component.translatable("menu.singleplayer"), (button) -> {
//            this.minecraft.setScreen(new SelectWorldScreen(this));
//        }).bounds(this.width / 2 - 100, y, 200, 20).build());
//        Component component = this.getMultiplayerDisabledReason();
//        boolean bl = component == null;
//        Tooltip tooltip = component != null ? Tooltip.create(component) : null;
//        ((Button)this.addRenderableWidget(Button.builder(Component.translatable("menu.multiplayer"), (button) -> {
//            Screen screen = this.minecraft.options.skipMultiplayerWarning ? new JoinMultiplayerScreen(this) : new SafetyScreen(this);
//            this.minecraft.setScreen((Screen)screen);
//        }).bounds(this.width / 2 - 100, y + rowHeight * 1, 200, 20).tooltip(tooltip).build())).active = bl;
//        ((Button)this.addRenderableWidget(Button.builder(Component.translatable("menu.online"), (button) -> {
//            this.realmsButtonClicked();
//        }).bounds(this.width / 2 - 100, y + rowHeight * 2, 200, 20).tooltip(tooltip).build())).active = bl;
    }

    @Nullable
    private Component getMultiplayerDisabledReason() {
        if (this.minecraft.allowsMultiplayer()) {
            return null;
        } else {
            BanDetails banDetails = this.minecraft.multiplayerBan();
            if (banDetails != null) {
                return banDetails.expires() != null ? Component.translatable("title.multiplayer.disabled.banned.temporary") : Component.translatable("title.multiplayer.disabled.banned.permanent");
            } else {
                return Component.translatable("title.multiplayer.disabled");
            }
        }
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.fadeInStart == 0L && this.fading) {
            this.fadeInStart = Util.getMillis();
        }

        float f = this.fading ? (float)(Util.getMillis() - this.fadeInStart) / 1000.0F : 1.0F;
        this.panorama.render(partialTick, Mth.clamp(f, 0.0F, 1.0F));
        RenderSystem.enableBlend();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.fading ? (float)Mth.ceil(Mth.clamp(f, 0.0F, 1.0F)) : 1.0F);
        guiGraphics.blit(PANORAMA_OVERLAY, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        float g = this.fading ? Mth.clamp(f - 1.0F, 0.0F, 1.0F) : 1.0F;
        this.logoRenderer.renderLogo(guiGraphics, this.width, g);
        int i = Mth.ceil(g * 255.0F) << 24;
        if ((i & -67108864) != 0) {
            if (this.splash != null) {
                this.splash.render(guiGraphics, this.width, this.font, i);
            }

            String string = "Minecraft " + SharedConstants.getCurrentVersion().getName();
            if (this.minecraft.isDemo()) {
                string = string + " Demo";
            } else {
                string = string + ("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : "/" + this.minecraft.getVersionType());
            }

            if (Minecraft.checkModStatus().shouldReportAsModified()) {
                string = string + I18n.get("menu.modded", new Object[0]);
            }

            guiGraphics.drawString(this.font, string, 2, this.height - 10, 16777215 | i);
            Iterator var9 = this.children().iterator();

            while(var9.hasNext()) {
                GuiEventListener guiEventListener = (GuiEventListener)var9.next();
                if (guiEventListener instanceof AbstractWidget) {
                    ((AbstractWidget)guiEventListener).setAlpha(g);
                }
            }

            super.render(guiGraphics, mouseX, mouseY, partialTick);
            if (this.realmsNotificationsEnabled() && g >= 1.0F) {
                RenderSystem.enableDepthTest();
                this.realmsNotificationsScreen.render(guiGraphics, mouseX, mouseY, partialTick);
            }

        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        } else {
            return this.realmsNotificationsEnabled() && this.realmsNotificationsScreen.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void removed() {
        if (this.realmsNotificationsScreen != null) {
            this.realmsNotificationsScreen.removed();
        }

    }

    public void added() {
        super.added();
        if (this.realmsNotificationsScreen != null) {
            this.realmsNotificationsScreen.added();
        }

    }

    private void confirmDemo(boolean confirmed) {
        if (confirmed) {
            try {
                LevelStorageSource.LevelStorageAccess levelStorageAccess = this.minecraft.getLevelSource().createAccess("Demo_World");

                try {
                    levelStorageAccess.deleteLevel();
                } catch (Throwable var6) {
                    if (levelStorageAccess != null) {
                        try {
                            levelStorageAccess.close();
                        } catch (Throwable var5) {
                            var6.addSuppressed(var5);
                        }
                    }

                    throw var6;
                }

                if (levelStorageAccess != null) {
                    levelStorageAccess.close();
                }
            } catch (IOException var7) {
                IOException iOException = var7;
                SystemToast.onWorldDeleteFailure(this.minecraft, "Demo_World");
                LOGGER.warn("Failed to delete demo world", iOException);
            }
        }

        this.minecraft.setScreen(this);
    }
}

package link.botwmcs.davinci.mixin;

import link.botwmcs.davinci.client.gui.screen.ltsx.LtsxTelemetryScreen;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.telemetry.TelemetryInfoScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TelemetryInfoScreen.class)
public class TelemetryInfoScreenMixin extends Screen {
    protected TelemetryInfoScreenMixin(Component title, Options options, Screen lastScreen) {
        super(title);
        this.options = options;
        this.lastScreen = lastScreen;
    }

    @Mutable
    @Shadow
    @Final
    private final Options options;

    @Mutable
    @Shadow @Final
    private final Screen lastScreen;

    @Inject(method = "init", at = @At("HEAD"))
    public void init(CallbackInfo ci) {
        this.minecraft.setScreen(new LtsxTelemetryScreen(lastScreen, options));
    }
}

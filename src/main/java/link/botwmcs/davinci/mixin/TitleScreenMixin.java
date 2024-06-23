package link.botwmcs.davinci.mixin;

import link.botwmcs.davinci.client.gui.screen.ltsx.LtsxTitleScreen;
import link.botwmcs.davinci.config.CommonConfig;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Component title) {
        super(title);
    }
    /**
     * @author Sam_Chai
     * @reason Redirect the init method to LtsxTitleScreen
     */
    @Inject(method = "init", at = @At("HEAD"))
    public void init(CallbackInfo ci) {
        if (CommonConfig.CONFIG.enableLtsxComponents.get()) {
            this.minecraft.setScreen(new LtsxTitleScreen());
        }
    }
}

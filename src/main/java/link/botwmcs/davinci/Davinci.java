package link.botwmcs.davinci;

import com.mojang.logging.LogUtils;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import link.botwmcs.davinci.command.AnnounceCommand;
import link.botwmcs.davinci.command.DavinciCommand;
import link.botwmcs.davinci.config.CommonConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

public class Davinci implements ModInitializer {
    public static final String MODID = "davinci";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        loadCommands();
        loadConfig();
    }

    private void loadCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            AnnounceCommand.register(dispatcher);
            DavinciCommand.register(dispatcher);
        });
    }

    public void loadConfig() {
        ForgeConfigRegistry.INSTANCE.register(Davinci.MODID, ModConfig.Type.COMMON, CommonConfig.CONFIG_SPEC);
    }
}

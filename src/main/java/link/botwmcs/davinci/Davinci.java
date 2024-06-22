package link.botwmcs.davinci;

import com.mojang.logging.LogUtils;
import link.botwmcs.davinci.command.AnnounceCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;

public class Davinci implements ModInitializer {
    public static final String MODID = "davinci";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        loadCommands();
    }

    private void loadCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            AnnounceCommand.register(dispatcher);
        });
    }
}

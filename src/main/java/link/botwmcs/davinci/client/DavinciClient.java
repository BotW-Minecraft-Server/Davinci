package link.botwmcs.davinci.client;

import link.botwmcs.davinci.client.gui.component.DynamicIsland;
import link.botwmcs.davinci.client.gui.component.ModernBossBarMessage;
import link.botwmcs.davinci.client.gui.component.TrainBarMessage;
import link.botwmcs.davinci.network.S2CHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class DavinciClient implements ClientModInitializer {
    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        loadS2CNetworkPackets();
        loadHud();
    }

    private void loadS2CNetworkPackets() {
        S2CHandler.register();
    }

    private void loadHud() {
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            ModernBossBarMessage.getInstance().tick();
            ModernBossBarMessage.getInstance().render(matrixStack, tickDelta);
            TrainBarMessage.getInstance().tick();
            TrainBarMessage.getInstance().render(matrixStack, tickDelta);
            DynamicIsland.getInstance().tick();
            DynamicIsland.getInstance().render(matrixStack, tickDelta);
        });
    }
}

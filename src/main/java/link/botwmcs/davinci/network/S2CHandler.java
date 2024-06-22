package link.botwmcs.davinci.network;

import link.botwmcs.davinci.client.gui.component.ModernBossBarMessage;
import link.botwmcs.davinci.client.gui.component.TrainBarMessage;
import link.botwmcs.davinci.network.s2c.SendModernBossBarMessage;
import link.botwmcs.davinci.network.s2c.SendSystemToastMessage;
import link.botwmcs.davinci.network.s2c.SendTrainBarMessage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class S2CHandler {
    @Environment(EnvType.CLIENT)
    public static void register() {
        ClientPlayConnectionEvents.INIT.register((handler, client) -> {
            ClientPlayNetworking.registerReceiver(SendSystemToastMessage.TYPE, S2CHandler::sendSystemToastMessage);
            ClientPlayNetworking.registerReceiver(SendModernBossBarMessage.TYPE, S2CHandler::sendModernBossBarMessage);
            ClientPlayNetworking.registerReceiver(SendTrainBarMessage.TYPE, S2CHandler::sendTrainBarMessage);
        });
    }

    @Environment(EnvType.CLIENT)
    private static void sendSystemToastMessage(SendSystemToastMessage packet, Player player, PacketSender sender) {
        Minecraft.getInstance().execute(() -> {
            SystemToast toast = new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT, Component.nullToEmpty(packet.title()), Component.nullToEmpty(packet.title()));
            Minecraft.getInstance().getToasts().addToast(toast);
        });
    }
    @Environment(EnvType.CLIENT)
    private static void sendModernBossBarMessage(SendModernBossBarMessage packet, Player player, PacketSender sender) {
        Minecraft.getInstance().execute(() -> {
            ModernBossBarMessage.getInstance().onShowHUDMessage(Component.literal(packet.component()), packet.stayTime() * 20 + 100);
        });
    }
    @Environment(EnvType.CLIENT)
    private static void sendTrainBarMessage(SendTrainBarMessage packet, Player player, PacketSender sender) {
        Minecraft.getInstance().execute(() -> {
            TrainBarMessage.getInstance().onShowHUDMessage(Component.literal(packet.component()), packet.stayTime() * 20 + 100);
        });
    }

}

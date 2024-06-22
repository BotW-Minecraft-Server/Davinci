package link.botwmcs.davinci.util;

import link.botwmcs.davinci.client.gui.component.ModernBossBarMessage;
import link.botwmcs.davinci.client.gui.component.TrainBarMessage;
import link.botwmcs.davinci.network.s2c.SendModernBossBarMessage;
import link.botwmcs.davinci.network.s2c.SendSystemToastMessage;
import link.botwmcs.davinci.network.s2c.SendTrainBarMessage;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SendComponentUtil {
    public static void cleanAllComponents(ServerPlayer player) {
        if (ModernBossBarMessage.isEnabled()) {
            ServerPlayNetworking.send(player, new SendModernBossBarMessage("", 0));
        }
        if (TrainBarMessage.isEnabled()) {
            ServerPlayNetworking.send(player, new SendTrainBarMessage("", 0));
        }
    }
    public static void sendSystemToast(ServerPlayer player, String title, String subTitle) {
        ServerPlayNetworking.send(player, new SendSystemToastMessage(title, subTitle));
    }

    public static void sendTrainBarMessage(ServerPlayer serverPlayer, String component, int stayTime) {
        ServerPlayNetworking.send(serverPlayer, new SendTrainBarMessage(component, stayTime));
    }

    public static void sendTrainBarMessageList(ServerPlayer serverPlayer, List<String> list, int stayTime) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        for (int i = 0; i < list.size(); i++) {
            String component = list.get(i);
            int delay = stayTime * i;
            scheduler.schedule(() -> sendTrainBarMessage(serverPlayer, component, stayTime + 100), delay, TimeUnit.SECONDS);
        }
        scheduler.shutdown();
    }

    public static void sendModernBossBarMessage(ServerPlayer serverPlayer, String component, int stayTime) {
        ServerPlayNetworking.send(serverPlayer, new SendModernBossBarMessage(component, stayTime));
    }

    public static void sendModernBossBarMessageList(ServerPlayer serverPlayer, List<String> list, int stayTime) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        for (int i = 0; i < list.size(); i++) {
            String component = list.get(i);
            int delay = stayTime * i;
            scheduler.schedule(() -> sendModernBossBarMessage(serverPlayer, component, stayTime + 100), delay, TimeUnit.SECONDS);
        }
        scheduler.shutdown();
    }

}

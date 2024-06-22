package link.botwmcs.davinci.network.s2c;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record SendModernBossBarMessage(String component, int stayTime) implements FabricPacket {

    public static final PacketType<SendModernBossBarMessage> TYPE =
            PacketType.create(new ResourceLocation("s2c/send_bossbar_hud_component"), SendModernBossBarMessage::read);

    private static SendModernBossBarMessage read(FriendlyByteBuf buf) {
        return new SendModernBossBarMessage(buf.readUtf(), buf.readInt());
    }
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(component);
        buf.writeInt(stayTime);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}

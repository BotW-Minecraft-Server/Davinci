package link.botwmcs.davinci.network.s2c;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;

public record SendIslandMessage(String component, int stayTime) implements FabricPacket {
    public static final PacketType<SendIslandMessage> TYPE = PacketType.create(new ResourceLocation("s2c/send_island_message"), SendIslandMessage::read);
    private static SendIslandMessage read(FriendlyByteBuf buf) {
        return new SendIslandMessage(buf.readUtf(), buf.readInt());
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

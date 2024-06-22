package link.botwmcs.davinci.network.s2c;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record SendTrainBarMessage(String component, int stayTime) implements FabricPacket {

    public static final PacketType<SendTrainBarMessage> TYPE =
            PacketType.create(new ResourceLocation("s2c/send_hud_component"), SendTrainBarMessage::read);

    private static SendTrainBarMessage read(FriendlyByteBuf buf) {
        return new SendTrainBarMessage(buf.readUtf(), buf.readInt());
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

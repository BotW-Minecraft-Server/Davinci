package link.botwmcs.davinci.network.s2c;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record SendSystemToastMessage(String title, String subTitle) implements FabricPacket {

    public static final PacketType<SendSystemToastMessage> TYPE =
            PacketType.create(new ResourceLocation("s2c/send_toast"), SendSystemToastMessage::read);
    private static SendSystemToastMessage read(FriendlyByteBuf buf) {
        return new SendSystemToastMessage(buf.readUtf(), buf.readUtf());
    }
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(title);
        buf.writeUtf(subTitle);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}

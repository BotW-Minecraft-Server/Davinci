package link.botwmcs.davinci.network.s2c;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ShowDemo(String component) implements FabricPacket {
    public static final PacketType<ShowDemo> TYPE = PacketType.create(new ResourceLocation("s2c/show_demo"), ShowDemo::read);
    private static ShowDemo read(FriendlyByteBuf buf) {
        return new ShowDemo(buf.readUtf());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(component);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}

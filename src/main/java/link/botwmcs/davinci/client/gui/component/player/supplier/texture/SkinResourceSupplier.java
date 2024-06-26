package link.botwmcs.davinci.client.gui.component.player.supplier.texture;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkinResourceSupplier {
    public static final ResourceLocation DEFAULT_SKIN_LOCATION = new ResourceLocation("textures/entity/wide/steve.png");
    protected boolean sourceIsPlayerName;
    protected volatile SkinResourceSupplier.SkinMetadata playerNameSkinMeta;
    protected volatile boolean startedDownloadingMetadata = false;

    public SkinResourceSupplier(@NotNull String source, boolean sourceIsPlayerName) {
        this.sourceIsPlayerName = sourceIsPlayerName;
    }

    public ResourceLocation getSkinLocation() {
        ResourceLocation location =
        return (location != null) ? location : DEFAULT_SKIN_LOCATION;
    }








    public record SkinMetadata(@NotNull String playerName, @Nullable String resourceSource, boolean slim) {

}

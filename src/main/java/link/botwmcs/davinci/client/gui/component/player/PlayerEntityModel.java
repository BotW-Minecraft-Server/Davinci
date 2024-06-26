package link.botwmcs.davinci.client.gui.component.player;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import link.botwmcs.davinci.client.gui.component.player.supplier.PlayerModelProperties;
import link.botwmcs.davinci.client.gui.component.player.supplier.PlayerModelRenderer;
import link.botwmcs.davinci.client.gui.component.player.supplier.texture.CapeResourceSupplier;
import link.botwmcs.davinci.client.gui.component.player.supplier.texture.SkinResourceSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

public class PlayerEntityModel {

    @NotNull
    public volatile String playerName = "Steve";
    public boolean showPlayerName = true;
    public boolean hasParrotOnShoulder = false;
    public boolean parrotOnLeftShoulder = false;
    public boolean crouching = false;
    public boolean isBaby = false;
    public String scale = "30";
    public boolean headFollowsMouse = true;
    public boolean bodyFollowsMouse = true;
    public volatile boolean slim = true;
    public volatile boolean autoSkin = false;
    public volatile boolean autoCape = false;
    public volatile boolean copyClientPlayer = false;
    public String bodyXRot;
    public String bodyYRot;
    public String headXRot;
    public String headYRot;
    public String headZRot;
    public String leftArmXRot;
    public String leftArmYRot;
    public String leftArmZRot;
    public String rightArmXRot;
    public String rightArmYRot;
    public String rightArmZRot;
    public String leftLegXRot;
    public String leftLegYRot;
    public String leftLegZRot;
    public String rightLegXRot;
    public String rightLegYRot;
    public String rightLegZRot;
    public boolean bodyXRotAdvancedMode;
    public boolean bodyYRotAdvancedMode;
    public boolean headXRotAdvancedMode;
    public boolean headYRotAdvancedMode;
    public boolean headZRotAdvancedMode;
    public boolean leftArmXRotAdvancedMode;
    public boolean leftArmYRotAdvancedMode;
    public boolean leftArmZRotAdvancedMode;
    public boolean rightArmXRotAdvancedMode;
    public boolean rightArmYRotAdvancedMode;
    public boolean rightArmZRotAdvancedMode;
    public boolean leftLegXRotAdvancedMode;
    public boolean leftLegYRotAdvancedMode;
    public boolean leftLegZRotAdvancedMode;
    public boolean rightLegXRotAdvancedMode;
    public boolean rightLegYRotAdvancedMode;
    public boolean rightLegZRotAdvancedMode;

    public int x;
    public int y;
    public int width;
    public int height;

    public final PlayerModelRenderer normalRenderer = buildEntityRenderer(false);
    public final PlayerModelRenderer slimRenderer = buildEntityRenderer(true);

    @Nullable
    public SkinResourceSupplier skinTextureSupplier;
    @Nullable
    public CapeResourceSupplier capeTextureSupplier;


    public PlayerEntityModel(int x, int y, int width, int height, Component message) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Nullable
    protected static PlayerModelRenderer buildEntityRenderer(boolean slim) {
        try {
            return new PlayerModelRenderer(slim);
        } catch (Exception ignored) {}
        return null;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.updatePlayerDisplayName();
        this.updateSkinAndCape();

        float scale = this.stringToFloat(this.scale);
        if (scale == 0.0F) scale = 30;

        //Update element size based on entity size
        this.baseWidth = (int)(this.getActiveEntityProperties().getDimensions().width * scale);
        this.baseHeight = (int)(this.getActiveEntityProperties().getDimensions().height * scale);

        RenderSystem.enableBlend();

        PlayerModelProperties props = this.getActiveEntityProperties();
        int x = this.getAbsoluteX();
        int y = this.getAbsoluteY();
        int mouseOffsetX = this.baseWidth / 2;
        int mouseOffsetY = (this.baseHeight / 4) / 2;
        if (props.isBaby) mouseOffsetY += (this.baseHeight / 2) - mouseOffsetY; //not exactly the same eye pos as for adult size, but good enough
        this.renderPlayerEntity(x, y, (int)scale, (float)x - mouseX + mouseOffsetX, (float)y - mouseY + mouseOffsetY, props);

    }

    protected void renderPlayerEntity(int posX, int posY, int scale, float angleXComponent, float angleYComponent, PlayerModelProperties props) {
        float f = (float)Math.atan(angleXComponent / 40.0F);
        float f1 = (float)Math.atan(angleYComponent / 40.0F);
        innerRenderPlayerEntity(posX, posY, scale, f, f1, props, this.getActiveRenderer());
    }

    @SuppressWarnings("all")
    protected void innerRenderPlayerEntity(int posX, int posY, int scale, float angleXComponent, float angleYComponent, PlayerModelProperties props, PlayerModelRenderer renderer) {

        float bodyXRot = this.stringToFloat(this.bodyXRot);
        float bodyYRot = this.stringToFloat(this.bodyYRot);
        float headXRot = this.stringToFloat(this.headXRot);
        float headYRot = this.stringToFloat(this.headYRot);
        float headZRot = this.stringToFloat(this.headZRot);
        float leftArmXRot = this.stringToFloat(this.leftArmXRot);
        float leftArmYRot = this.stringToFloat(this.leftArmYRot);
        float leftArmZRot = this.stringToFloat(this.leftArmZRot);
        float rightArmXRot = this.stringToFloat(this.rightArmXRot);
        float rightArmYRot = this.stringToFloat(this.rightArmYRot);
        float rightArmZRot = this.stringToFloat(this.rightArmZRot);
        float leftLegXRot = this.stringToFloat(this.leftLegXRot);
        float leftLegYRot = this.stringToFloat(this.leftLegYRot);
        float leftLegZRot = this.stringToFloat(this.leftLegZRot);
        float rightLegXRot = this.stringToFloat(this.rightLegXRot);
        float rightLegYRot = this.stringToFloat(this.rightLegYRot);
        float rightLegZRot = this.stringToFloat(this.rightLegZRot);

        PoseStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushPose();
        modelViewStack.translate((posX+((props.getDimensions().width / 2) * scale)), (posY+(props.getDimensions().height * scale)), 1050.0F);
        modelViewStack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack innerPoseStack = new PoseStack();
        innerPoseStack.pushPose();
        innerPoseStack.translate(0.0F, 0.0F, 1000.0F);
        innerPoseStack.scale((float)scale, (float)scale, (float)scale);

        Quaternionf quat1 = this.bodyFollowsMouse ? new Quaternionf().rotateZ((float)Math.PI) : Axis.ZP.rotationDegrees(180.0F);
        Quaternionf quat2 = this.bodyFollowsMouse ? new Quaternionf().rotateX(angleYComponent * 20.0F * ((float)Math.PI / 180F)) : Axis.XP.rotationDegrees(bodyYRot);
        quat1.mul(quat2);
        innerPoseStack.mulPose(quat1);

        //Apply follow-mouse values by default
        props.yBodyRot = 180.0F + angleXComponent * 20.0F;
        props.yRot = 180.0F + angleXComponent * 40.0F;
        props.xRot = -angleYComponent * 20.0F;
        props.yHeadRot = 180.0F + angleXComponent * 40.0F;
        props.yHeadRotO = 180.0F + angleXComponent * 40.0F;
        props.headZRot = 0;

        if (!this.bodyFollowsMouse) {
            props.yBodyRot = 180.0F + bodyXRot;
        }
        if (!this.headFollowsMouse) {
            props.xRot = headYRot;
            props.yRot = 0;
            props.yHeadRot = 180.0F + headXRot;
            props.yHeadRotO = 180.0F + headXRot;
            props.headZRot = headZRot;
        }
        props.leftArmXRot = leftArmXRot;
        props.leftArmYRot = leftArmYRot;
        props.leftArmZRot = leftArmZRot;
        props.rightArmXRot = rightArmXRot;
        props.rightArmYRot = rightArmYRot;
        props.rightArmZRot = rightArmZRot;
        props.leftLegXRot = leftLegXRot;
        props.leftLegYRot = leftLegYRot;
        props.leftLegZRot = leftLegZRot;
        props.rightLegXRot = rightLegXRot;
        props.rightLegYRot = rightLegYRot;
        props.rightLegZRot = rightLegZRot;

        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        quat2.conjugate();
        dispatcher.overrideCameraOrientation(quat2);
        dispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            renderer.renderPlayerEntityItem(0.0D, 0.0D, 0.0D, 0.0F, 1.0F, innerPoseStack, bufferSource, 15728880);
        });
        bufferSource.endBatch();
        dispatcher.setRenderShadow(true);
        modelViewStack.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
        innerPoseStack.popPose();
    }

    protected float stringToFloat(@Nullable String s) {
        if (s == null) return 0.0F;
        s = PlaceholderParser.replacePlaceholders(s);
        s = s.replace(" ", "");
        try {
            return Float.parseFloat(s);
        } catch (Exception ignore) {}
        return 0.0F;
    }

    public void setCopyClientPlayer(boolean copyClientPlayer) {
        if (copyClientPlayer) {
            this.copyClientPlayer = true;
            this.autoCape = false;
            this.autoSkin = false;
            this.slim = false;
            this.setPlayerName(Minecraft.getInstance().getUser().getName());
            this.setSkinByPlayerName();
            this.setCapeByPlayerName();
        } else {
            this.copyClientPlayer = false;
            this.skinTextureSupplier = null;
            this.capeTextureSupplier = null;
        }
    }

    public void setPlayerName(@Nullable String playerName) {
        if (playerName == null) {
            playerName = "Steve";
        }
        this.playerName = playerName;
        this.updatePlayerDisplayName();
    }

    public void setHasParrotOnShoulder(boolean hasParrot, boolean onLeftShoulder) {
        if ((this.normalRenderer == null) || (this.slimRenderer == null)) return;
        this.hasParrotOnShoulder = hasParrot;
        this.parrotOnLeftShoulder = onLeftShoulder;
        this.normalRenderer.properties.hasParrotOnShoulder = hasParrot;
        this.slimRenderer.properties.hasParrotOnShoulder = hasParrot;
        this.normalRenderer.properties.parrotOnLeftShoulder = onLeftShoulder;
        this.slimRenderer.properties.parrotOnLeftShoulder = onLeftShoulder;
    }

    public void setCrouching(boolean crouching) {
        if ((this.normalRenderer == null) || (this.slimRenderer == null)) return;
        this.crouching = crouching;
        this.normalRenderer.properties.crouching = crouching;
        this.slimRenderer.properties.crouching = crouching;
    }

    public void setIsBaby(boolean isBaby) {
        if ((this.normalRenderer == null) || (this.slimRenderer == null)) return;
        this.isBaby = isBaby;
        this.normalRenderer.properties.isBaby = isBaby;
        this.slimRenderer.properties.isBaby = isBaby;
    }

    public void setCapeByPlayerName() {
        this.capeTextureSupplier = new CapeResourceSupplier(this.playerName, true);
    }

    public void setSkinByPlayerName() {
        this.skinTextureSupplier = new SkinResourceSupplier(this.playerName, true);
    }

    protected void updateSkinAndCape() {
        if ((this.normalRenderer == null) || (this.slimRenderer == null)) return;
        if (this.copyClientPlayer || this.autoSkin) {
            this.slim = (this.skinTextureSupplier == null) || this.skinTextureSupplier.isSlimPlayerNameSkin();
        }
        if ((this.capeTextureSupplier != null) && this.capeTextureSupplier.hasNoCape()) {
            this.capeTextureSupplier = null;
        }
        this.normalRenderer.properties.setSkinTextureLocation((this.skinTextureSupplier != null) ? this.skinTextureSupplier.getSkinLocation() : SkinResourceSupplier.DEFAULT_SKIN_LOCATION);
        this.slimRenderer.properties.setSkinTextureLocation((this.skinTextureSupplier != null) ? this.skinTextureSupplier.getSkinLocation() : SkinResourceSupplier.DEFAULT_SKIN_LOCATION);
        ResourceLocation capeLoc = null;
        if ((this.capeTextureSupplier != null) && !this.capeTextureSupplier.hasNoCape()) {
            capeLoc = this.capeTextureSupplier.getCapeLocation();
            if (capeLoc == CapeResourceSupplier.DEFAULT_CAPE_LOCATION) capeLoc = null;
        }
        this.normalRenderer.properties.setCapeTextureLocation(capeLoc);
        this.slimRenderer.properties.setCapeTextureLocation(capeLoc);
    }

    protected void updatePlayerDisplayName() {
        if ((this.normalRenderer == null) || (this.slimRenderer == null)) return;
        this.normalRenderer.properties.displayName = Component.literal(this.playerName);
        this.slimRenderer.properties.displayName = Component.literal(this.playerName);
    }

    public PlayerModelRenderer getActiveRenderer() {
        if (this.slim) {
            return this.slimRenderer;
        }
        return this.normalRenderer;
    }

    public PlayerModelProperties getActiveEntityProperties() {
        return this.getActiveRenderer().properties;
    }






}
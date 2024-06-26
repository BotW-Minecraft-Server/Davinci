package link.botwmcs.davinci.client.gui.component.player.supplier.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import link.botwmcs.davinci.client.gui.component.player.supplier.PlayerModelProperties;
import link.botwmcs.davinci.client.gui.component.player.supplier.PlayerModelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.PlayerModelPart;

public class PlayerCapeLayer extends PlayerEntityRenderLayer {
    public final PlayerModelProperties properties;
    public final PlayerModelRenderer renderer;

    public PlayerCapeLayer(PlayerModelRenderer renderer, PlayerModelProperties properties) {
        super(renderer);
        this.properties = properties;
        this.renderer = renderer;
    }

    // p_116617_ = packedLight
    // p_116619_ = limbSwing
    // p_116620_ = limbSwingAmount
    // p_116621_ = partialTicks
    // p_116622_ = ageInTicks
    // p_116623_ = netHeadYaw
    // p_116624_ = headPitch

    @Override
    public void render(PoseStack poseStack, MultiBufferSource p_116616_, int p_116617_, Entity livingEntity, float p_116619_, float p_116620_, float p_116621_, float p_116622_, float p_116623_, float p_116624_) {
        if (!this.properties.invisible && this.properties.isModelPartShown(PlayerModelPart.CAPE) && this.properties.getCapeTextureLocation() != null) {
            poseStack.pushPose();
            poseStack.translate(0.0F, 0.0F, 0.125F);
            double d0 = Mth.lerp((double)p_116621_, this.properties.xCloakO, this.properties.xCloak) - Mth.lerp((double)p_116621_, this.properties.xo, 0);
            double d1 = Mth.lerp((double)p_116621_, this.properties.yCloakO, this.properties.yCloak) - Mth.lerp((double)p_116621_, this.properties.yo, 0);
            double d2 = Mth.lerp((double)p_116621_, this.properties.zCloakO, this.properties.zCloak) - Mth.lerp((double)p_116621_, this.properties.zo, 0);
            float f = this.properties.yBodyRotO + (this.properties.yBodyRot - this.properties.yBodyRotO);
            double d3 = (double)Mth.sin(f * ((float)Math.PI / 180F));
            double d4 = (double)(-Mth.cos(f * ((float)Math.PI / 180F)));
            float f1 = (float)d1 * 10.0F;
            f1 = Mth.clamp(f1, -6.0F, 32.0F);
            float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
            f2 = Mth.clamp(f2, 0.0F, 150.0F);
            float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
            f3 = Mth.clamp(f3, -20.0F, 20.0F);
            if (f2 < 0.0F) {
                f2 = 0.0F;
            }

            float f4 = Mth.lerp(p_116621_, this.properties.oBob, this.properties.bob);
            f1 += Mth.sin(Mth.lerp(p_116621_, 0, 0) * 6.0F) * 32.0F * f4;
            if (this.properties.isCrouching()) {
                f1 += 25.0F;
            }

            poseStack.mulPose(Axis.XP.rotationDegrees(6.0F + f2 / 2.0F + f1));
            poseStack.mulPose(Axis.ZP.rotationDegrees(f3 / 2.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - f3 / 2.0F));
            VertexConsumer vertexconsumer = p_116616_.getBuffer(RenderType.entitySolid(this.properties.getCapeTextureLocation()));
            this.renderer.getModel().renderCloak(poseStack, vertexconsumer, p_116617_, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
    }
}
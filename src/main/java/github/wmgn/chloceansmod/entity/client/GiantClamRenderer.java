package github.wmgn.chloceansmod.entity.client;

import github.wmgn.chloceansmod.ChloceansMod;
import github.wmgn.chloceansmod.entity.custom.GiantClamEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GiantClamRenderer extends GeoEntityRenderer<GiantClamEntity> {
    public GiantClamRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new GiantClamModel());
        this.shadowRadius = 0.4f;
    }

    @Override
    public Identifier getTextureResource(GiantClamEntity instance) {
        return new Identifier(ChloceansMod.MOD_ID, "textures/entity/giantclam_texture.png");
        //return super.getTextureResource(animatable);
    }

    @Override
    public RenderLayer getRenderType(GiantClamEntity animatable, float partialTick, MatrixStack poseStack,
                                     VertexConsumerProvider bufferSource, VertexConsumer buffer,
                                     int packedLight, Identifier texture) {
        poseStack.scale(2f,2f,2f);

        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}

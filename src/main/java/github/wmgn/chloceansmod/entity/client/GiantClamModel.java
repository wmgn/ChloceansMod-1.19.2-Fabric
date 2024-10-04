package github.wmgn.chloceansmod.entity.client;

import github.wmgn.chloceansmod.ChloceansMod;
import github.wmgn.chloceansmod.entity.custom.GiantClamEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GiantClamModel extends AnimatedGeoModel<GiantClamEntity> {

    @Override
    public Identifier getModelResource(GiantClamEntity object) {
        return new Identifier(ChloceansMod.MOD_ID, "geo/giantclam.geo.json");
    }

    @Override
    public Identifier getTextureResource(GiantClamEntity object) {
        return new Identifier(ChloceansMod.MOD_ID, "textures/entity/giantclam_texture.png");
    }

    @Override
    public Identifier getAnimationResource(GiantClamEntity animatable) {
        return new Identifier(ChloceansMod.MOD_ID, "animations/giantclam.animation.json");
    }

    private static final Identifier PARTICLE =
            new Identifier(ChloceansMod.MOD_ID, "textures/particle/giant_clam_bubble.png");

}

package github.wmgn.chloceansmod;

import github.wmgn.chloceansmod.entity.ModEntities;
import github.wmgn.chloceansmod.entity.client.GiantClamRenderer;
import github.wmgn.chloceansmod.particles.ModClientParticles;
import github.wmgn.chloceansmod.screen.GiantClamScreen;
import github.wmgn.chloceansmod.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

//@Environment(EnvType.CLIENT)
public class ChloceansModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {


        EntityRendererRegistry.register(ModEntities.GIANTCLAM, GiantClamRenderer::new);

        //ScreenRegistry.register(ModScreenHandlers.GIANT_CLAM_SCREEN_HANDLER, GiantClamScreen::new);
        HandledScreens.register(ModScreenHandlers.GIANT_CLAM_SCREEN_HANDLER, GiantClamScreen::new);

        ModClientParticles.registerClientParticles();

    }
}



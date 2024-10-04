package github.wmgn.chloceansmod.particles;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class ModClientParticles {
    public static void registerClientParticles() {
        ParticleFactoryRegistry.getInstance().register(ModParticles.GIANT_CLAM_BUBBLES, GiantClamBubblesParticle.Factory::new);
    }
}
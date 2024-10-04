package github.wmgn.chloceansmod.particles;

import github.wmgn.chloceansmod.ChloceansMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {
    public static final DefaultParticleType GIANT_CLAM_BUBBLES = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(ChloceansMod.MOD_ID, "giantclambubbles"), GIANT_CLAM_BUBBLES);
    }
}
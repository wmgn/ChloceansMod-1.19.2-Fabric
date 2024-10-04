package github.wmgn.chloceansmod.entity;

import github.wmgn.chloceansmod.ChloceansMod;
import github.wmgn.chloceansmod.entity.custom.GiantClamEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {
    public static final EntityType<GiantClamEntity> GIANTCLAM = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(ChloceansMod.MOD_ID, "giantclam"),
            FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, GiantClamEntity::new)
                    .dimensions(EntityDimensions.fixed(4.2f, 1.5f)).build());
                                                     // ^^^ EntityDimensions.fixed is hitbox
}

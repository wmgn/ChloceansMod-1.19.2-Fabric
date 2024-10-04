package github.wmgn.chloceansmod.world.gen;

import github.wmgn.chloceansmod.entity.ModEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntitySpawn {

    public static void addEntitySpawn(){
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.DEEP_OCEAN,
                        BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN, BiomeKeys.COLD_OCEAN,
                        BiomeKeys.DEEP_COLD_OCEAN), SpawnGroup.WATER_CREATURE,
                ModEntities.GIANTCLAM, 300, 1,  3);

        SpawnRestriction.register(ModEntities.GIANTCLAM, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);
    }


}

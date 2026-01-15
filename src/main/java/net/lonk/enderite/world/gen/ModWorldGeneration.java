package net.lonk.enderite.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.lonk.enderite.world.ModPlacedFeatures;
import net.minecraft.world.gen.GenerationStep;

public class ModWorldGeneration {
    public static void generateWorldGeneration() {
        BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(), GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.ENDERITE_ORE_PLACED_KEY);
    }
}

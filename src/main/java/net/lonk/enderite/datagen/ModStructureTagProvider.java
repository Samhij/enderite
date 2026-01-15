package net.lonk.enderite.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.lonk.enderite.util.ModTags;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.gen.structure.Structure;

import java.util.concurrent.CompletableFuture;

public class ModStructureTagProvider extends FabricTagProvider<Structure> {
    public ModStructureTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.STRUCTURE, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        var structureLookup = wrapperLookup.getOrThrow(RegistryKeys.STRUCTURE);
        var tagBuilder = getOrCreateTagBuilder(ModTags.Structures.ALL_STRUCTURES);

        structureLookup.streamKeys().forEach(tagBuilder::add);
    }
}

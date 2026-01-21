package net.lonk.enderite.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.lonk.enderite.block.ModBlocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        valueLookupBuilder(BlockTags.BEACON_BASE_BLOCKS)
                .add(ModBlocks.ENDERITE_BLOCK)
                .add(ModBlocks.VOID_INFUSED_BLOCK)
        ;

        valueLookupBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.ENDERITE_ORE)
                .add(ModBlocks.ENDERITE_BLOCK)
                .add(ModBlocks.VOID_INFUSED_BLOCK)
                .add(ModBlocks.VOID_INFUSION_TABLE)
        ;

        valueLookupBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.ENDERITE_ORE)
                .add(ModBlocks.ENDERITE_BLOCK)
                .add(ModBlocks.VOID_INFUSED_BLOCK)
                .add(ModBlocks.VOID_INFUSION_TABLE)
        ;
    }
}

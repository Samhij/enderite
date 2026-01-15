package net.lonk.enderite.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.lonk.enderite.block.ModBlocks;
import net.lonk.enderite.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.ENDERITE_ORE, block -> oreDrops(block, ModItems.RAW_ENDERITE));
        addDrop(ModBlocks.VOID_INFUSION_TABLE);
        addDrop(ModBlocks.ENDERITE_BLOCK);
        addDrop(ModBlocks.VOID_INFUSED_BLOCK);
    }
}

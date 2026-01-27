package net.lonk.enderite.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.lonk.enderite.block.ModBlocks;
import net.lonk.enderite.item.ModArmorMaterials;
import net.lonk.enderite.item.ModItems;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ENDERITE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ENDERITE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.VOID_INFUSED_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.ENDERITE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_ENDERITE, Models.GENERATED);
        itemModelGenerator.register(ModItems.ENDERITE_SCRAP, Models.GENERATED);
        itemModelGenerator.register(ModItems.ENDERITE_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.VOID_INFUSED_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.VOID_INFUSED_CHORUS_FRUIT, Models.GENERATED);

        itemModelGenerator.register(ModItems.ENDERITE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ENDERITE_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ENDERITE_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ENDERITE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ENDERITE_HOE, Models.HANDHELD);

        itemModelGenerator.registerArmor((ArmorItem) ModItems.ENDERITE_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.ENDERITE_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.ENDERITE_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.ENDERITE_BOOTS);


        itemModelGenerator.register(ModItems.VOID_INFUSED_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.VOID_INFUSED_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.VOID_INFUSED_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.VOID_INFUSED_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.VOID_INFUSED_HOE, Models.HANDHELD);

        itemModelGenerator.registerArmor((ArmorItem) ModItems.VOID_INFUSED_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.VOID_INFUSED_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.VOID_INFUSED_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.VOID_INFUSED_BOOTS);
    }
}

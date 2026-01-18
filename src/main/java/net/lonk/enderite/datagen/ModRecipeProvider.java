package net.lonk.enderite.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.lonk.enderite.block.ModBlocks;
import net.lonk.enderite.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter exporter) {
        return new RecipeGenerator(wrapperLookup, exporter) {
            @Override
            public void generate() {
                final List<ItemConvertible> ENDERITE_SMELTABLES = List.of(
                        ModItems.RAW_ENDERITE,
                        ModBlocks.ENDERITE_ORE
                );

                // Smelting & Blasting
                offerSmelting(ENDERITE_SMELTABLES, RecipeCategory.MISC, ModItems.ENDERITE_SCRAP, 0.25f, 200, "enderite");
                offerBlasting(ENDERITE_SMELTABLES, RecipeCategory.MISC, ModItems.ENDERITE_SCRAP, 0.25f, 100, "enderite");

                // Items
                createShapeless(RecipeCategory.MISC, ModItems.ENDERITE_INGOT)
                        .input(ModItems.ENDERITE_SCRAP, 8)
                        .input(Items.ECHO_SHARD, 1)
                        .criterion(hasItem(ModItems.ENDERITE_SCRAP), conditionsFromItem(ModItems.ENDERITE_SCRAP))
                        .offerTo(exporter, "enderite_ingot_from_enderite_scrap");

                offerSmithingTemplateCopyingRecipe(ModItems.ENDERITE_UPGRADE_SMITHING_TEMPLATE, Blocks.END_STONE);

                // Blocks
                offerReversibleCompactingRecipes(RecipeCategory.MISC, ModItems.ENDERITE_INGOT, RecipeCategory.MISC, ModBlocks.ENDERITE_BLOCK);
                offerReversibleCompactingRecipes(RecipeCategory.MISC, ModItems.VOID_INFUSED_INGOT, RecipeCategory.MISC, ModBlocks.VOID_INFUSED_BLOCK);

                createShaped(RecipeCategory.MISC, ModBlocks.VOID_INFUSION_TABLE)
                        .pattern("OEO")
                        .pattern(" B ")
                        .pattern("CCC")
                        .input('O', Blocks.CRYING_OBSIDIAN)
                        .input('E', Items.ENDER_EYE)
                        .input('B', Blocks.END_STONE_BRICKS)
                        .input('C', Blocks.CHORUS_FLOWER)
                        .criterion(hasItem(Items.ENDER_EYE), conditionsFromItem(Items.ENDER_EYE))
                        .offerTo(exporter);

                // Tools
                offerEnderiteUpgradeRecipe(Items.NETHERITE_SWORD, RecipeCategory.COMBAT, ModItems.ENDERITE_SWORD);
                offerEnderiteUpgradeRecipe(Items.NETHERITE_PICKAXE, RecipeCategory.TOOLS, ModItems.ENDERITE_PICKAXE);
                offerEnderiteUpgradeRecipe(Items.NETHERITE_SHOVEL, RecipeCategory.TOOLS, ModItems.ENDERITE_SHOVEL);
                offerEnderiteUpgradeRecipe(Items.NETHERITE_AXE, RecipeCategory.TOOLS, ModItems.ENDERITE_AXE);
                offerEnderiteUpgradeRecipe(Items.NETHERITE_HOE, RecipeCategory.TOOLS, ModItems.ENDERITE_HOE);

                // Armor
                offerEnderiteUpgradeRecipe(Items.NETHERITE_HELMET, RecipeCategory.COMBAT, ModItems.ENDERITE_HELMET);
                offerEnderiteUpgradeRecipe(Items.NETHERITE_CHESTPLATE, RecipeCategory.COMBAT, ModItems.ENDERITE_CHESTPLATE);
                offerEnderiteUpgradeRecipe(Items.NETHERITE_LEGGINGS, RecipeCategory.COMBAT, ModItems.ENDERITE_LEGGINGS);
                offerEnderiteUpgradeRecipe(Items.NETHERITE_BOOTS, RecipeCategory.COMBAT, ModItems.ENDERITE_BOOTS);

                // Void Infusion Recipes are manually created in src/main/resources/data/enderite/recipe/
                // to avoid datagen serialization issues
            }

            private void offerEnderiteUpgradeRecipe(Item input, RecipeCategory category, Item result) {
                SmithingTransformRecipeJsonBuilder.create(
                                Ingredient.ofItems(ModItems.ENDERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.ofItems(input), Ingredient.ofItems(ModItems.ENDERITE_INGOT), category, result
                        )
                        .criterion(hasItem(ModItems.ENDERITE_INGOT), conditionsFromItem(ModItems.ENDERITE_INGOT))
                        .offerTo(exporter, getItemPath(result) + "_smithing");
            }
        };
    }

    @Override
    public String getName() {
        return "Recipes";
    }
}

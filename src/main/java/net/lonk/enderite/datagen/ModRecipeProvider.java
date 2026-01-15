package net.lonk.enderite.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.lonk.enderite.Enderite;
import net.lonk.enderite.block.ModBlocks;
import net.lonk.enderite.item.ModItems;
import net.lonk.enderite.recipe.VoidInfusionRecipe;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

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
                        .input('C', Blocks.CHORUS_PLANT)
                        .criterion(hasItem(Items.ENDER_EYE), conditionsFromItem(Items.ENDER_EYE))
                        .offerTo(exporter);

                offerEnderiteUpgradeRecipe(Items.NETHERITE_SWORD, RecipeCategory.COMBAT, ModItems.ENDERITE_SWORD);
                offerEnderiteUpgradeRecipe(Items.NETHERITE_PICKAXE, RecipeCategory.TOOLS, ModItems.ENDERITE_PICKAXE);
                offerEnderiteUpgradeRecipe(Items.NETHERITE_SHOVEL, RecipeCategory.TOOLS, ModItems.ENDERITE_SHOVEL);
                offerEnderiteUpgradeRecipe(Items.NETHERITE_AXE, RecipeCategory.TOOLS, ModItems.ENDERITE_AXE);
                offerEnderiteUpgradeRecipe(Items.NETHERITE_HOE, RecipeCategory.TOOLS, ModItems.ENDERITE_HOE);

                // Void Infusion Recipes
                offerVoidInfusionRecipe(ModItems.ENDERITE_INGOT, ModItems.VOID_INFUSED_INGOT);
                offerVoidInfusionRecipe(Items.CHORUS_FRUIT, ModItems.VOID_INFUSED_CHORUS_FRUIT);

                // Tools
                offerVoidInfusionRecipe(ModItems.ENDERITE_SWORD, ModItems.VOID_INFUSED_SWORD);
                offerVoidInfusionRecipe(ModItems.ENDERITE_PICKAXE, ModItems.VOID_INFUSED_PICKAXE);
                offerVoidInfusionRecipe(ModItems.ENDERITE_SHOVEL, ModItems.VOID_INFUSED_SHOVEL);
                offerVoidInfusionRecipe(ModItems.ENDERITE_AXE, ModItems.VOID_INFUSED_AXE);
                offerVoidInfusionRecipe(ModItems.ENDERITE_HOE, ModItems.VOID_INFUSED_HOE);

                // Armor
                offerVoidInfusionRecipe(ModItems.ENDERITE_HELMET, ModItems.VOID_INFUSED_HELMET);
                offerVoidInfusionRecipe(ModItems.ENDERITE_CHESTPLATE, ModItems.VOID_INFUSED_CHESTPLATE);
                offerVoidInfusionRecipe(ModItems.ENDERITE_LEGGINGS, ModItems.VOID_INFUSED_LEGGINGS);
                offerVoidInfusionRecipe(ModItems.ENDERITE_BOOTS, ModItems.VOID_INFUSED_BOOTS);
            }

            private void offerEnderiteUpgradeRecipe(Item input, RecipeCategory category, Item result) {
                SmithingTransformRecipeJsonBuilder.create(
                                Ingredient.ofItems(ModItems.ENDERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.ofItems(input), Ingredient.ofItems(ModItems.ENDERITE_INGOT), category, result
                        )
                        .criterion(hasItem(ModItems.ENDERITE_INGOT), conditionsFromItem(ModItems.ENDERITE_INGOT))
                        .offerTo(exporter, getItemPath(result) + "_smithing");
            }

            private void offerVoidInfusionRecipe(ItemConvertible input, ItemConvertible output) {
                offerVoidInfusionRecipe(input, output, 200);
            }

            private void offerVoidInfusionRecipe(ItemConvertible input, ItemConvertible output, int infusionTime) {
                VoidInfusionRecipe recipe = new VoidInfusionRecipe(
                        Ingredient.ofItems(input),
                        new ItemStack(output.asItem()),
                        infusionTime
                );

                Identifier id = Identifier.of(Enderite.MOD_ID, getItemPath(output) + "_from_void_infusion");
                exporter.accept(
                        RegistryKey.of(RegistryKeys.RECIPE, id),
                        recipe,
                        null
                );
            }
        };
    }

    @Override
    public String getName() {
        return "Recipes";
    }
}

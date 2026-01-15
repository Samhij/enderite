package net.lonk.enderite.recipe;

import net.lonk.enderite.Enderite;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipeTypes {
    public static final RecipeType<VoidInfusionRecipe> VOID_INFUSION =
            Registry.register(Registries.RECIPE_TYPE, Identifier.of(Enderite.MOD_ID, "void_infusion"),
                    new RecipeType<VoidInfusionRecipe>() {
                        @Override
                        public String toString() {
                            return "void_infusion";
                        }
                    });

    public static final RecipeSerializer<VoidInfusionRecipe> VOID_INFUSION_SERIALIZER =
            Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(Enderite.MOD_ID, "void_infusion"),
                    new VoidInfusionRecipe.Serializer());

    public static void init() {}
}

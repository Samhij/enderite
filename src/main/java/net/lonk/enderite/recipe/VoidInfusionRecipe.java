package net.lonk.enderite.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategories;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.List;

public class VoidInfusionRecipe implements Recipe<SingleStackRecipeInput> {
    private final Ingredient ingredient;
    private final ItemStack result;
    private final int infusionTime;

    public VoidInfusionRecipe(Ingredient ingredient, ItemStack result, int infusionTime) {
        this.ingredient = ingredient;
        this.result = result;
        this.infusionTime = infusionTime;
    }

    @Override
    public boolean matches(SingleStackRecipeInput input, World world) {
        return this.ingredient.test(input.item());
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return this.result.copy();
    }

    @Override
    public RecipeSerializer<VoidInfusionRecipe> getSerializer() {
        return ModRecipeTypes.VOID_INFUSION_SERIALIZER;
    }

    @Override
    public RecipeType<VoidInfusionRecipe> getType() {
        return ModRecipeTypes.VOID_INFUSION;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.NONE;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public int getInfusionTime() {
        return infusionTime;
    }

    public ItemStack getResultStack() {
        return result;
    }

    public static class Serializer implements RecipeSerializer<VoidInfusionRecipe> {
        public static final MapCodec<VoidInfusionRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Ingredient.CODEC.fieldOf("ingredient").forGetter(VoidInfusionRecipe::getIngredient),
                        ItemStack.CODEC.fieldOf("result").forGetter(VoidInfusionRecipe::getResultStack),
                        Codec.INT.optionalFieldOf("infusionTime", 200).forGetter(VoidInfusionRecipe::getInfusionTime)
                ).apply(instance, VoidInfusionRecipe::new)
        );

        public static final PacketCodec<RegistryByteBuf, VoidInfusionRecipe> PACKET_CODEC = PacketCodec.tuple(
                Ingredient.PACKET_CODEC, VoidInfusionRecipe::getIngredient,
                ItemStack.PACKET_CODEC, VoidInfusionRecipe::getResultStack,
                PacketCodec.ofStatic(PacketByteBuf::writeInt, PacketByteBuf::readInt), VoidInfusionRecipe::getInfusionTime,
                VoidInfusionRecipe::new
        );

        @Override
        public MapCodec<VoidInfusionRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, VoidInfusionRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}

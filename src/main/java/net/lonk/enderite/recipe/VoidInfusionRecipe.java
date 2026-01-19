package net.lonk.enderite.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategories;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.List;

public class VoidInfusionRecipe implements Recipe<VoidInfusionRecipe.VoidInfusionRecipeInput> {
    private final Ingredient baseIngredient;
    private final Ingredient voidIngredient;
    private final ItemStack result;
    private final int infusionTime;

    public VoidInfusionRecipe(Ingredient baseIngredient, Ingredient voidIngredient, ItemStack result, int infusionTime) {
        this.baseIngredient = baseIngredient;
        this.voidIngredient = voidIngredient;
        this.result = result;
        this.infusionTime = infusionTime;
    }

    @Override
    public boolean matches(VoidInfusionRecipeInput input, World world) {
        boolean baseMatches = this.baseIngredient.test(input.base());
        // If no void ingredient is specified (null or empty ingredient), the void slot should be empty
        if (this.voidIngredient == null || this.voidIngredient.isEmpty()) {
            return baseMatches && input.voidItem().isEmpty();
        }
        // Otherwise, the void ingredient must match
        return baseMatches && this.voidIngredient.test(input.voidItem());
    }

    @Override
    public ItemStack craft(VoidInfusionRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack output = this.result.copy();
        ItemStack baseStack = input.base();

        // Copy all components from base input to output (enchantments, armor trims, etc.)
        output.applyComponentsFrom(baseStack.getComponents());

        // Reset damage to 0 (repair the item)
        output.setDamage(0);

        return output;
    }

    public record VoidInfusionRecipeInput(ItemStack base, ItemStack voidItem) implements RecipeInput {
        @Override
        public ItemStack getStackInSlot(int slot) {
            return switch (slot) {
                case 0 -> base;
                case 1 -> voidItem;
                default -> ItemStack.EMPTY;
            };
        }

        @Override
        public int size() {
            return 2;
        }
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

    public Ingredient getBaseIngredient() {
        return baseIngredient;
    }

    public Ingredient getVoidIngredient() {
        return voidIngredient;
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
                        Ingredient.CODEC.fieldOf("base_ingredient").forGetter(VoidInfusionRecipe::getBaseIngredient),
                        Ingredient.CODEC.optionalFieldOf("void_ingredient").forGetter(recipe -> {
                            Ingredient voidIng = recipe.getVoidIngredient();
                            return voidIng != null && !voidIng.isEmpty() ? java.util.Optional.of(voidIng) : java.util.Optional.empty();
                        }),
                        ItemStack.CODEC.fieldOf("result").forGetter(VoidInfusionRecipe::getResultStack),
                        Codec.INT.optionalFieldOf("infusionTime", 200).forGetter(VoidInfusionRecipe::getInfusionTime)
                ).apply(instance, (base, voidOpt, result, time) ->
                    new VoidInfusionRecipe(base, voidOpt.orElse(null), result, time))
        );

        public static final PacketCodec<RegistryByteBuf, VoidInfusionRecipe> PACKET_CODEC = PacketCodec.tuple(
                Ingredient.PACKET_CODEC, VoidInfusionRecipe::getBaseIngredient,
                Ingredient.PACKET_CODEC, VoidInfusionRecipe::getVoidIngredient,
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

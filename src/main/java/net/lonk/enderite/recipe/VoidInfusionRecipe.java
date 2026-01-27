package net.lonk.enderite.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

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

    @Override
    public boolean fits(int width, int height) {
        return true;
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
        public int getSize() {
            return 2;
        }
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return this.result.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.VOID_INFUSION_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.VOID_INFUSION;
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
                        Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("base_ingredient").forGetter(VoidInfusionRecipe::getBaseIngredient),
                        Ingredient.ALLOW_EMPTY_CODEC.optionalFieldOf("void_ingredient", Ingredient.EMPTY).forGetter(VoidInfusionRecipe::getVoidIngredient),
                        ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(VoidInfusionRecipe::getResultStack),
                        Codec.INT.optionalFieldOf("infusionTime", 200).forGetter(VoidInfusionRecipe::getInfusionTime)
                ).apply(instance, VoidInfusionRecipe::new)
        );

        public static final PacketCodec<RegistryByteBuf, VoidInfusionRecipe> PACKET_CODEC = PacketCodec.of(
                (recipe, buf) -> {
                    Ingredient.PACKET_CODEC.encode(buf, recipe.baseIngredient);
                    Ingredient.PACKET_CODEC.encode(buf, recipe.voidIngredient != null ? recipe.voidIngredient : Ingredient.EMPTY);
                    ItemStack.PACKET_CODEC.encode(buf, recipe.result);
                    buf.writeInt(recipe.infusionTime);
                },
                buf -> {
                    Ingredient baseIngredient = Ingredient.PACKET_CODEC.decode(buf);
                    Ingredient voidIngredient = Ingredient.PACKET_CODEC.decode(buf);
                    ItemStack result = ItemStack.PACKET_CODEC.decode(buf);
                    int infusionTime = buf.readInt();
                    return new VoidInfusionRecipe(baseIngredient, voidIngredient, result, infusionTime);
                }
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

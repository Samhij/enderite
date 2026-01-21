package net.lonk.enderite.block.entity;

import net.lonk.enderite.block.ModBlockEntities;
import net.lonk.enderite.recipe.ModRecipeTypes;
import net.lonk.enderite.recipe.VoidInfusionRecipe;
import net.lonk.enderite.screen.VoidInfusionTableScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class VoidInfusionTableBlockEntity extends BlockEntity implements SidedInventory, NamedScreenHandlerFactory {
    private static final int VOID_INPUT_SLOT = 0;  // Left slot
    private static final int BASE_INPUT_SLOT = 1;  // Middle/right slot
    private static final int FUEL_SLOT = 2;
    private static final int OUTPUT_SLOT = 3;
    private static final int INFUSION_TIME = 12000; // 10 minutes (12000 ticks)

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    private int progress = 0;
    private int fuelRemaining = 0;
    private int fuelTime = 0; // Max burn time for current fuel

    // Cache for recipe lookup optimization
    private RecipeEntry<VoidInfusionRecipe> cachedRecipe = null;
    private ItemStack cachedBaseInputStack = ItemStack.EMPTY;
    private ItemStack cachedVoidInputStack = ItemStack.EMPTY;

    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> VoidInfusionTableBlockEntity.this.progress;
                case 1 -> {
                    // Use cached recipe to avoid repeated lookups
                    RecipeEntry<VoidInfusionRecipe> recipe = getCachedRecipe();
                    if (recipe != null) {
                        yield recipe.value().getInfusionTime();
                    }
                    yield INFUSION_TIME;
                }
                case 2 -> VoidInfusionTableBlockEntity.this.fuelRemaining;
                case 3 -> VoidInfusionTableBlockEntity.this.fuelTime;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> VoidInfusionTableBlockEntity.this.progress = value;
                case 2 -> VoidInfusionTableBlockEntity.this.fuelRemaining = value;
                case 3 -> VoidInfusionTableBlockEntity.this.fuelTime = value;
            }
        }

        @Override
        public int size() {
            return 4;
        }
    };

    public VoidInfusionTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VOID_INFUSION_TABLE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void onBlockReplaced(BlockPos pos, BlockState oldState) {
        ItemScatterer.spawn(world, pos, (this));
        super.onBlockReplaced(pos, oldState);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        Inventories.writeData(view, inventory);
        view.putInt("Progress", progress);
        view.putInt("FuelRemaining", fuelRemaining);
        view.putInt("FuelTime", fuelTime);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        Inventories.readData(view, inventory);
        progress = view.getInt("Progress", 0);
        fuelRemaining = view.getInt("FuelRemaining", 0);
        fuelTime = view.getInt("FuelTime", 0);
    }

    private RecipeEntry<VoidInfusionRecipe> getCachedRecipe() {
        if (world == null || !(world instanceof ServerWorld)) {
            return null;
        }

        ItemStack baseInputStack = inventory.get(BASE_INPUT_SLOT);
        ItemStack voidInputStack = inventory.get(VOID_INPUT_SLOT);

        // Check if cache is still valid (item type and components must match)
        if (cachedRecipe != null &&
            ItemStack.areItemsAndComponentsEqual(cachedBaseInputStack, baseInputStack) &&
            ItemStack.areItemsAndComponentsEqual(cachedVoidInputStack, voidInputStack)) {
            return cachedRecipe;
        }

        // Cache is invalid, perform lookup and update cache
        if (baseInputStack.isEmpty()) {
            cachedRecipe = null;
            cachedBaseInputStack = ItemStack.EMPTY;
            cachedVoidInputStack = ItemStack.EMPTY;
            return null;
        }

        // Void ingredient can be empty for recipes that don't require it
        VoidInfusionRecipe.VoidInfusionRecipeInput recipeInput =
            new VoidInfusionRecipe.VoidInfusionRecipeInput(baseInputStack, voidInputStack);
        ServerRecipeManager recipeManager = ((ServerWorld) world).getRecipeManager();

        cachedRecipe = recipeManager.values()
                .stream()
                .filter(recipe -> recipe.value().getType() == ModRecipeTypes.VOID_INFUSION)
                .filter(recipe -> {
                    VoidInfusionRecipe voidRecipe = (VoidInfusionRecipe) recipe.value();
                    return voidRecipe.matches(recipeInput, world);
                })
                .map(recipe -> new RecipeEntry<>(recipe.id(), (VoidInfusionRecipe) recipe.value()))
                .findFirst()
                .orElse(null);

        cachedBaseInputStack = baseInputStack.copy();
        cachedVoidInputStack = voidInputStack.copy();

        return cachedRecipe;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }

        boolean dirty = false;

        // Check if we have valid inputs
        ItemStack baseInputStack = inventory.get(BASE_INPUT_SLOT);
        ItemStack voidInputStack = inventory.get(VOID_INPUT_SLOT);
        ItemStack fuelStack = inventory.get(FUEL_SLOT);
        ItemStack outputStack = inventory.get(OUTPUT_SLOT);

        // Find matching recipe (using cached lookup)
        RecipeEntry<VoidInfusionRecipe> recipeEntry = getCachedRecipe();

        if (recipeEntry != null) {
            VoidInfusionRecipe recipe = recipeEntry.value();
            ItemStack recipeOutput = recipe.getResultStack();
            int recipeTime = recipe.getInfusionTime();

            // Check if we can output
            boolean canOutput = outputStack.isEmpty() ||
                    (ItemStack.areItemsAndComponentsEqual(outputStack, recipeOutput) &&
                     outputStack.getCount() < outputStack.getMaxCount());

            if (canOutput) {
                // Try to consume fuel if we need it
                if (fuelRemaining <= 0 && fuelStack.isOf(Items.DRAGON_BREATH)) {
                    fuelStack.decrement(1);
                    fuelRemaining = recipeTime;
                    fuelTime = recipeTime; // Store the max fuel time
                    dirty = true;
                }

                // Process if we have fuel
                if (fuelRemaining > 0) {
                    progress++;
                    fuelRemaining--;
                    dirty = true;

                    // Spawn particles for visual effect
                    if (world.random.nextInt(5) == 0) {
                        spawnInfusionParticles(world, pos);
                    }

                    // Complete the infusion
                    if (progress >= recipeTime) {
                        baseInputStack.decrement(1);
                        // Only consume void ingredient if the recipe requires it
                        Ingredient voidIng = recipe.getVoidIngredient();
                        if (!voidInputStack.isEmpty() && voidIng != null && !voidIng.isEmpty()) {
                            voidInputStack.decrement(1);
                        }

                        if (outputStack.isEmpty()) {
                            inventory.set(OUTPUT_SLOT, recipeOutput.copy());
                        } else {
                            outputStack.increment(1);
                        }

                        progress = 0;
                        dirty = true;
                    }
                }
            } else {
                // Reset progress if we can't output
                if (progress > 0) {
                    progress = 0;
                    dirty = true;
                }
            }
        } else {
            // Reset progress if no recipe matches
            if (progress > 0) {
                progress = 0;
                dirty = true;
            }
        }

        if (dirty) {
            markDirty();
        }
    }

    private void spawnInfusionParticles(World world, BlockPos pos) {
        if (world instanceof ServerWorld serverWorld) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.7;
            double z = pos.getZ() + 0.5;

            // Spawn portal particles (purple/void-like effect)
            for (int i = 0; i < 3; i++) {
                double offsetX = (world.random.nextDouble() - 0.5) * 0.5;
                double offsetY = world.random.nextDouble() * 0.5;
                double offsetZ = (world.random.nextDouble() - 0.5) * 0.5;

                serverWorld.spawnParticles(
                        ParticleTypes.PORTAL,
                        x + offsetX, y + offsetY, z + offsetZ,
                        1,
                        0.0, 0.0, 0.0,
                        0.1
                );
            }

            // Add some end rod particles (similar visual to dragon breath)
            if (world.random.nextInt(3) == 0) {
                double offsetX = (world.random.nextDouble() - 0.5) * 0.3;
                double offsetZ = (world.random.nextDouble() - 0.5) * 0.3;

                serverWorld.spawnParticles(
                        ParticleTypes.END_ROD,
                        x + offsetX, y + 0.2, z + offsetZ,
                        1,
                        0.0, 0.05, 0.0,
                        0.02
                );
            }

            // Add enchanting table particles occasionally
            if (world.random.nextInt(10) == 0) {
                double offsetX = (world.random.nextDouble() - 0.5) * 0.8;
                double offsetZ = (world.random.nextDouble() - 0.5) * 0.8;

                serverWorld.spawnParticles(
                        ParticleTypes.ENCHANT,
                        x + offsetX, y + 1.0, z + offsetZ,
                        1,
                        0.0, -0.5, 0.0,
                        0.5
                );
            }

            // Play a subtle sound effect occasionally
            if (world.random.nextInt(40) == 0) {
                world.playSound(null, pos, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.2f, 1.5f);
            }
        }
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return INFUSION_TIME;
    }

    // SidedInventory implementation
    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return new int[]{OUTPUT_SLOT};
        } else if (side == Direction.UP) {
            return new int[]{BASE_INPUT_SLOT, VOID_INPUT_SLOT};
        } else {
            return new int[]{FUEL_SLOT};
        }
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        if (slot == BASE_INPUT_SLOT || slot == VOID_INPUT_SLOT) {
            // Check if there's a recipe for this item using optimized lookup
            if (world instanceof ServerWorld && !stack.isEmpty()) {
                ServerRecipeManager recipeManager = ((ServerWorld) world).getRecipeManager();
                // For base input, we need to check if any recipe accepts this item in the base slot
                // For void input, we need to check if it can be a void ingredient
                ItemStack otherStack = slot == BASE_INPUT_SLOT ?
                    inventory.get(VOID_INPUT_SLOT) : inventory.get(BASE_INPUT_SLOT);

                // If the other slot is empty, we can't validate yet, so allow insertion
                if (otherStack.isEmpty()) {
                    return true;
                }

                // Create recipe input based on which slot we're inserting into
                VoidInfusionRecipe.VoidInfusionRecipeInput recipeInput = slot == BASE_INPUT_SLOT ?
                    new VoidInfusionRecipe.VoidInfusionRecipeInput(stack, otherStack) :
                    new VoidInfusionRecipe.VoidInfusionRecipeInput(otherStack, stack);

                return recipeManager.values()
                        .stream()
                        .filter(recipe -> recipe.value().getType() == ModRecipeTypes.VOID_INFUSION)
                        .anyMatch(recipe -> {
                            VoidInfusionRecipe voidRecipe = (VoidInfusionRecipe) recipe.value();
                            return voidRecipe.matches(recipeInput, world);
                        });
            }
            return false;
        } else if (slot == FUEL_SLOT) {
            return stack.isOf(Items.DRAGON_BREATH);
        }
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot == OUTPUT_SLOT;
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        // Optimized: Use early return to avoid unnecessary iterations
        for (ItemStack stack : inventory) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(inventory, slot, amount);
        if (!result.isEmpty()) {
            markDirty();
        }
        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        // Invalidate cache when input slots change to different items
        if (slot == BASE_INPUT_SLOT) {
            ItemStack oldStack = inventory.get(BASE_INPUT_SLOT);
            // Only invalidate if the item or components actually changed
            if (!ItemStack.areItemsAndComponentsEqual(oldStack, stack)) {
                cachedRecipe = null;
                cachedBaseInputStack = ItemStack.EMPTY;
                cachedVoidInputStack = ItemStack.EMPTY;
            }
        } else if (slot == VOID_INPUT_SLOT) {
            ItemStack oldStack = inventory.get(VOID_INPUT_SLOT);
            // Only invalidate if the item or components actually changed
            if (!ItemStack.areItemsAndComponentsEqual(oldStack, stack)) {
                cachedRecipe = null;
                cachedBaseInputStack = ItemStack.EMPTY;
                cachedVoidInputStack = ItemStack.EMPTY;
            }
        }

        inventory.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return pos.isWithinDistance(player.getEntityPos(), 8.0);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    // NamedScreenHandlerFactory implementation
    @Override
    public Text getDisplayName() {
        return Text.translatable("container.enderite.void_infusion_table");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new VoidInfusionTableScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
}

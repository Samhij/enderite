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
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class VoidInfusionTableBlockEntity extends BlockEntity implements SidedInventory, NamedScreenHandlerFactory {
    private static final int INPUT_SLOT = 0;
    private static final int FUEL_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private static final int INFUSION_TIME = 12000; // 10 minutes (12000 ticks)

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    private int progress = 0;
    private int fuelRemaining = 0;
    private int fuelTime = 0; // Max burn time for current fuel
    
    // Cache for recipe lookup optimization
    private RecipeEntry<VoidInfusionRecipe> cachedRecipe = null;
    private ItemStack cachedInputStack = ItemStack.EMPTY;

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
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("Progress", progress);
        nbt.putInt("FuelRemaining", fuelRemaining);
        nbt.putInt("FuelTime", fuelTime);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt("Progress");
        fuelRemaining = nbt.getInt("FuelRemaining");
        fuelTime = nbt.getInt("FuelTime");
    }

    /**
     * Gets the cached recipe for the current input stack, or performs a lookup if the cache is invalid.
     * This method significantly improves performance by avoiding repeated recipe manager queries.
     * 
     * Note: Stack count is not considered for cache validation because VoidInfusionRecipe matching
     * only depends on item type and components, not quantity (as per SingleStackRecipeInput behavior).
     */
    private RecipeEntry<VoidInfusionRecipe> getCachedRecipe() {
        if (world == null || !(world instanceof ServerWorld)) {
            return null;
        }

        ItemStack inputStack = inventory.get(INPUT_SLOT);

        // Check if cache is still valid (item type and components must match)
        if (cachedRecipe != null && ItemStack.areItemsAndComponentsEqual(cachedInputStack, inputStack)) {
            return cachedRecipe;
        }

        // Cache is invalid, perform lookup and update cache
        if (inputStack.isEmpty()) {
            cachedRecipe = null;
            cachedInputStack = ItemStack.EMPTY;
            return null;
        }

        SingleStackRecipeInput recipeInput = new SingleStackRecipeInput(inputStack);
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

        cachedInputStack = inputStack.copy();

        return cachedRecipe;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient) {
            return;
        }

        boolean dirty = false;

        // Check if we have valid inputs
        ItemStack inputStack = inventory.get(INPUT_SLOT);
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
                        inputStack.decrement(1);

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

            // Add some dragon breath particles
            if (world.random.nextInt(3) == 0) {
                double offsetX = (world.random.nextDouble() - 0.5) * 0.3;
                double offsetZ = (world.random.nextDouble() - 0.5) * 0.3;

                serverWorld.spawnParticles(
                        ParticleTypes.DRAGON_BREATH,
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
            return new int[]{INPUT_SLOT};
        } else {
            return new int[]{FUEL_SLOT};
        }
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        if (slot == INPUT_SLOT) {
            // Check if there's a recipe for this item using optimized lookup
            if (world instanceof ServerWorld && !stack.isEmpty()) {
                SingleStackRecipeInput recipeInput = new SingleStackRecipeInput(stack);
                ServerRecipeManager recipeManager = ((ServerWorld) world).getRecipeManager();
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
        // Invalidate cache when input slot changes to a different item
        if (slot == INPUT_SLOT) {
            ItemStack oldStack = inventory.get(INPUT_SLOT);
            // Only invalidate if the item or components actually changed
            if (!ItemStack.areItemsAndComponentsEqual(oldStack, stack)) {
                cachedRecipe = null;
                cachedInputStack = ItemStack.EMPTY;
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
        return pos.isWithinDistance(player.getPos(), 8.0);
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

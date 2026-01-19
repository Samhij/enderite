package net.lonk.enderite.screen;

import net.lonk.enderite.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class VoidInfusionTableScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public static final int VOID_INPUT_SLOT = 0;  // Left slot (added first at x=26)
    public static final int BASE_INPUT_SLOT = 1;  // Middle/right slot (added second at x=56)
    public static final int FUEL_SLOT = 2;
    public static final int OUTPUT_SLOT = 3;

    public VoidInfusionTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(4), new ArrayPropertyDelegate(4));
    }

    public VoidInfusionTableScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.VOID_INFUSION_TABLE_SCREEN_HANDLER, syncId);
        checkSize(inventory, 4);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        inventory.onOpen(playerInventory.player);

        // Void ingredient slot - left slot for void-infused ingot
        this.addSlot(new Slot(inventory, VOID_INPUT_SLOT, 26, 17));

        // Base input slot - middle slot, accepts any item with a recipe
        this.addSlot(new Slot(inventory, BASE_INPUT_SLOT, 56, 17));

        // Fuel slot (dragon's breath) - bottom slot like furnace fuel
        this.addSlot(new Slot(inventory, FUEL_SLOT, 56, 53) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.DRAGON_BREATH);
            }
        });

        // Output slot - right side like furnace output
        this.addSlot(new Slot(inventory, OUTPUT_SLOT, 116, 35) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        this.addProperties(propertyDelegate);
    }

    public int getProgress() {
        return propertyDelegate.get(0);
    }

    public int getMaxProgress() {
        return propertyDelegate.get(1);
    }

    public int getFuelRemaining() {
        return propertyDelegate.get(2);
    }

    public int getMaxFuel() {
        return propertyDelegate.get(3);
    }

    public boolean isBurning() {
        return getFuelRemaining() > 0;
    }

    public float getInfusionProgress() {
        int progress = this.getProgress();
        int maxProgress = this.getMaxProgress();
        return maxProgress != 0 && progress != 0 ? net.minecraft.util.math.MathHelper.clamp((float)progress / (float)maxProgress, 0.0F, 1.0F) : 0.0F;
    }

    public float getFuelProgress() {
        int fuelRemaining = this.getFuelRemaining();
        int maxFuel = this.getMaxFuel();

        // If maxFuel is 0 but we have fuel remaining, use fuel remaining as max
        // This handles the case where old block entities don't have fuelTime saved
        if (maxFuel == 0 && fuelRemaining > 0) {
            return 1.0F; // Show full fuel bar
        }

        if (maxFuel == 0) {
            return 0.0F;
        }

        return net.minecraft.util.math.MathHelper.clamp((float)fuelRemaining / (float)maxFuel, 0.0F, 1.0F);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if (invSlot < this.inventory.size()) {
                // Moving from machine to player inventory
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Moving from player inventory to machine
                boolean inserted = false;

                if (originalStack.isOf(Items.DRAGON_BREATH)) {
                    // Dragon's breath goes to fuel slot
                    inserted = this.insertItem(originalStack, FUEL_SLOT, FUEL_SLOT + 1, false);
                } else if (originalStack.isOf(ModItems.VOID_INFUSED_INGOT)) {
                    // Void-infused ingot ALWAYS goes to void ingredient slot first
                    inserted = this.insertItem(originalStack, VOID_INPUT_SLOT, VOID_INPUT_SLOT + 1, false);
                } else {
                    // Try to put any other item in the base input slot (it will be validated by the block entity)
                    inserted = this.insertItem(originalStack, BASE_INPUT_SLOT, BASE_INPUT_SLOT + 1, false);
                }

                // If we couldn't insert into the machine, move between main inventory and hotbar
                if (!inserted) {
                    if (invSlot < this.inventory.size() + 27) {
                        if (!this.insertItem(originalStack, this.inventory.size() + 27, this.slots.size(), false)) {
                            return ItemStack.EMPTY;
                        }
                    } else {
                        if (!this.insertItem(originalStack, this.inventory.size(), this.inventory.size() + 27, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}

package net.lonk.enderite.item.custom;

import net.lonk.enderite.world.dimension.ModDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ChorusSingularityItem extends Item {
    public ChorusSingularityItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack resultStack = super.finishUsing(stack, world, user);

        if (user instanceof PlayerEntity player && !world.isClient()) {
            if (player.getWorld().getRegistryKey() == ModDimensions.THE_VOID_LEVEL_KEY) {
                ServerWorld overworld = player.getServer().getOverworld();
                player.teleport(overworld, player.getX(), 250, player.getY(), Set.of(), player.getYaw(), player.getPitch(), false);
                world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.PLAYERS, 1f, 2f);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 100, 0));
                scrambleInventory(player);
                putInRandomSlot(player, new ItemStack(Items.CHORUS_FRUIT));
                player.sendMessage(Text.literal("If you want to survive, check your inventory"), true);
            }
        }

        return resultStack;
    }

    private void scrambleInventory(PlayerEntity player) {
        PlayerInventory inventory = player.getInventory();
        List<ItemStack> stacks = new ArrayList<>();

        // 1. Collect all items from the main inventory (0-35)
        // We skip armor (36-39) and offhand (40) so the player doesn't
        // suddenly lose their boots or shield mid-teleport.
        for (int i = 0; i < 36; i++) {
            stacks.add(inventory.getStack(i).copy());
        }

        // 2. Shuffle the list
        Collections.shuffle(stacks);

        // 3. Put them back into the inventory
        for (int i = 0; i < 36; i++) {
            inventory.setStack(i, stacks.get(i));
        }

        // 4. Mark inventory as dirty so the client syncs the changes
        inventory.markDirty();
    }

    private void putInRandomSlot(PlayerEntity player, ItemStack stack) {
        PlayerInventory inventory = player.getInventory();
        var random = player.getWorld().getRandom();

        // Try up to 10 times to find a random empty slot
        for (int i = 0; i < 10; i++) {
            int randomSlot = random.nextInt(36);
            if (inventory.getStack(randomSlot).isEmpty()) {
                inventory.setStack(randomSlot, stack);
                return;
            }
        }

        // If no empty slot was found after 10 tries, just give it normally
        // (This will try to stack it or drop it if the inventory is full)
        player.giveItemStack(stack);
    }
}

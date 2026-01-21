package net.lonk.enderite.effect;

import net.lonk.enderite.Enderite;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public record TeleportFromVoidConsumeEffect() implements ConsumeEffect {
    @Override
    public Type<? extends ConsumeEffect> getType() {
        return Type.TELEPORT_RANDOMLY;
    }

    @Override
    public boolean onConsume(World world, ItemStack stack, LivingEntity user) {
        if (user instanceof PlayerEntity player && !world.isClient()) { // Ensure server-side logic
            if (player.getEntityWorld().getRegistryKey() == RegistryKey.of(RegistryKeys.WORLD, Identifier.of(Enderite.MOD_ID, "the_void"))) {
                ServerWorld overworld = player.getEntityWorld().getServer().getOverworld();

                // 1. Teleport first
                player.teleport(overworld, player.getX(), 250, player.getY(), Set.of(), player.getYaw(), player.getPitch(), false);

                // 2. Add effects and sounds
                world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.PLAYERS, 1f, 2f);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 100, 0));

                // 3. Scramble inventory
                scrambleInventory(player);

                // 4. Add the survival item (Chorus Fruit)
                putInRandomSlot(player, new ItemStack(Items.CHORUS_FRUIT));

                // 5. Send the warning
                player.sendMessage(Text.literal("If you want to survive, check your inventory"), true);

                return true;
            } else {
                player.sendMessage(Text.literal("It seems this is not the right dimension to be using this item."), true);
                return false;
            }
        }
        return false;
    }

    public void scrambleInventory(PlayerEntity player) {
        // 1. Get the main inventory list (36 slots: 9 hotbar + 27 main)
        var inventory = player.getInventory().getMainStacks();

        // 2. Copy current items to a temporary list
        List<ItemStack> items = new ArrayList<>(inventory);

        // 3. Shuffle the list
        Collections.shuffle(items);

        // 4. Put shuffled items back into the actual inventory
        for (int i = 0; i < inventory.size(); i++) {
            inventory.set(i, items.get(i));
        }

        // 5. IMPORTANT: Sync changes if this is on the server
        player.getInventory().markDirty();
    }

    private void putInRandomSlot(PlayerEntity player, ItemStack stack) {
        PlayerInventory inventory = player.getInventory();
        var random = player.getEntityWorld().getRandom();

        // Try up to 10 times to find a random empty slot
        for (int i = 0; i < 10; i++) {
            int randomSlot = random.nextInt(36);
            if (inventory.getStack(randomSlot).isEmpty()) {
                inventory.setStack(randomSlot, stack);
                inventory.markDirty();
                return;
            }
        }

        // If no empty slot was found after 10 tries, just give it normally
        // (This will try to stack it or drop it if the inventory is full)
        player.giveItemStack(stack);
    }
}

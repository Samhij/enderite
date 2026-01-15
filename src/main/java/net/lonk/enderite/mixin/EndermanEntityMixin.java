package net.lonk.enderite.mixin;

import net.lonk.enderite.item.ModItems;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(EndermanEntity.class)
public abstract class EndermanEntityMixin {
    // Pre-computed set for O(1) lookup instead of multiple comparisons
    @Unique
    private static final Set<Item> ENDERITE_ARMOR_ITEMS = Set.of(
            ModItems.ENDERITE_HELMET,
            ModItems.ENDERITE_CHESTPLATE,
            ModItems.ENDERITE_LEGGINGS,
            ModItems.ENDERITE_BOOTS
    );

    @Unique
    private static final Set<Item> VOID_INFUSED_ARMOR_ITEMS = Set.of(
            ModItems.VOID_INFUSED_HELMET,
            ModItems.VOID_INFUSED_CHESTPLATE,
            ModItems.VOID_INFUSED_LEGGINGS,
            ModItems.VOID_INFUSED_BOOTS
    );

    @Inject(method = "isPlayerStaring", at = @At("HEAD"), cancellable = true)
    private void preventAggroWithEnderiteArmor(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (isWearingFullEnderiteArmor(player)) {
            cir.setReturnValue(false);
        }
    }

    @Unique
    private boolean isWearingFullEnderiteArmor(PlayerEntity player) {
        Iterable<ItemStack> armor = player.getArmorItems();
        int enderiteCount = 0;

        // Optimized: Use Set.contains() for O(1) lookup instead of multiple equality checks
        for (ItemStack stack : armor) {
            if (ENDERITE_ARMOR_ITEMS.contains(stack.getItem()) || VOID_INFUSED_ARMOR_ITEMS.contains(stack.getItem())) {
                enderiteCount++;
            }
        }

        return enderiteCount == 4;
    }
}

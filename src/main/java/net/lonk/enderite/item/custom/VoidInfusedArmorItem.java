package net.lonk.enderite.item.custom;

import net.lonk.enderite.item.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class VoidInfusedArmorItem extends ArmorItem {
    public VoidInfusedArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient()) {
            applyVanishingCurse(stack, world);
            applyLuckEffectForHelmet(entity);
            applyWeavingEffectForBoots(entity);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void applyVanishingCurse(ItemStack stack, World world) {
        RegistryEntry<Enchantment> vanishingCurse = world.getRegistryManager()
                .getWrapperOrThrow(RegistryKeys.ENCHANTMENT)
                .getOrThrow(Enchantments.VANISHING_CURSE);

        if (EnchantmentHelper.getLevel(vanishingCurse, stack) == 0) {
            stack.addEnchantment(vanishingCurse, 1);
        }
    }

    private void applyLuckEffectForHelmet(Entity entity) {
        if (entity instanceof ServerPlayerEntity player) {
            if (player.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.VOID_INFUSED_HELMET)) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS));
            }
        }
    }

    private void applyWeavingEffectForBoots(Entity entity) {
        if (entity instanceof ServerPlayerEntity player) {
            if (player.getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.VOID_INFUSED_BOOTS)) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAVING));
            }
        }
    }
}

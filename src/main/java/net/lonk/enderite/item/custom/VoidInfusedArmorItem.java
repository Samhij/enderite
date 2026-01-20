package net.lonk.enderite.item.custom;

import net.lonk.enderite.item.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class VoidInfusedArmorItem extends ArmorItem {
    public VoidInfusedArmorItem(ArmorMaterial material, EquipmentType type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        applyVanishingCurse(stack, world);
        applyLuckEffectForHelmet(entity);
        applyWeavingEffectForBoots(entity);
    }

    private void applyVanishingCurse(ItemStack stack, World world) {
        RegistryEntry<Enchantment> vanishingCurse = world.getRegistryManager()
                .getOrThrow(RegistryKeys.ENCHANTMENT)
                .getOrThrow(Enchantments.VANISHING_CURSE);

        if (EnchantmentHelper.getLevel(vanishingCurse, stack) == 0) {
            stack.addEnchantment(vanishingCurse, 1);
        }
    }

    private void applyLuckEffectForHelmet(Entity entity) {
        if (entity instanceof ServerPlayerEntity player) {
            if (hasEquipped(player, ModItems.VOID_INFUSED_HELMET)) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS));
            }
        }
    }

    private void applyWeavingEffectForBoots(Entity entity) {
        if (entity instanceof ServerPlayerEntity player) {
            if (hasEquipped(player, ModItems.VOID_INFUSED_BOOTS)) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAVING));
            }
        }
    }

    private boolean hasEquipped(ServerPlayerEntity player, Item item) {
        var armor = player.getInventory().armor;
        return armor.get(0).getItem() == item || armor.get(1).getItem() == item || armor.get(2).getItem() == item || armor.get(3).getItem() == item;
    }
}

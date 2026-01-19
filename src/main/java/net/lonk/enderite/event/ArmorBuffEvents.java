package net.lonk.enderite.event;

import net.lonk.enderite.item.ModItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ArmorBuffEvents {
    public static boolean enderiteLeggings(LivingEntity livingEntity, DamageSource damageSource, float v) {
        if (!(livingEntity instanceof ServerPlayerEntity player)) return true;
        float chance = 0.0f;

        if (player.getEquippedStack(EquipmentSlot.LEGS).isOf(ModItems.ENDERITE_LEGGINGS)) {
            chance = 0.08f;
        } else if (player.getEquippedStack(EquipmentSlot.LEGS).isOf(ModItems.VOID_INFUSED_LEGGINGS)) {
            chance = 0.12f;
        }

        if (damageSource.isOf(DamageTypes.ARROW) || damageSource.isOf(DamageTypes.MOB_PROJECTILE)) {
            Random random = player.getRandom();

            if (random.nextFloat() < chance) {
                return false;
            }
        }

        return true;
    }
}

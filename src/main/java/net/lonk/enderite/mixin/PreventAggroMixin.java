package net.lonk.enderite.mixin;

import net.lonk.enderite.item.ModItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class PreventAggroMixin {
    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    private void preventAggro(LivingEntity target, CallbackInfo ci) {
        // Early Return: If the target isn't a player, ignore
        if (!(target instanceof PlayerEntity player)) return;

        // This is the entity (Phantom, Enderman, etc.)
        MobEntity mob = (MobEntity) (Object) this;

        // Enderman Logic (Enderite & Void-Infused Helmet)
        if (mob instanceof EndermanEntity) {
            if (player.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.ENDERITE_HELMET) || player.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.VOID_INFUSED_HELMET)) {
                ci.cancel();
            }
        }

        // Phantom Logic (Void-Infused Chestplate)
        if (mob instanceof PhantomEntity) {
            if (player.getEquippedStack(EquipmentSlot.BODY).isOf(ModItems.VOID_INFUSED_CHESTPLATE)) {
                ci.cancel();
            }
        }
    }
}

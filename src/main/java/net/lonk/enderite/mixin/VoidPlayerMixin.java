package net.lonk.enderite.mixin;

import net.lonk.enderite.Enderite;
import net.lonk.enderite.item.ModItems;
import net.lonk.enderite.world.dimension.ModDimensions;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(LivingEntity.class)
public abstract class VoidPlayerMixin {
    @Inject(method = "getSafeFallDistance()I", at = @At("HEAD"), cancellable = true)
    private void increaseSafeFallDistance(CallbackInfoReturnable<Integer> cir) {
        if ((Object) this instanceof PlayerEntity player) {
            if (player.getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.ENDERITE_BOOTS)
                    || player.getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.VOID_INFUSED_BOOTS)) {
                cir.setReturnValue(5);
            }
        }
    }

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    private void cancelVoidFallDamage(double fallDistance, float damagePerDistance, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity instanceof ServerPlayerEntity player) {
            if (player.getWorld().getRegistryKey() == ModDimensions.THE_VOID) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void handleVoidTick(CallbackInfo ci) {
        if ((Object) this instanceof ServerPlayerEntity player) {
            if (player.getY() < -100 && player.getWorld().getRegistryKey() != World.END) {
                if (player.getWorld().getRegistryKey() == ModDimensions.THE_VOID) {
                    grantAdvancement(player, "nice_try", "player_jumped_in_void");
                }

                ServerWorld voidWorld = player.getServer().getWorld(ModDimensions.THE_VOID);
                if (voidWorld != null) {
                    player.teleport(voidWorld, player.getX(), 500, player.getZ(), Set.of(), player.getYaw(), player.getPitch(), false);
                }
            }
        }
    }

    @Unique
    private void grantAdvancement(ServerPlayerEntity player, String name, String criterion) {
        var advancement = player.getServer().getAdvancementLoader().get(Identifier.of(Enderite.MOD_ID, name));
        if (advancement != null) {
            player.getAdvancementTracker().grantCriterion(advancement, criterion);
        }
    }
}

package net.lonk.enderite.mixin;

import net.lonk.enderite.Enderite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(Entity.class)
public abstract class VoidTeleportMixin {
    @Shadow
    public abstract double getY();

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;

        // Only trigger for players on the server side in the End dimension
        if (entity instanceof ServerPlayerEntity player) {
            // Check if player is in the End
            if (!player.getWorld().getDimensionEntry().matchesKey(DimensionTypes.THE_END)) {
                if (this.getY() < -100) {
                    RegistryKey<World> voidWorldKey = RegistryKey.of(RegistryKeys.WORLD, Identifier.of(Enderite.MOD_ID, "the_void"));
                    ServerWorld voidWorld = player.getServer().getWorld(voidWorldKey);

                    if (voidWorld != null) {
                        // Teleport to the Void dimension, starting at the top (Y 250)
                        player.teleport(voidWorld, player.getX(), 250, player.getZ(), Set.of(), player.getYaw(), player.getPitch(), false);
                    }
                }
            }
        }
    }
}

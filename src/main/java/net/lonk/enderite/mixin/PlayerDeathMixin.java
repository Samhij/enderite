package net.lonk.enderite.mixin;

import net.lonk.enderite.Enderite;
import net.lonk.enderite.world.dimension.ModDimensions;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class PlayerDeathMixin {
    @Inject(method = "onDeath", at = @At("HEAD"))
    private void onPlayerDeath(DamageSource damageSource, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        // Check if player is in the void dimension
        if (player.getWorld().getRegistryKey().equals(ModDimensions.THE_VOID)) {
            // Grant the advancement
            AdvancementEntry advancement = player.getServer().getAdvancementLoader()
                .get(Identifier.of(Enderite.MOD_ID, "die_in_void"));

            if (advancement != null) {
                player.getAdvancementTracker().grantCriterion(advancement, "player_died");
            }
        }
    }
}

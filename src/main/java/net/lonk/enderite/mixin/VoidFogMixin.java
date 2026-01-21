package net.lonk.enderite.mixin;

import net.lonk.enderite.Enderite;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.fog.DimensionOrBossFogModifier;
import net.minecraft.client.render.fog.FogData;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DimensionOrBossFogModifier.class)
public abstract class VoidFogMixin {
    @Inject(method = "applyStartEndModifier", at = @At("HEAD"), cancellable = true)
    private void forceVoidFog(FogData fogData, Entity entity, BlockPos pos, ClientWorld world, float viewDistance, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (entity instanceof ClientPlayerEntity player) {
            if (player.getWorld().getRegistryKey() == RegistryKey.of(RegistryKeys.WORLD, Identifier.of(Enderite.MOD_ID, "the_void"))) {
                // Apply thick void fog effect
                fogData.environmentalStart = 0.0f;
                fogData.environmentalEnd = 128.0f;
                ci.cancel();
            }
        }
    }
}

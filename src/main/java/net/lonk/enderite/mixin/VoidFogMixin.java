package net.lonk.enderite.mixin;

import net.lonk.enderite.Enderite;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DimensionOrBossFogModifier.class)
public abstract class VoidFogMixin {
    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void forceVoidFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        Entity entity = camera.getFocusedEntity();

        if (entity instanceof ClientPlayerEntity player) {
            if (player.getWorld().getRegistryKey() == RegistryKey.of(RegistryKeys.WORLD, Identifier.of(Enderite.MOD_ID, "the_void"))) {
                RenderSystem.setShaderFogStart(0.0f);
                RenderSystem.setShaderFogEnd(128.0f);
                ci.cancel();
            }
        }
    }
}

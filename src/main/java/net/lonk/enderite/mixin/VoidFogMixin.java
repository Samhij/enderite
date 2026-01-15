package net.lonk.enderite.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.lonk.enderite.world.dimension.ModDimensions;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Fog;
import net.minecraft.client.render.FogShape;
import net.minecraft.entity.Entity;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BackgroundRenderer.class)
public abstract class VoidFogMixin {
    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void forceVoidFog(Camera camera, BackgroundRenderer.FogType fogType, Vector4f color, float viewDistance, boolean thickenFog, float tickDelta, CallbackInfoReturnable<Fog> cir) {
        Entity entity = camera.getFocusedEntity();

        if (entity instanceof ClientPlayerEntity player) {
            if (player.getWorld().getRegistryKey() == ModDimensions.THE_VOID_WORLD_KEY) {
                Fog thickVoidFog = new Fog(0.0f, 128.0f, FogShape.SPHERE, color.x, color.y, color.z, color.w);
                RenderSystem.setShaderFog(thickVoidFog);
                cir.setReturnValue(thickVoidFog);
            }
        }
    }
}

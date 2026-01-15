package net.lonk.enderite.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @ModifyVariable(
            method = "renderStatusBars",
            at = @At(value = "STORE"),
            ordinal = 1
    )
    private int modifyArmorValue(int armor) {
        // Scale armor: 25 points -> 10 icons (20 points)
        // This makes each icon represent 2.5 armor points instead of 2
        if (armor > 20) {
            return (int) Math.ceil(armor * 20.0 / 25.0);
        }
        return armor;
    }
}

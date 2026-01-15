package net.lonk.enderite.mixin;

import net.lonk.enderite.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class BedrockBreakerMixin {
    @Inject(method = "calcBlockBreakingDelta", at = @At("HEAD"), cancellable = true)
    private void allowBedrockBreaking(PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        AbstractBlock.AbstractBlockState state = (AbstractBlock.AbstractBlockState) (Object) this;

        // Check if the block being looked at is Bedrock
        if (state.isOf(Blocks.BEDROCK)) {
            ItemStack stack = player.getMainHandStack();

            // Check if player is holding Void-Infused Pickaxe
            if (stack.isOf(ModItems.VOID_INFUSED_PICKAXE)) {
                cir.setReturnValue(0.01f);
            }
        }
    }
}

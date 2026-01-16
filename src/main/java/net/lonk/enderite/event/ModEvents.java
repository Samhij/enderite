package net.lonk.enderite.event;

import net.lonk.enderite.world.dimension.ModDimensions;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BedItem;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class ModEvents {

    public static ActionResult sendBedMessage(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (world.isClient() && player.getStackInHand(hand).getItem() instanceof BedItem) {
            if (world.getRegistryKey() == ModDimensions.THE_VOID) {
                player.sendMessage(Text.literal("Don't even THINK about sleeping in that bed"), true);
            }
        }

        return ActionResult.PASS;
    }

    public static ActionResult explodeBed(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (!world.isClient) {
            BlockState state = world.getBlockState(hitResult.getBlockPos());

            if (state.getBlock() instanceof BedBlock) {
                if (world.getRegistryKey() == ModDimensions.THE_VOID) {
                    world.removeBlock(hitResult.getBlockPos(), false);

                    world.createExplosion(
                            null,
                            hitResult.getBlockPos().getX() + 0.5,
                            hitResult.getBlockPos().getY() + 0.5,
                            hitResult.getBlockPos().getZ() + 0.5,
                            30.0f,
                            true,
                            World.ExplosionSourceType.BLOCK
                    );

                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }
}

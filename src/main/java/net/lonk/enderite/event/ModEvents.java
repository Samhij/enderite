package net.lonk.enderite.event;

import net.lonk.enderite.world.dimension.ModDimensions;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BedItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class ModEvents {
    private static int tntRainTimer = 0;
    private static final int TNT_RAIN_INTERVAL = 200; // 10 seconds (20 ticks per second)

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

    public static void onServerTick(ServerWorld world) {
        // Only run TNT rain in the void dimension
        if (world.getRegistryKey() != ModDimensions.THE_VOID) {
            return;
        }

        tntRainTimer++;

        // Spawn TNT every TNT_RAIN_INTERVAL ticks
        if (tntRainTimer >= TNT_RAIN_INTERVAL) {
            tntRainTimer = 0;

            // For each player in the void dimension, spawn TNT above them
            for (ServerPlayerEntity player : world.getPlayers()) {
                // Spawn 3-5 TNT entities at random positions around the player
                int tntCount = 3 + world.random.nextInt(3);

                for (int i = 0; i < tntCount; i++) {
                    // Random position around the player (within 20 blocks horizontally)
                    double offsetX = (world.random.nextDouble() - 0.5) * 40;
                    double offsetZ = (world.random.nextDouble() - 0.5) * 40;

                    // Spawn TNT 50 blocks above the player
                    double x = player.getX() + offsetX;
                    double y = player.getY() + 50;
                    double z = player.getZ() + offsetZ;

                    TntEntity tnt = new TntEntity(world, x, y, z, null);
                    tnt.setFuse(80); // 4 seconds fuse
                    world.spawnEntity(tnt);
                }
            }
        }
    }
}

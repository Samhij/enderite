package net.lonk.enderite.mixin;

import net.lonk.enderite.block.ModBlocks;
import net.lonk.enderite.entity.custom.EnderiteGolemEntity;
import net.lonk.enderite.entity.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.SpawnReason;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CarvedPumpkinBlock.class)
public abstract class SpawnEnderiteGolemMixin {
    @Shadow
    public static void breakPatternBlocks(World world, BlockPattern.Result patternResult) {}

    @Unique
    private BlockPattern enderiteGolemPattern;

    @Unique
    private BlockPattern getEnderiteGolemPattern() {
        if (this.enderiteGolemPattern == null) {
            this.enderiteGolemPattern = BlockPatternBuilder.start()
                    .aisle("~^~", "###", "~#~")
                    .where('^', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.CARVED_PUMPKIN)))
                    .where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(ModBlocks.ENDERITE_BLOCK)))
                    .where('~', pos -> pos.getBlockState().isAir())
                    .build();
        }
        return this.enderiteGolemPattern;
    }

    @Inject(method = "onBlockAdded", at = @At("TAIL"))
    private void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (!world.isClient) {
            trySpawnEnderiteGolem(world, pos);
        }
    }

    @Unique
    private void trySpawnEnderiteGolem(World world, BlockPos pos) {
        BlockPattern.Result result = this.getEnderiteGolemPattern().searchAround(world, pos);
        if (result != null) {
            EnderiteGolemEntity golem = ModEntities.ENDERITE_GOLEM.create(world);
            if (golem != null) {
                breakPatternBlocks(world, result);
                BlockPos spawnPos = result.translate(0, 2, 0).getBlockPos();
                golem.refreshPositionAndAngles(
                        (double) spawnPos.getX() + 0.5,
                        (double) spawnPos.getY() + 0.05,
                        (double) spawnPos.getZ() + 0.5,
                        0.0F, 0.0F
                );
                golem.setBodyYaw(getHorizontalFacing(result).getRotationQuaternion().y);
                world.spawnEntity(golem);

                for (int i = 0; i < 120; i++) {
                    world.addParticle(
                            net.minecraft.particle.ParticleTypes.SNOWFLAKE,
                            (double) spawnPos.getX() + world.random.nextDouble(),
                            (double) spawnPos.getY() + world.random.nextDouble() * 2.5,
                            (double) spawnPos.getZ() + world.random.nextDouble(),
                            0.0, 0.0, 0.0
                    );
                }

                for (int x = -2; x <= 2; x++) {
                    for (int y = -2; y <= 2; y++) {
                        for (int z = -2; z <= 2; z++) {
                            world.updateNeighbors(spawnPos.add(x, y, z), Blocks.AIR);
                        }
                    }
                }
            }
        }
    }

    @Unique
    private static Direction getHorizontalFacing(BlockPattern.Result result) {
        return result.getForwards();
    }
}

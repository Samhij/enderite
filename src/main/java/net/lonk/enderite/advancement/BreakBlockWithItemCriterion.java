package net.lonk.enderite.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class BreakBlockWithItemCriterion extends AbstractCriterion<BreakBlockWithItemCriterion.Conditions> {
    @Override
    public Codec<Conditions> getConditionsCodec() {
        return Conditions.CODEC;
    }

    public void trigger(ServerPlayerEntity player, BlockPos pos, ItemStack stack) {
        ServerWorld world = player.getServerWorld();
        this.trigger(player, conditions -> conditions.matches(world, pos, stack));
    }

    public record Conditions(Optional<LootContextPredicate> player, Optional<ItemPredicate> item, Optional<BlockPredicate> block) implements AbstractCriterion.Conditions {
        public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
                ItemPredicate.CODEC.optionalFieldOf("item").forGetter(Conditions::item),
                BlockPredicate.CODEC.optionalFieldOf("block").forGetter(Conditions::block)
        ).apply(instance, Conditions::new));

        public boolean matches(ServerWorld world, BlockPos pos, ItemStack stack) {
            // 1. Check Item
            if (this.item.isPresent() && !this.item.get().test(stack)) {
                return false;
            }

            // 2. Check Block
            if (this.block.isPresent()) {
                // Create the CachedBlockPosition required by 1.21.1
                CachedBlockPosition cachedPos = new CachedBlockPosition(world, pos, true);
                if (!this.block.get().test(cachedPos)) {
                    return false;
                }
            }

            return true;
        }
    }
}

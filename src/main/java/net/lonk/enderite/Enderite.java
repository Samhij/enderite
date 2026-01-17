package net.lonk.enderite;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.lonk.enderite.advancement.BreakBlockWithItemCriterion;
import net.lonk.enderite.block.ModBlockEntities;
import net.lonk.enderite.block.ModBlocks;
import net.lonk.enderite.entity.custom.EnderiteGolemEntity;
import net.lonk.enderite.entity.ModEntities;
import net.lonk.enderite.event.ModEvents;
import net.lonk.enderite.item.ModItemGroups;
import net.lonk.enderite.item.ModItems;
import net.lonk.enderite.recipe.ModRecipeTypes;
import net.lonk.enderite.screen.ModScreenHandlers;
import net.lonk.enderite.util.ModLootTableModifiers;
import net.lonk.enderite.world.gen.ModWorldGeneration;
import net.minecraft.advancement.criterion.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Enderite implements ModInitializer {
    public static final String MOD_ID = "enderite";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final BreakBlockWithItemCriterion BREAK_BLOCK_WITH_ITEM = Criteria.register("enderite:break_block_with_item", new BreakBlockWithItemCriterion());

    @Override
    public void onInitialize() {
        ModItemGroups.init();
        ModItems.init();

        ModBlocks.init();
        ModBlockEntities.init();

        ModScreenHandlers.init();
        ModRecipeTypes.init();

        ModWorldGeneration.generateWorldGeneration();
        ModLootTableModifiers.modifyLootTables();

        ModEntities.init();
        FabricDefaultAttributeRegistry.register(ModEntities.ENDERITE_GOLEM, EnderiteGolemEntity.createEnderiteGolemAttributes());

        registerEvents();
    }

    private void registerEvents() {
        UseBlockCallback.EVENT.register(ModEvents::sendBedMessage);
        UseBlockCallback.EVENT.register(ModEvents::explodeBed);
        ServerTickEvents.END_WORLD_TICK.register(ModEvents::onServerTick);
    }
}

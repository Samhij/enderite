package net.lonk.enderite.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.lonk.enderite.Enderite;
import net.lonk.enderite.advancement.BreakBlockWithItemCriterion;
import net.lonk.enderite.item.ModItems;
import net.lonk.enderite.util.ModTags;
import net.lonk.enderite.world.dimension.ModDimensions;
import net.minecraft.advancement.*;
import net.lonk.enderite.block.ModBlocks;
import net.minecraft.advancement.criterion.*;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.*;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureKeys;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends FabricAdvancementProvider {
    public ModAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup wrapperLookup, Consumer<AdvancementEntry> consumer) {
        final Identifier BACKGROUND_TEXTURE = Identifier.ofVanilla("textures/gui/advancements/backgrounds/end.png");
        final Identifier BACKGROUND_TEXTURE_VOID = Identifier.ofVanilla("textures/block/sculk_sensor_bottom.png");

        var structureLookup = wrapperLookup.getWrapperOrThrow(RegistryKeys.STRUCTURE);

        // region Root & Enderite Basics

        AdvancementEntry root = Advancement.Builder.create()
                .display(
                        ModBlocks.ENDERITE_ORE,
                        Text.translatable("advancements.enderite.root.title"),
                        Text.translatable("advancements.enderite.root.description"),
                        BACKGROUND_TEXTURE,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("raw_enderite", InventoryChangedCriterion.Conditions.items(ModItems.RAW_ENDERITE))
                .build(consumer, "enderite:root");

        AdvancementEntry echoesOfPower = Advancement.Builder.create()
                .display(
                        ModItems.ENDERITE_INGOT,
                        Text.translatable("advancements.enderite.echoes_of_power.title"),
                        Text.translatable("advancements.enderite.echoes_of_power.description"),
                        BACKGROUND_TEXTURE,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                ).parent(root)
                .criterion("enderite_ingot", InventoryChangedCriterion.Conditions.items(ModItems.ENDERITE_INGOT))
                .build(consumer, "enderite:echoes_of_power");

        AdvancementEntry cityTreasure = Advancement.Builder.create()
                .display(
                        ModItems.ENDERITE_UPGRADE_SMITHING_TEMPLATE,
                        Text.translatable("advancements.enderite.city_treasure.title"),
                        Text.translatable("advancements.enderite.city_treasure.description"),
                        BACKGROUND_TEXTURE,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                ).parent(echoesOfPower)
                .criterion("enderite_upgrade_smithing_template", InventoryChangedCriterion.Conditions.items(ModItems.ENDERITE_UPGRADE_SMITHING_TEMPLATE))
                .build(consumer, "enderite:city_treasure");

        // endregion

        // region Enderite Armor

        AdvancementEntry upgradedAgain = Advancement.Builder.create()
                .display(
                        ModItems.ENDERITE_CHESTPLATE,
                        Text.translatable("advancements.enderite.upgraded_again.title"),
                        Text.translatable("advancements.enderite.upgraded_again.description"),
                        BACKGROUND_TEXTURE,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                ).parent(cityTreasure)
                .criterion("enderite_helmet", InventoryChangedCriterion.Conditions.items(ModItems.ENDERITE_HELMET))
                .criterion("enderite_chestplate", InventoryChangedCriterion.Conditions.items(ModItems.ENDERITE_CHESTPLATE))
                .criterion("enderite_leggings", InventoryChangedCriterion.Conditions.items(ModItems.ENDERITE_LEGGINGS))
                .criterion("enderite_boots", InventoryChangedCriterion.Conditions.items(ModItems.ENDERITE_BOOTS))
                .criterion("enderite_sword", InventoryChangedCriterion.Conditions.items(ModItems.ENDERITE_SWORD))
                .criterion("enderite_pickaxe", InventoryChangedCriterion.Conditions.items(ModItems.ENDERITE_PICKAXE))
                .criterion("enderite_axe", InventoryChangedCriterion.Conditions.items(ModItems.ENDERITE_AXE))
                .criterion("enderite_shovel", InventoryChangedCriterion.Conditions.items(ModItems.ENDERITE_SHOVEL))
                .criterion("enderite_hoe", InventoryChangedCriterion.Conditions.items(ModItems.ENDERITE_HOE))
                .requirements(AdvancementRequirements.anyOf(List.of(
                        "enderite_helmet",
                        "enderite_chestplate",
                        "enderite_leggings",
                        "enderite_boots",
                        "enderite_sword",
                        "enderite_pickaxe",
                        "enderite_axe",
                        "enderite_shovel",
                        "enderite_hoe"
                ))).build(consumer, "enderite:upgraded_again");

        AdvancementEntry voidKnight = Advancement.Builder.create()
                .display(
                        ModItems.ENDERITE_CHESTPLATE,
                        Text.translatable("advancements.enderite.void_knight.title"),
                        Text.translatable("advancements.enderite.void_knight.description"),
                        BACKGROUND_TEXTURE,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                ).parent(upgradedAgain)
                .criterion("enderite_helmet", InventoryChangedCriterion.Conditions.items(ModItems.ENDERITE_HELMET))
                .criterion("enderite_chestplate", InventoryChangedCriterion.Conditions.items(ModItems.ENDERITE_CHESTPLATE))
                .criterion("enderite_leggings", InventoryChangedCriterion.Conditions.items(ModItems.ENDERITE_LEGGINGS))
                .criterion("enderite_boots", InventoryChangedCriterion.Conditions.items(ModItems.ENDERITE_BOOTS))
                .requirements(AdvancementRequirements.allOf(List.of(
                        "enderite_helmet",
                        "enderite_chestplate",
                        "enderite_leggings",
                        "enderite_boots"
                ))).build(consumer, "enderite:void_knight");

        // endregion

        // region Void Infusion

        AdvancementEntry voidInfusionTable = Advancement.Builder.create()
                .display(
                        ModBlocks.VOID_INFUSION_TABLE,
                        Text.translatable("advancements.enderite.void_infusion_table.title"),
                        Text.translatable("advancements.enderite.void_infusion_table.description"),
                        BACKGROUND_TEXTURE,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                ).parent(echoesOfPower)
                .criterion("void_infusion_table", InventoryChangedCriterion.Conditions.items(ModBlocks.VOID_INFUSION_TABLE))
                .build(consumer, "enderite:void_infusion_table");

        AdvancementEntry dragonsBreath = Advancement.Builder.create()
                .display(
                        net.minecraft.item.Items.DRAGON_BREATH,
                        Text.translatable("advancements.enderite.dragons_breath.title"),
                        Text.translatable("advancements.enderite.dragons_breath.description"),
                        BACKGROUND_TEXTURE,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                ).parent(voidInfusionTable)
                .criterion("dragons_breath", InventoryChangedCriterion.Conditions.items(net.minecraft.item.Items.DRAGON_BREATH))
                .build(consumer, "enderite:dragons_breath");

        AdvancementEntry voidInfused = Advancement.Builder.create()
                .display(
                        ModItems.VOID_INFUSED_SWORD,
                        Text.translatable("advancements.enderite.void_infused.title"),
                        Text.translatable("advancements.enderite.void_infused.description"),
                        BACKGROUND_TEXTURE,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                ).parent(dragonsBreath)
                .criterion("void_infused_sword", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_SWORD))
                .criterion("void_infused_pickaxe", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_PICKAXE))
                .criterion("void_infused_axe", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_AXE))
                .criterion("void_infused_shovel", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_SHOVEL))
                .criterion("void_infused_hoe", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_HOE))
                .criterion("void_infused_helmet", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_HELMET))
                .criterion("void_infused_chestplate", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_CHESTPLATE))
                .criterion("void_infused_leggings", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_LEGGINGS))
                .criterion("void_infused_boots", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_BOOTS))
                .criterion("void_infused_ingot", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_INGOT))
                .requirements(AdvancementRequirements.anyOf(List.of(
                        "void_infused_sword",
                        "void_infused_pickaxe",
                        "void_infused_axe",
                        "void_infused_shovel",
                        "void_infused_hoe",
                        "void_infused_helmet",
                        "void_infused_chestplate",
                        "void_infused_leggings",
                        "void_infused_boots",
                        "void_infused_ingot"
                ))).build(consumer, "enderite:void_infused");

        AdvancementEntry voidAscendant = Advancement.Builder.create()
                .display(
                        ModItems.VOID_INFUSED_CHESTPLATE,
                        Text.translatable("advancements.enderite.void_ascendant.title"),
                        Text.translatable("advancements.enderite.void_ascendant.description"),
                        BACKGROUND_TEXTURE,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                ).parent(voidInfused)
                .criterion("void_infused_helmet", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_HELMET))
                .criterion("void_infused_chestplate", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_CHESTPLATE))
                .criterion("void_infused_leggings", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_LEGGINGS))
                .criterion("void_infused_boots", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_BOOTS))
                .requirements(AdvancementRequirements.allOf(List.of(
                        "void_infused_helmet",
                        "void_infused_chestplate",
                        "void_infused_leggings",
                        "void_infused_boots"
                ))).build(consumer, "enderite:void_ascendant");

        AdvancementEntry voidMaster = Advancement.Builder.create()
                .display(
                        ModItems.VOID_INFUSED_HOE,
                        Text.translatable("advancements.enderite.void_master.title"),
                        Text.translatable("advancements.enderite.void_master.description"),
                        BACKGROUND_TEXTURE,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                ).parent(voidInfused)
                .criterion("void_infused_sword", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_SWORD))
                .criterion("void_infused_pickaxe", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_PICKAXE))
                .criterion("void_infused_axe", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_AXE))
                .criterion("void_infused_shovel", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_SHOVEL))
                .criterion("void_infused_hoe", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_HOE))
                .criterion("void_infused_helmet", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_HELMET))
                .criterion("void_infused_chestplate", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_CHESTPLATE))
                .criterion("void_infused_leggings", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_LEGGINGS))
                .criterion("void_infused_boots", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_BOOTS))
                .requirements(AdvancementRequirements.allOf(List.of(
                        "void_infused_sword",
                        "void_infused_pickaxe",
                        "void_infused_axe",
                        "void_infused_shovel",
                        "void_infused_hoe",
                        "void_infused_helmet",
                        "void_infused_chestplate",
                        "void_infused_leggings",
                        "void_infused_boots"
                ))).build(consumer, "enderite:void_master");

        AdvancementEntry what = Advancement.Builder.create()
                .display(
                        ModItems.VOID_INFUSED_HOE,
                        Text.translatable("advancements.enderite.what.title"),
                        Text.translatable("advancements.enderite.what.description"),
                        BACKGROUND_TEXTURE,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        true
                ).parent(voidInfused)
                .criterion("void_infused_hoe", InventoryChangedCriterion.Conditions.items(ModItems.VOID_INFUSED_HOE))
                .build(consumer, "enderite:what");

        AdvancementEntry voidDragon = Advancement.Builder.create()
                .display(
                        net.minecraft.item.Items.DRAGON_HEAD,
                        Text.translatable("advancements.enderite.void_dragon.title"),
                        Text.translatable("advancements.enderite.void_dragon.description"),
                        BACKGROUND_TEXTURE,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                ).parent(voidAscendant)
                .criterion("kill_ender_dragon", OnKilledCriterion.Conditions.createPlayerKilledEntity(
                        EntityPredicate.Builder.create().type(EntityType.ENDER_DRAGON),
                        DamageSourcePredicate.Builder.create()
                                .directEntity(EntityPredicate.Builder.create()
                                        .equipment(EntityEquipmentPredicate.Builder.create()
                                                .head(ItemPredicate.Builder.create()
                                                        .items(ModItems.VOID_INFUSED_HELMET))
                                                .chest(ItemPredicate.Builder.create()
                                                        .items(ModItems.VOID_INFUSED_CHESTPLATE))
                                                .legs(ItemPredicate.Builder.create()
                                                        .items(ModItems.VOID_INFUSED_LEGGINGS))
                                                .feet(ItemPredicate.Builder.create()
                                                        .items(ModItems.VOID_INFUSED_BOOTS))
                                                .build())
                                )
                )).build(consumer, "enderite:void_dragon");

        // endregion

        // region The Void

        // region General

        AdvancementEntry unbreakable = Advancement.Builder.create()
                .display(
                        Blocks.BEDROCK,
                        Text.translatable("advancements.enderite.unbreakable.title"),
                        Text.translatable("advancements.enderite.unbreakable.description"),
                        BACKGROUND_TEXTURE,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        true
                ).parent(voidInfused)
                .criterion("break_bedrock", Enderite.BREAK_BLOCK_WITH_ITEM.create(new BreakBlockWithItemCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(ItemPredicate.Builder.create().items(ModItems.VOID_INFUSED_PICKAXE).build()),
                        Optional.of(BlockPredicate.Builder.create().blocks(Blocks.BEDROCK).build())
                )))
                .build(consumer, "enderite:unbreakable");

        AdvancementEntry enterTheVoid = Advancement.Builder.create()
                .display(
                        Blocks.SCULK,
                        Text.translatable("advancements.enderite.enter_the_void.title"),
                        Text.translatable("advancements.enderite.enter_the_void.description"),
                        BACKGROUND_TEXTURE_VOID,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("enter_void", ChangedDimensionCriterion.Conditions.to(RegistryKey.of(RegistryKeys.WORLD, Identifier.of(Enderite.MOD_ID, "the_void"))))
                .build(consumer, "enderite:enter_the_void");

        AdvancementEntry escapeVoid = Advancement.Builder.create()
                .display(
                        Blocks.END_PORTAL_FRAME,
                        Text.translatable("advancements.enderite.escape_void.title"),
                        Text.translatable("advancements.enderite.escape_void.description"),
                        BACKGROUND_TEXTURE_VOID,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("leave_void", ChangedDimensionCriterion.Conditions.create(ModDimensions.THE_VOID, World.END))
                .parent(enterTheVoid)
                .build(consumer, "enderite:escape_void");

        AdvancementEntry dieInVoid = Advancement.Builder.create()
                .display(
                        Blocks.SKELETON_SKULL,
                        Text.translatable("advancements.enderite.die_in_void.title"),
                        Text.translatable("advancements.enderite.die_in_void.description"),
                        BACKGROUND_TEXTURE_VOID,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                // Criterion is manually granted in VoidPlayerMixin
                .criterion("player_died", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create()
                        .items(Items.BARRIER)
                        .count(NumberRange.IntRange.exactly(0))
                        .build()))
                .parent(enterTheVoid)
                .build(consumer, "enderite:die_in_void");

        AdvancementEntry niceTry = Advancement.Builder.create()
                .display(
                        Blocks.BARRIER,
                        Text.translatable("advancements.enderite.nice_try.title"),
                        Text.translatable("advancements.enderite.nice_try.description"),
                        BACKGROUND_TEXTURE_VOID,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                )
                // Criterion is manually granted in VoidPlayerMixin
                .criterion("player_jumped_in_void", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create()
                        .items(Items.BARRIER)
                        .count(NumberRange.IntRange.exactly(0))
                        .build()))
                .parent(enterTheVoid)
                .build(consumer, "enderite:nice_try");

        AdvancementEntry getEnderEye = Advancement.Builder.create()
                .display(
                        Items.ENDER_EYE,
                        Text.translatable("advancements.enderite.get_ender_eye.title"),
                        Text.translatable("advancements.enderite.get_ender_eye.description"),
                        BACKGROUND_TEXTURE_VOID,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("get_ender_eye", Criteria.INVENTORY_CHANGED.create(
                        new InventoryChangedCriterion.Conditions(
                                Optional.of(EntityPredicate.contextPredicateFromEntityPredicate(
                                        EntityPredicate.Builder.create()
                                            .location(LocationPredicate.Builder.createDimension(ModDimensions.THE_VOID))
                                )),
                                InventoryChangedCriterion.Conditions.Slots.ANY,
                                List.of(ItemPredicate.Builder.create().items(Items.ENDER_EYE).build())
                        )
                ))
                .parent(enterTheVoid)
                .build(consumer, "enderite:get_ender_eye");

        AdvancementEntry voidStructure = Advancement.Builder.create()
                .display(
                        Blocks.PURPUR_BLOCK,
                        Text.translatable("advancements.enderite.void_structure.title"),
                        Text.translatable("advancements.enderite.void_structure.description"),
                        BACKGROUND_TEXTURE_VOID,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                )
                .criterion("find_structure", TickCriterion.Conditions.createLocation(
                        LocationPredicate.Builder.create()
                                .dimension(RegistryKey.of(RegistryKeys.WORLD, Identifier.of(Enderite.MOD_ID, "the_void")))
                                .structure(structureLookup.getOrThrow(ModTags.Structures.ALL_STRUCTURES))
                )).parent(enterTheVoid)
                .build(consumer, "enderite:void_structure");

        // endregion

        // region Structure-specific

        AdvancementEntry tradeWithVillagerVoid = Advancement.Builder.create()
                .display(
                        Items.EMERALD,
                        Text.translatable("advancements.enderite.trade_with_villager_void.title"),
                        Text.translatable("advancements.enderite.trade_with_villager_void.description"),
                        BACKGROUND_TEXTURE_VOID,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                )
                .criterion("trade_villager", VillagerTradeCriterion.Conditions.create(
                        EntityPredicate.Builder.create()
                                .location(LocationPredicate.Builder.create().dimension(ModDimensions.THE_VOID))
                )).parent(voidStructure)
                .build(consumer, "enderite:trade_with_villager_void");

        AdvancementEntry lootBastionVoid = Advancement.Builder.create()
                .display(
                        Blocks.ENDER_CHEST,
                        Text.translatable("advancements.enderite.loot_bastion_void.title"),
                        Text.translatable("advancements.enderite.loot_bastion_void.description"),
                        BACKGROUND_TEXTURE_VOID,
                        AdvancementFrame.TASK,
                        true, true, true
                )
                // Using the manual constructor logic via the helper method
                .criterion("loot_stable", lootedInVoid(LootTables.BASTION_HOGLIN_STABLE_CHEST, ModDimensions.THE_VOID))
                .criterion("loot_other", lootedInVoid(LootTables.BASTION_OTHER_CHEST, ModDimensions.THE_VOID))
                .criterion("loot_treasure", lootedInVoid(LootTables.BASTION_TREASURE_CHEST, ModDimensions.THE_VOID))
                .criterion("loot_bridge", lootedInVoid(LootTables.BASTION_BRIDGE_CHEST, ModDimensions.THE_VOID))

                // Uses anyOf so that opening any ONE of these in the Void grants the advancement
                .requirements(AdvancementRequirements.anyOf(List.of(
                        "loot_stable", "loot_other", "loot_treasure", "loot_bridge"
                )))
                .parent(voidStructure)
                .build(consumer, "enderite:loot_bastion_void");

        AdvancementEntry killBlazeVoid = Advancement.Builder.create()
                .display(
                        Items.BLAZE_ROD,
                        Text.translatable("advancements.enderite.kill_blaze_void.title"),
                        Text.translatable("advancements.enderite.kill_blaze_void.description"),
                        BACKGROUND_TEXTURE_VOID,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                ).parent(voidStructure)
                .criterion("kill_blaze", OnKilledCriterion.Conditions.createPlayerKilledEntity(
                        EntityPredicate.Builder.create().type(EntityTypePredicate.create(EntityType.BLAZE))
                                .location(LocationPredicate.Builder.createStructure(structureLookup.getOrThrow(StructureKeys.FORTRESS)))
                                .location(LocationPredicate.Builder.createDimension(ModDimensions.THE_VOID))
                ))
                .build(consumer, "enderite:kill_blaze_void");

        AdvancementEntry getLevitationVoid = Advancement.Builder.create()
                .display(
                        Blocks.SHULKER_BOX,
                        Text.translatable("advancements.enderite.get_levitation_void.title"),
                        Text.translatable("advancements.enderite.get_levitation_void.description"),
                        BACKGROUND_TEXTURE_VOID,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                ).parent(voidStructure)
                .criterion("get_levitation", Criteria.EFFECTS_CHANGED.create(
                        new EffectsChangedCriterion.Conditions(
                                Optional.of(EntityPredicate.contextPredicateFromEntityPredicate(
                                        EntityPredicate.Builder.create()
                                                .location(LocationPredicate.Builder.createDimension(ModDimensions.THE_VOID))
                                                .location(LocationPredicate.Builder.createStructure(structureLookup.getOrThrow(StructureKeys.END_CITY)))
                                )),
                                EntityEffectPredicate.Builder.create().addEffect(StatusEffects.LEVITATION).build(),
                                Optional.empty()
                        )
                ))
                .build(consumer, "enderite:get_levitation");

        AdvancementEntry breakSpiderSpawnerVoid = Advancement.Builder.create()
                .display(
                        Blocks.SPAWNER,
                        Text.translatable("advancements.enderite.break_spider_spawner_void.title"),
                        Text.translatable("advancements.enderite.break_spider_spawner_void.description"),
                        BACKGROUND_TEXTURE_VOID,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                ).parent(voidStructure)
                .criterion("break_spawner", Enderite.BREAK_BLOCK_WITH_ITEM.create(new BreakBlockWithItemCriterion.Conditions(
                        Optional.of(EntityPredicate.contextPredicateFromEntityPredicate(
                                EntityPredicate.Builder.create()
                                        .location(LocationPredicate.Builder
                                                .createStructure(structureLookup.getOrThrow(StructureKeys.MINESHAFT))
                                                .dimension(ModDimensions.THE_VOID)
                                        )
                        )),
                        Optional.empty(),
                        Optional.of(BlockPredicate.Builder.create().blocks(Blocks.SPAWNER).build())
                )))
                .build(consumer, "enderite:break_spider_spawner");

        // endregion

        // endregion

        // region Misc Challenges

        AdvancementEntry overkill = Advancement.Builder.create()
                .display(
                        ModItems.ENDERITE_SWORD,
                        Text.translatable("advancements.enderite.overkill.title"),
                        Text.translatable("advancements.enderite.overkill.description"),
                        BACKGROUND_TEXTURE,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                ).parent(echoesOfPower)
                .criterion("kill_chicken", OnKilledCriterion.Conditions.createPlayerKilledEntity(
                        EntityPredicate.Builder.create().type(EntityType.CHICKEN),
                        DamageSourcePredicate.Builder.create()
                                .directEntity(EntityPredicate.Builder.create()
                                        .equipment(EntityEquipmentPredicate.Builder.create()
                                                .mainhand(ItemPredicate.Builder.create()
                                                        .items(ModItems.ENDERITE_SWORD))
                                                .build())
                                )
                )).build(consumer, "enderite:overkill");

        AdvancementEntry betrayal = Advancement.Builder.create()
                .display(
                        ModItems.ENDERITE_SWORD,
                        Text.translatable("advancements.enderite.betrayal.title"),
                        Text.translatable("advancements.enderite.betrayal.description"),
                        BACKGROUND_TEXTURE,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                ).parent(echoesOfPower)
                .criterion("kill_enderman_enderite", OnKilledCriterion.Conditions.createPlayerKilledEntity(
                        EntityPredicate.Builder.create().type(EntityType.ENDERMAN),
                        DamageSourcePredicate.Builder.create()
                                .directEntity(EntityPredicate.Builder.create()
                                        .equipment(EntityEquipmentPredicate.Builder.create()
                                                .mainhand(ItemPredicate.Builder.create()
                                                        .items(ModItems.ENDERITE_SWORD))
                                                .build())
                                )
                ))
                .criterion("kill_enderman_void", OnKilledCriterion.Conditions.createPlayerKilledEntity(
                        EntityPredicate.Builder.create().type(EntityType.ENDERMAN),
                        DamageSourcePredicate.Builder.create()
                                .directEntity(EntityPredicate.Builder.create()
                                        .equipment(EntityEquipmentPredicate.Builder.create()
                                                .mainhand(ItemPredicate.Builder.create()
                                                        .items(ModItems.VOID_INFUSED_SWORD))
                                                .build())
                                )
                ))
                .requirements(AdvancementRequirements.anyOf(List.of(
                        "kill_enderman_enderite",
                        "kill_enderman_void"
                ))).build(consumer, "enderite:betrayal");

        AdvancementEntry enderDragon = Advancement.Builder.create()
                .display(
                        net.minecraft.item.Items.DRAGON_HEAD,
                        Text.translatable("advancements.enderite.ender_dragon.title"),
                        Text.translatable("advancements.enderite.ender_dragon.description"),
                        BACKGROUND_TEXTURE,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                ).parent(voidKnight)
                .criterion("kill_ender_dragon", OnKilledCriterion.Conditions.createPlayerKilledEntity(
                        EntityPredicate.Builder.create().type(EntityType.ENDER_DRAGON),
                        DamageSourcePredicate.Builder.create()
                                .directEntity(EntityPredicate.Builder.create()
                                        .equipment(EntityEquipmentPredicate.Builder.create()
                                                .head(ItemPredicate.Builder.create()
                                                        .items(ModItems.ENDERITE_HELMET))
                                                .chest(ItemPredicate.Builder.create()
                                                        .items(ModItems.ENDERITE_CHESTPLATE))
                                                .legs(ItemPredicate.Builder.create()
                                                        .items(ModItems.ENDERITE_LEGGINGS))
                                                .feet(ItemPredicate.Builder.create()
                                                        .items(ModItems.ENDERITE_BOOTS))
                                                .build())
                                )
                )).build(consumer, "enderite:ender_dragon");

        // endregion
    }

    private AdvancementCriterion<PlayerGeneratesContainerLootCriterion.Conditions> lootedInVoid(RegistryKey<LootTable> lootTable, RegistryKey<World> dim) {
        return Criteria.PLAYER_GENERATES_CONTAINER_LOOT.create(
                new PlayerGeneratesContainerLootCriterion.Conditions(
                        Optional.of(EntityPredicate.contextPredicateFromEntityPredicate(
                                EntityPredicate.Builder.create()
                                        .location(LocationPredicate.Builder.create().dimension(dim))
                        )),
                        lootTable
                )
        );
    }
}

package net.lonk.enderite.item;

import net.lonk.enderite.Enderite;
import net.lonk.enderite.item.custom.VoidInfusedArmorItem;
import net.lonk.enderite.item.custom.VoidInfusedPickaxeItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.*;

import java.util.List;
import java.util.function.Function;

public class ModItems {
    // region Items

    public static final Item ENDERITE_INGOT = registerItem("enderite_ingot",
            settings -> new Item(settings.fireproof()));

    public static final Item RAW_ENDERITE = registerItem("raw_enderite",
            settings -> new Item(settings.fireproof()));

    public static final Item ENDERITE_SCRAP = registerItem("enderite_scrap",
            settings -> new Item(settings.fireproof()));

    public static final Item ENDERITE_UPGRADE_SMITHING_TEMPLATE = registerItem("enderite_upgrade_smithing_template",
            settings -> createEnderiteUpgradeTemplate(settings.fireproof().rarity(Rarity.UNCOMMON)));

    public static final Item VOID_INFUSED_INGOT = registerItem("void_infused_ingot",
            settings -> new Item(settings.rarity(Rarity.RARE).fireproof()));

    public static final Item VOID_INFUSED_CHORUS_FRUIT = registerItem("void_infused_chorus_fruit",
            settings -> new Item(settings.food(FoodComponents.CHORUS_FRUIT, ModConsumableComponents.VOID_INFUSED_CHORUS_FRUIT).rarity(Rarity.RARE).fireproof()));

    // endregion

    // region Tools

    public static final Item ENDERITE_SWORD = registerItem("enderite_sword",
            settings -> new Item(settings.fireproof().sword(ModToolMaterials.ENDERITE, 3.0f, -2.4f)));

    public static final Item ENDERITE_PICKAXE = registerItem("enderite_pickaxe",
            settings -> new Item(settings.fireproof().pickaxe(ModToolMaterials.ENDERITE, 1.0f, -2.8f)));

    public static final Item ENDERITE_SHOVEL = registerItem("enderite_shovel",
            settings -> new Item(settings.shovel(ModToolMaterials.ENDERITE, 1.5f, -3.0f).fireproof()));

    public static final Item ENDERITE_AXE = registerItem("enderite_axe",
            settings -> new Item(settings.axe(ModToolMaterials.ENDERITE, 6.0f, -3.2f).fireproof()));

    public static final Item ENDERITE_HOE = registerItem("enderite_hoe",
            settings -> new Item(settings.hoe(ModToolMaterials.ENDERITE, 0.0f, -3.0f).fireproof()));


    public static final Item VOID_INFUSED_SWORD = registerItem("void_infused_sword",
            settings -> new Item(settings.sword(ModToolMaterials.VOID_INFUSED, 3.0f, -2.4f).rarity(Rarity.RARE).fireproof()));

    public static final Item VOID_INFUSED_PICKAXE = registerItem("void_infused_pickaxe",
            settings -> new VoidInfusedPickaxeItem(settings.pickaxe(ModToolMaterials.VOID_INFUSED, 1.0f, -2.8f)));

    public static final Item VOID_INFUSED_SHOVEL = registerItem("void_infused_shovel",
            settings -> new Item(settings.shovel(ModToolMaterials.VOID_INFUSED, 1.5f, -3.0f).rarity(Rarity.RARE).fireproof()));

    public static final Item VOID_INFUSED_AXE = registerItem("void_infused_axe",
            settings -> new Item(settings.axe(ModToolMaterials.VOID_INFUSED, 6.0f, -3.2f).rarity(Rarity.RARE).fireproof()));

    public static final Item VOID_INFUSED_HOE = registerItem("void_infused_hoe",
            settings -> new Item(settings.hoe(ModToolMaterials.VOID_INFUSED, 0.0f, -3.0f).rarity(Rarity.RARE).fireproof()));

    // endregion

    // region Armor

    public static final Item ENDERITE_HELMET = registerItem("enderite_helmet",
            settings -> new Item(settings.armor(ModArmorMaterials.ENDERITE, EquipmentType.HELMET).fireproof()));

    public static final Item ENDERITE_CHESTPLATE = registerItem("enderite_chestplate",
            settings -> new Item(settings.armor(ModArmorMaterials.ENDERITE, EquipmentType.CHESTPLATE).fireproof().component(DataComponentTypes.GLIDER, Unit.INSTANCE)));

    public static final Item ENDERITE_LEGGINGS = registerItem("enderite_leggings",
            settings -> new Item(settings.armor(ModArmorMaterials.ENDERITE, EquipmentType.LEGGINGS).fireproof()));

    public static final Item ENDERITE_BOOTS = registerItem("enderite_boots",
            settings -> new Item(settings.armor(ModArmorMaterials.ENDERITE, EquipmentType.BOOTS).fireproof()));

    public static final Item VOID_INFUSED_HELMET = registerItem("void_infused_helmet",
            settings -> new VoidInfusedArmorItem(settings.armor(ModArmorMaterials.VOID_INFUSED, EquipmentType.HELMET).rarity(Rarity.RARE).fireproof()));

    public static final Item VOID_INFUSED_CHESTPLATE = registerItem("void_infused_chestplate",
            settings -> new VoidInfusedArmorItem(settings.armor(ModArmorMaterials.VOID_INFUSED, EquipmentType.CHESTPLATE).rarity(Rarity.RARE).fireproof().component(DataComponentTypes.GLIDER, Unit.INSTANCE)));

    public static final Item VOID_INFUSED_LEGGINGS = registerItem("void_infused_leggings",
            settings -> new VoidInfusedArmorItem(settings.armor(ModArmorMaterials.VOID_INFUSED, EquipmentType.LEGGINGS).rarity(Rarity.RARE).fireproof()));

    public static final Item VOID_INFUSED_BOOTS = registerItem("void_infused_boots",
            settings -> new VoidInfusedArmorItem(settings.armor(ModArmorMaterials.VOID_INFUSED, EquipmentType.BOOTS).rarity(Rarity.RARE).fireproof()));

    // endregion

    // region Helper Methods

    private static Item registerItem(String name, Function<Item.Settings, Item> function) {
        return Registry.register(Registries.ITEM, Identifier.of(Enderite.MOD_ID, name),
                function.apply(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Enderite.MOD_ID, name)))));
    }

    private static SmithingTemplateItem createEnderiteUpgradeTemplate(Item.Settings settings) {
        return new SmithingTemplateItem(
                getEnderiteUpgradeAppliesTo(),
                getEnderiteUpgradeIngredients(),
                getEnderiteUpgradeBaseSlotDescription(),
                getEnderiteUpgradeAdditionsSlotDescription(),
                getEnderiteUpgradeEmptyBaseSlotTextures(),
                getEnderiteUpgradeEmptyAdditionsSlotTextures(),
                settings
        );
    }

    private static Text getEnderiteUpgradeAppliesTo() {
        return Text.translatable(Util.createTranslationKey("item", Identifier.of(Enderite.MOD_ID, "smithing_template.enderite_upgrade.applies_to")))
                .formatted(Formatting.BLUE);
    }

    private static Text getEnderiteUpgradeIngredients() {
        return Text.translatable(Util.createTranslationKey("item", Identifier.of(Enderite.MOD_ID, "smithing_template.enderite_upgrade.ingredients")))
                .formatted(Formatting.BLUE);
    }

    private static Text getEnderiteUpgradeBaseSlotDescription() {
        return Text.translatable(Util.createTranslationKey("item", Identifier.of(Enderite.MOD_ID, "smithing_template.enderite_upgrade.base_slot_description")));
    }

    private static Text getEnderiteUpgradeAdditionsSlotDescription() {
        return Text.translatable(Util.createTranslationKey("item", Identifier.of(Enderite.MOD_ID, "smithing_template.enderite_upgrade.additions_slot_description")));
    }

    private static List<Identifier> getEnderiteUpgradeEmptyBaseSlotTextures() {
        return List.of(
                Identifier.ofVanilla("container/slot/helmet"),
                Identifier.ofVanilla("container/slot/chestplate"),
                Identifier.ofVanilla("container/slot/leggings"),
                Identifier.ofVanilla("container/slot/boots"),
                Identifier.ofVanilla("container/slot/sword"),
                Identifier.ofVanilla("container/slot/pickaxe"),
                Identifier.ofVanilla("container/slot/axe"),
                Identifier.ofVanilla("container/slot/hoe"),
                Identifier.ofVanilla("container/slot/shovel")
        );
    }

    private static List<Identifier> getEnderiteUpgradeEmptyAdditionsSlotTextures() {
        return List.of(Identifier.ofVanilla("container/slot/ingot"));
    }

    public static void init() {}

    // endregion
}

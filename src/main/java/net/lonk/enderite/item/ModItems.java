package net.lonk.enderite.item;

import net.lonk.enderite.Enderite;
import net.lonk.enderite.item.custom.VoidInfusedArmorItem;
import net.lonk.enderite.item.custom.VoidInfusedPickaxeItem;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.Util;

import java.util.List;
import java.util.function.Function;

public class ModItems {
    // region Items

    public static final Item ENDERITE_INGOT = registerItem("enderite_ingot",
            new Item(new Item.Settings().fireproof()));

    public static final Item RAW_ENDERITE = registerItem("raw_enderite",
            new Item(new Item.Settings().fireproof()));

    public static final Item ENDERITE_SCRAP = registerItem("enderite_scrap",
            new Item(new Item.Settings().fireproof()));

    public static final Item ENDERITE_UPGRADE_SMITHING_TEMPLATE = registerItem("enderite_upgrade_smithing_template",
            createEnderiteUpgradeTemplate());

    public static final Item VOID_INFUSED_INGOT = registerItem("void_infused_ingot",
            new Item(new Item.Settings().rarity(Rarity.RARE).fireproof()));

    public static final Item VOID_INFUSED_CHORUS_FRUIT = registerItem("void_infused_chorus_fruit",
            new Item(new Item.Settings().food(FoodComponents.CHORUS_FRUIT).rarity(Rarity.RARE).fireproof()));

    // endregion

    // region Tools

    public static final Item ENDERITE_SWORD = registerItem("enderite_sword",
            new SwordItem(ModToolMaterials.ENDERITE, new Item.Settings().fireproof().attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.ENDERITE, 3, -2.4f))));

    public static final Item ENDERITE_PICKAXE = registerItem("enderite_pickaxe",
            new PickaxeItem(ModToolMaterials.ENDERITE, new Item.Settings().fireproof().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.ENDERITE, 1.0f, -2.8f))));

    public static final Item ENDERITE_SHOVEL = registerItem("enderite_shovel",
            new ShovelItem(ModToolMaterials.ENDERITE, new Item.Settings().fireproof().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.ENDERITE, 1.5f, -3.0f))));

    public static final Item ENDERITE_AXE = registerItem("enderite_axe",
            new AxeItem(ModToolMaterials.ENDERITE, new Item.Settings().fireproof().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.ENDERITE, 6.0f, -3.2f))));

    public static final Item ENDERITE_HOE = registerItem("enderite_hoe",
            new HoeItem(ModToolMaterials.ENDERITE, new Item.Settings().fireproof().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.ENDERITE, 0.0f, -3.0f))));


    public static final Item VOID_INFUSED_SWORD = registerItem("void_infused_sword",
            new SwordItem(ModToolMaterials.VOID_INFUSED, new Item.Settings().rarity(Rarity.RARE).fireproof().attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.VOID_INFUSED, 3, -2.4f))));

    public static final Item VOID_INFUSED_PICKAXE = registerItem("void_infused_pickaxe",
            new VoidInfusedPickaxeItem(ModToolMaterials.VOID_INFUSED, new Item.Settings().rarity(Rarity.RARE).fireproof().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.VOID_INFUSED, 1.0f, -2.8f))));

    public static final Item VOID_INFUSED_SHOVEL = registerItem("void_infused_shovel",
            new ShovelItem(ModToolMaterials.VOID_INFUSED, new Item.Settings().rarity(Rarity.RARE).fireproof().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.VOID_INFUSED, 1.5f, -3.0f))));

    public static final Item VOID_INFUSED_AXE = registerItem("void_infused_axe",
            new AxeItem(ModToolMaterials.VOID_INFUSED, new Item.Settings().rarity(Rarity.RARE).fireproof().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.VOID_INFUSED, 6.0f, -3.2f))));

    public static final Item VOID_INFUSED_HOE = registerItem("void_infused_hoe",
            new HoeItem(ModToolMaterials.VOID_INFUSED, new Item.Settings().rarity(Rarity.RARE).fireproof().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.VOID_INFUSED, 0.0f, -3.0f))));

    // endregion

    // region Armor

    public static final Item ENDERITE_HELMET = registerItem("enderite_helmet",
            new ArmorItem(ModArmorMaterials.ENDERITE, ArmorItem.Type.HELMET, new Item.Settings().fireproof()));

    public static final Item ENDERITE_CHESTPLATE = registerItem("enderite_chestplate",
            new ArmorItem(ModArmorMaterials.ENDERITE, ArmorItem.Type.CHESTPLATE, new Item.Settings().fireproof()));

    public static final Item ENDERITE_LEGGINGS = registerItem("enderite_leggings",
            new ArmorItem(ModArmorMaterials.ENDERITE, ArmorItem.Type.LEGGINGS, new Item.Settings().fireproof()));

    public static final Item ENDERITE_BOOTS = registerItem("enderite_boots",
            new ArmorItem(ModArmorMaterials.ENDERITE, ArmorItem.Type.BOOTS, new Item.Settings().fireproof()));

    public static final Item VOID_INFUSED_HELMET = registerItem("void_infused_helmet",
            new VoidInfusedArmorItem(ModArmorMaterials.VOID_INFUSED, ArmorItem.Type.HELMET, new Item.Settings().rarity(Rarity.RARE).fireproof()));

    public static final Item VOID_INFUSED_CHESTPLATE = registerItem("void_infused_chestplate",
            new VoidInfusedArmorItem(ModArmorMaterials.VOID_INFUSED, ArmorItem.Type.CHESTPLATE, new Item.Settings().rarity(Rarity.RARE).fireproof()));

    public static final Item VOID_INFUSED_LEGGINGS = registerItem("void_infused_leggings",
            new VoidInfusedArmorItem(ModArmorMaterials.VOID_INFUSED, ArmorItem.Type.LEGGINGS, new Item.Settings().rarity(Rarity.RARE).fireproof()));

    public static final Item VOID_INFUSED_BOOTS = registerItem("void_infused_boots",
            new VoidInfusedArmorItem(ModArmorMaterials.VOID_INFUSED, ArmorItem.Type.BOOTS, new Item.Settings().rarity(Rarity.RARE).fireproof()));

    // endregion

    // region Helper Methods

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Enderite.MOD_ID, name), item);
    }

    private static SmithingTemplateItem createEnderiteUpgradeTemplate() {
        return new SmithingTemplateItem(
                getEnderiteUpgradeAppliesTo(),
                getEnderiteUpgradeIngredients(),
                Text.translatable(Util.createTranslationKey("item", Identifier.of(Enderite.MOD_ID, "smithing_template.enderite_upgrade.title"))).formatted(Formatting.GRAY),
                getEnderiteUpgradeBaseSlotDescription(),
                getEnderiteUpgradeAdditionsSlotDescription(),
                getEnderiteUpgradeEmptyBaseSlotTextures(),
                getEnderiteUpgradeEmptyAdditionsSlotTextures()
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

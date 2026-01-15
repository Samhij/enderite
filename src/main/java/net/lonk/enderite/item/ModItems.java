package net.lonk.enderite.item;

import net.lonk.enderite.Enderite;
import net.lonk.enderite.item.custom.ChorusSingularityItem;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    // region Items

    public static final Item ENDERITE_INGOT = registerItem("enderite_ingot",
            new Item(baseSettings("enderite_ingot")));

    public static final Item RAW_ENDERITE = registerItem("raw_enderite",
            new Item(baseSettings("raw_enderite")));

    public static final Item ENDERITE_SCRAP = registerItem("enderite_scrap",
            new Item(baseSettings("enderite_scrap")));

    public static final Item ENDERITE_UPGRADE_SMITHING_TEMPLATE = registerItem("enderite_upgrade_smithing_template",
            SmithingTemplateItem.createNetheriteUpgrade(baseSettings("enderite_upgrade_smithing_template")));

    public static final Item VOID_INFUSED_INGOT = registerItem("void_infused_ingot",
            new Item(baseSettings("void_infused_ingot").rarity(Rarity.RARE).fireproof()));

    public static final Item CHORUS_SINGULARITY = registerItem("chorus_singularity",
            new ChorusSingularityItem(baseSettings("chorus_singularity").food(FoodComponents.CHORUS_FRUIT).rarity(Rarity.RARE).fireproof()));

    // endregion

    // region Tools

    public static final Item ENDERITE_SWORD = registerItem("enderite_sword",
            new SwordItem(ModToolMaterials.ENDERITE, 3, -2.4f, baseSettings("enderite_sword")));

    public static final Item ENDERITE_PICKAXE = registerItem("enderite_pickaxe",
            new PickaxeItem(ModToolMaterials.ENDERITE, 1.0f, -2.8f, baseSettings("enderite_pickaxe")));

    public static final Item ENDERITE_SHOVEL = registerItem("enderite_shovel",
            new ShovelItem(ModToolMaterials.ENDERITE, 1.5f, -3.0f, baseSettings("enderite_shovel")));

    public static final Item ENDERITE_AXE = registerItem("enderite_axe",
            new AxeItem(ModToolMaterials.ENDERITE, 6.0f, -3.2f, baseSettings("enderite_axe")));

    public static final Item ENDERITE_HOE = registerItem("enderite_hoe",
            new HoeItem(ModToolMaterials.ENDERITE, 0.0f, -3.0f, baseSettings("enderite_hoe")));


    public static final Item VOID_INFUSED_SWORD = registerItem("void_infused_sword",
            new SwordItem(ModToolMaterials.VOID_INFUSED, 3, -2.4f, baseSettings("void_infused_sword").rarity(Rarity.RARE).fireproof()));

    public static final Item VOID_INFUSED_PICKAXE = registerItem("void_infused_pickaxe",
            new PickaxeItem(ModToolMaterials.VOID_INFUSED, 1.0f, -2.8f, baseSettings("void_infused_pickaxe").rarity(Rarity.RARE).fireproof()));

    public static final Item VOID_INFUSED_SHOVEL = registerItem("void_infused_shovel",
            new ShovelItem(ModToolMaterials.VOID_INFUSED, 1.5f, -3.0f, baseSettings("void_infused_shovel").rarity(Rarity.RARE).fireproof()));

    public static final Item VOID_INFUSED_AXE = registerItem("void_infused_axe",
            new AxeItem(ModToolMaterials.VOID_INFUSED, 6.0f, -3.2f, baseSettings("void_infused_axe").rarity(Rarity.RARE).fireproof()));

    public static final Item VOID_INFUSED_HOE = registerItem("void_infused_hoe",
            new HoeItem(ModToolMaterials.VOID_INFUSED, 0.0f, -3.0f, baseSettings("void_infused_hoe").rarity(Rarity.RARE).fireproof()));

    // endregion

    // region Armor

    public static final Item ENDERITE_HELMET = registerItem("enderite_helmet",
            new ArmorItem(ModArmorMaterials.ENDERITE_ARMOR_MATERIAL, EquipmentType.HELMET, baseSettings("enderite_helmet")));

    public static final Item ENDERITE_CHESTPLATE = registerItem("enderite_chestplate",
            new ArmorItem(ModArmorMaterials.ENDERITE_ARMOR_MATERIAL, EquipmentType.CHESTPLATE, baseSettings("enderite_chestplate")));

    public static final Item ENDERITE_LEGGINGS = registerItem("enderite_leggings",
            new ArmorItem(ModArmorMaterials.ENDERITE_ARMOR_MATERIAL, EquipmentType.LEGGINGS, baseSettings("enderite_leggings")));

    public static final Item ENDERITE_BOOTS = registerItem("enderite_boots",
            new ArmorItem(ModArmorMaterials.ENDERITE_ARMOR_MATERIAL, EquipmentType.BOOTS, baseSettings("enderite_boots")));


    public static final Item VOID_INFUSED_HELMET = registerItem("void_infused_helmet",
            new ArmorItem(ModArmorMaterials.VOID_INFUSED_ARMOR_MATERIAL, EquipmentType.HELMET, baseSettings("void_infused_helmet").rarity(Rarity.RARE).fireproof()));

    public static final Item VOID_INFUSED_CHESTPLATE = registerItem("void_infused_chestplate",
            new ArmorItem(ModArmorMaterials.VOID_INFUSED_ARMOR_MATERIAL, EquipmentType.CHESTPLATE, baseSettings("void_infused_chestplate").rarity(Rarity.RARE).fireproof()));

    public static final Item VOID_INFUSED_LEGGINGS = registerItem("void_infused_leggings",
            new ArmorItem(ModArmorMaterials.VOID_INFUSED_ARMOR_MATERIAL, EquipmentType.LEGGINGS, baseSettings("void_infused_leggings").rarity(Rarity.RARE).fireproof()));

    public static final Item VOID_INFUSED_BOOTS = registerItem("void_infused_boots",
            new ArmorItem(ModArmorMaterials.VOID_INFUSED_ARMOR_MATERIAL, EquipmentType.BOOTS, baseSettings("void_infused_boots").rarity(Rarity.RARE).fireproof()));

    // endregion

    // region Helper Methods

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Enderite.MOD_ID, name), item);
    }

    private static Item.Settings baseSettings(String name) {
        return new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Enderite.MOD_ID, name)));
    }

    public static void init() {}

    // endregion
}

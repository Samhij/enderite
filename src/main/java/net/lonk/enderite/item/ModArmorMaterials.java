package net.lonk.enderite.item;

import net.lonk.enderite.Enderite;
import net.lonk.enderite.util.ModTags;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;

public class ModArmorMaterials {
    static RegistryKey<? extends Registry<EquipmentAsset>> REGISTRY_KEY = RegistryKey.ofRegistry(Identifier.ofVanilla("equipment_asset"));

    public static final RegistryKey<EquipmentAsset> ENDERITE_KEY = RegistryKey.of(REGISTRY_KEY, Identifier.of(Enderite.MOD_ID, "enderite"));
    public static final RegistryKey<EquipmentAsset> VOID_INFUSED_KEY = RegistryKey.of(REGISTRY_KEY, Identifier.of(Enderite.MOD_ID, "void_infused"));

    public static final ArmorMaterial ENDERITE_ARMOR_MATERIAL = new ArmorMaterial(
            33,
            Util.make(new EnumMap<>(EquipmentType.class), map -> {
                map.put(EquipmentType.BOOTS, 4);
                map.put(EquipmentType.LEGGINGS, 7);
                map.put(EquipmentType.CHESTPLATE, 9);
                map.put(EquipmentType.HELMET, 4);
                map.put(EquipmentType.BODY, 12);
            }),
            10,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
            2.0f,
            0.1f,
            ModTags.Items.ENDERITE_REPAIR,
            ENDERITE_KEY
    );

    public static final ArmorMaterial VOID_INFUSED_ARMOR_MATERIAL = new ArmorMaterial(
            40,
            Util.make(new EnumMap<>(EquipmentType.class), map -> {
                map.put(EquipmentType.BOOTS, 5);
                map.put(EquipmentType.LEGGINGS, 8);
                map.put(EquipmentType.CHESTPLATE, 10);
                map.put(EquipmentType.HELMET, 5);
                map.put(EquipmentType.BODY, 13);
            }),
            25,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
            4.0f,
            0.15f,
            ModTags.Items.VOID_INFUSED_REPAIR,
            VOID_INFUSED_KEY
    );
}

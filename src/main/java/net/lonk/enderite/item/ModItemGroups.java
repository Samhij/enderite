package net.lonk.enderite.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.lonk.enderite.Enderite;
import net.lonk.enderite.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup ENDERITE = Registry.register(Registries.ITEM_GROUP, Identifier.of(Enderite.MOD_ID, "enderite"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(Registries.ITEM.get(Identifier.of(Enderite.MOD_ID, "enderite_ingot"))))
                    .displayName(Text.translatable("itemGroup.enderite"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.ENDERITE_INGOT);
                        entries.add(ModItems.RAW_ENDERITE);
                        entries.add(ModItems.ENDERITE_SCRAP);
                        entries.add(ModItems.ENDERITE_UPGRADE_SMITHING_TEMPLATE);
                        entries.add(ModItems.VOID_INFUSED_INGOT);
                        entries.add(ModItems.CHORUS_SINGULARITY);

                        entries.add(ModBlocks.ENDERITE_ORE);
                        entries.add(ModBlocks.ENDERITE_BLOCK);
                        entries.add(ModBlocks.VOID_INFUSED_BLOCK);
                        entries.add(ModBlocks.VOID_INFUSION_TABLE);

                        entries.add(ModItems.ENDERITE_SWORD);
                        entries.add(ModItems.ENDERITE_PICKAXE);
                        entries.add(ModItems.ENDERITE_SHOVEL);
                        entries.add(ModItems.ENDERITE_AXE);
                        entries.add(ModItems.ENDERITE_HOE);

                        entries.add(ModItems.ENDERITE_HELMET);
                        entries.add(ModItems.ENDERITE_CHESTPLATE);
                        entries.add(ModItems.ENDERITE_LEGGINGS);
                        entries.add(ModItems.ENDERITE_BOOTS);

                        entries.add(ModItems.VOID_INFUSED_SWORD);
                        entries.add(ModItems.VOID_INFUSED_PICKAXE);
                        entries.add(ModItems.VOID_INFUSED_SHOVEL);
                        entries.add(ModItems.VOID_INFUSED_AXE);
                        entries.add(ModItems.VOID_INFUSED_HOE);

                        entries.add(ModItems.VOID_INFUSED_HELMET);
                        entries.add(ModItems.VOID_INFUSED_CHESTPLATE);
                        entries.add(ModItems.VOID_INFUSED_LEGGINGS);
                        entries.add(ModItems.VOID_INFUSED_BOOTS);
                    }).build());

    public static void init() {}
}

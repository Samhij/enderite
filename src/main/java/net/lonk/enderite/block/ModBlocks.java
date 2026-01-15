package net.lonk.enderite.block;

import net.lonk.enderite.Enderite;
import net.lonk.enderite.block.custom.VoidInfusionTableBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block ENDERITE_ORE = registerBlock("enderite_ore",
            new Block(baseSettingsCopy("enderite_ore", Blocks.END_STONE)));

    public static final Block ENDERITE_BLOCK = registerBlock("enderite_block",
            new Block(baseSettingsCopy("enderite_block", Blocks.NETHERITE_BLOCK).sounds(BlockSoundGroup.METAL)));

    public static final Block VOID_INFUSED_BLOCK = registerBlock("void_infused_block",
            new Block(baseSettingsCopy("void_infused_block", Blocks.NETHERITE_BLOCK)));

    public static final Block VOID_INFUSION_TABLE = registerBlock("void_infusion_table",
            new VoidInfusionTableBlock(baseSettingsCopy("void_infusion_table", Blocks.ENCHANTING_TABLE)
                    .luminance(state -> 7).nonOpaque()));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Enderite.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Enderite.MOD_ID, name), new BlockItem(block, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Enderite.MOD_ID, name)))));
    }

    private static AbstractBlock.Settings baseSettingsCreate(String name) {
        return AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Enderite.MOD_ID, name)));
    }

    private static AbstractBlock.Settings baseSettingsCopy(String name, Block copiedBlock) {
        return AbstractBlock.Settings.copy(copiedBlock).registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Enderite.MOD_ID, name)));
    }

    public static void init() {}
}

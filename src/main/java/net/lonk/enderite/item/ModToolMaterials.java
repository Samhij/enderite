package net.lonk.enderite.item;

import net.lonk.enderite.util.ModTags;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;

public class ModToolMaterials  {
    public static final ToolMaterial ENDERITE = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2031, 9.0f, 4.0f, 15, ModTags.Items.ENDERITE_REPAIR);
    public static final ToolMaterial VOID_INFUSED = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2500, 10.0f, 5.0f, 25, ModTags.Items.VOID_INFUSED_REPAIR);
}

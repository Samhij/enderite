package net.lonk.enderite.item.custom;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class VoidInfusedPickaxeItem extends Item {
    public VoidInfusedPickaxeItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        textConsumer.accept(Text.translatable("item.enderite.void_infused_pickaxe.tooltip"));
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }
}

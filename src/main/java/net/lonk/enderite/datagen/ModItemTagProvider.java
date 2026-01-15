package net.lonk.enderite.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.lonk.enderite.item.ModItems;
import net.lonk.enderite.util.ModTags;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.Items.ENDERITE_REPAIR).add(ModItems.ENDERITE_INGOT);
        getOrCreateTagBuilder(ModTags.Items.VOID_INFUSED_REPAIR).add(ModItems.VOID_INFUSED_INGOT);

        getOrCreateTagBuilder(ItemTags.BEACON_PAYMENT_ITEMS)
                .add(ModItems.ENDERITE_INGOT)
                .add(ModItems.VOID_INFUSED_INGOT)
        ;

        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ModItems.ENDERITE_SWORD)
                .add(ModItems.VOID_INFUSED_SWORD)
        ;

        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModItems.ENDERITE_PICKAXE)
                .add(ModItems.VOID_INFUSED_PICKAXE)
        ;

        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(ModItems.ENDERITE_SHOVEL)
                .add(ModItems.VOID_INFUSED_SHOVEL)
        ;

        getOrCreateTagBuilder(ItemTags.AXES)
                .add(ModItems.ENDERITE_AXE)
                .add(ModItems.VOID_INFUSED_AXE)
        ;

        getOrCreateTagBuilder(ItemTags.HOES)
                .add(ModItems.ENDERITE_HOE)
                .add(ModItems.VOID_INFUSED_HOE)
        ;


        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.ENDERITE_HELMET)
                .add(ModItems.ENDERITE_CHESTPLATE)
                .add(ModItems.ENDERITE_LEGGINGS)
                .add(ModItems.ENDERITE_BOOTS)

                .add(ModItems.VOID_INFUSED_HELMET)
                .add(ModItems.VOID_INFUSED_CHESTPLATE)
                .add(ModItems.VOID_INFUSED_LEGGINGS)
                .add(ModItems.VOID_INFUSED_BOOTS)
        ;
    }
}

package net.lonk.enderite.datagen.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.lonk.enderite.Enderite;
import net.lonk.enderite.block.ModBlocks;
import net.lonk.enderite.entity.ModEntities;
import net.lonk.enderite.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ModEnglishLangProvider extends FabricLanguageProvider {
    public ModEnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        // Items
        Registries.ITEM.getIds().stream()
                .filter(id -> id.getNamespace().equals(Enderite.MOD_ID))
                .map(Registries.ITEM::get)
                .forEach(item -> addAutoName(translationBuilder, item));

        translationBuilder.add("item.enderite.void_infused_pickaxe.tooltip", "A gateway to the Void");

        // Smithing Template
        translationBuilder.add("upgrade.enderite.enderite_upgrade", "Enderite Upgrade");
        translationBuilder.add("item.enderite.smithing_template.enderite_upgrade.applies_to", "Netherite Equipment");
        translationBuilder.add("item.enderite.smithing_template.enderite_upgrade.ingredients", "Enderite Ingot");
        translationBuilder.add("item.enderite.smithing_template.enderite_upgrade.base_slot_description", "Add netherite armor, weapon, or tool");
        translationBuilder.add("item.enderite.smithing_template.enderite_upgrade.additions_slot_description", "Add Enderite Ingot");

        translationBuilder.add("itemGroup.enderite", "Enderite");
        translationBuilder.add("container.enderite.void_infusion_table", "Void Infusion Table");

        // Entities
        translationBuilder.add(ModEntities.ENDERITE_GOLEM, "Enderite Golem");

        // region Advancements

        // get raw enderite
        translationBuilder.add("advancements.enderite.root.title", "End Game");
        translationBuilder.add("advancements.enderite.root.description", "The End is just the beginning");

        // craft enderite ingot
        translationBuilder.add("advancements.enderite.echoes_of_power.title", "Echoes of Power");
        translationBuilder.add("advancements.enderite.echoes_of_power.description", "Ancient whispers meet End crystals");

        // find enderite upgrade template in end city
        translationBuilder.add("advancements.enderite.city_treasure.title", "City Treasure");
        translationBuilder.add("advancements.enderite.city_treasure.description", "Find an Enderite Upgrade Smithing Template in an End City chest");

        // first enderite equipment (armor or tool)
        translationBuilder.add("advancements.enderite.upgraded_again.title", "Upgraded... Again");
        translationBuilder.add("advancements.enderite.upgraded_again.description", "Upgrade any netherite armor piece or tool to enderite");

        // full armor set
        translationBuilder.add("advancements.enderite.void_knight.title", "Void Knight");
        translationBuilder.add("advancements.enderite.void_knight.description", "Dressed to impress the void itself");

        // craft void-infused hoe
        translationBuilder.add("advancements.enderite.what.title", "What?");
        translationBuilder.add("advancements.enderite.what.description", "I seriously cannot believe you've done this. Out of every piece of equipment you could have infused with the void's power, you chose a hoe. You could have infused a pickaxe, a sword, an axe or a shovel but YOU decided that your precious Dragon's Breath and Enderite Hoe would be best spent on a VOID-INFUSED HOE? What are you even going to do with it? Till end stone? Harvest chorus plants more efficiently? You have wasted your time mining Enderite, crafting an Enderite Hoe, fighting the Ender Dragon for its breath, and channeling the raw power of the void itself... just to make a slightly better farming implement. The void weeps for your choices. I hope you're happy.");

        // break bedrock with void-infused pickaxe
        translationBuilder.add("advancements.enderite.unbreakable.title", "Breaking the Rules");
        translationBuilder.add("advancements.enderite.unbreakable.description", "Jump");

        // kill chicken with enderite sword
        translationBuilder.add("advancements.enderite.overkill.title", "Overkill");
        translationBuilder.add("advancements.enderite.overkill.description", "Was that really necessary?");

        // kill enderman with enderite or void-infused sword
        translationBuilder.add("advancements.enderite.betrayal.title", "Betrayal");
        translationBuilder.add("advancements.enderite.betrayal.description", "Strike down an Enderman with a blade forged from their own kind");

        // craft void infusion table
        translationBuilder.add("advancements.enderite.void_infusion_table.title", "Void Infusion");
        translationBuilder.add("advancements.enderite.void_infusion_table.description", "Construct a Void Infusion Table to channel the void's power");

        // obtain dragon's breath
        translationBuilder.add("advancements.enderite.dragons_breath.title", "Breath of the Dragon");
        translationBuilder.add("advancements.enderite.dragons_breath.description", "Collect Dragon's Breath to fuel your void infusions");

        // first void-infused item
        translationBuilder.add("advancements.enderite.void_infused.title", "Void Touched");
        translationBuilder.add("advancements.enderite.void_infused.description", "Infuse an item with the raw power of the void");

        // full void-infused armor set
        translationBuilder.add("advancements.enderite.void_ascendant.title", "Void Ascendant");
        translationBuilder.add("advancements.enderite.void_ascendant.description", "Embrace the void completely with a full set of void-infused armor");

        // all void-infused items
        translationBuilder.add("advancements.enderite.void_master.title", "Master of the Void");
        translationBuilder.add("advancements.enderite.void_master.description", "Obtain every void-infused tool and armor piece. The void bends to your will.");

        // kill ender dragon wearing full enderite armor
        translationBuilder.add("advancements.enderite.ender_dragon.title", "Ender Dragon");
        translationBuilder.add("advancements.enderite.ender_dragon.description", "Defeat the Ender Dragon while wearing a full set of Enderite armor");

        // kill ender dragon wearing full void-infused armor
        translationBuilder.add("advancements.enderite.void_dragon.title", "Void Dragon");
        translationBuilder.add("advancements.enderite.void_dragon.description", "Defeat the Ender Dragon while wearing a full set of Void-Infused armor");

        // enter the void
        translationBuilder.add("advancements.enderite.enter_the_void.title", "Into the Void");
        translationBuilder.add("advancements.enderite.enter_the_void.description", "Fall beyond the limits of reality itself");

        // escape the void
        translationBuilder.add("advancements.enderite.escape_void.title", "Defying the Depths");
        translationBuilder.add("advancements.enderite.escape_void.description", "Find a way to escape the Void and end up in the End");

        // die in the void
        translationBuilder.add("advancements.enderite.die_in_void.title", "Bad Dream");
        translationBuilder.add("advancements.enderite.die_in_void.description", "Escape the Void by dying (it's not the only way to escape)");

        // jump in the void in the void
        translationBuilder.add("advancements.enderite.nice_try.title", "Nice Try");
        translationBuilder.add("advancements.enderite.nice_try.description", "It's not going to be that easy to escape");

        // enter a structure in the void
        translationBuilder.add("advancements.enderite.void_structure.title", "A Trip Down Memory Lane");
        translationBuilder.add("advancements.enderite.void_structure.description", "Discover a familiar echo in the emptiness");

        // trade with villager in the void
        translationBuilder.add("advancements.enderite.trade_with_villager_void.title", "Market Expansion");
        translationBuilder.add("advancements.enderite.trade_with_villager_void.description", "Complete a trade with a Villager in the Void");

        // loot bastion chest in the void
        translationBuilder.add("advancements.enderite.loot_bastion_void.title", "Tax Haven");
        translationBuilder.add("advancements.enderite.loot_bastion_void.description", "Loot a chest in a Void Bastion");

        // kill blaze in the void
        translationBuilder.add("advancements.enderite.kill_blaze_void.title", "Freezer Burn");
        translationBuilder.add("advancements.enderite.kill_blaze_void.description", "Kill a Blaze in a Void Fortress");

        // get levitation from shulker in the void
        translationBuilder.add("advancements.enderite.get_levitation_void.title", "Urban Sprawl");
        translationBuilder.add("advancements.enderite.get_levitation_void.description", "Get hit by a Shulker bullet in a Void End City");

        // break spider spawner in mineshaft in void
        translationBuilder.add("advancements.enderite.break_spider_spawner_void.title", "OSHA Violation");
        translationBuilder.add("advancements.enderite.break_spider_spawner_void.description", "Destroy a Spider Spawner in a Void Mineshaft");

        // get eye of ender in void
        translationBuilder.add("advancements.enderite.get_ender_eye.title", "One Step Closer");
        translationBuilder.add("advancements.enderite.get_ender_eye.description", "Obtain an Eye of Ender and get one step closer to escaping");

        // endregion
    }

    private void addAutoName(TranslationBuilder builder, Item item) {
        String path = Registries.ITEM.getId(item).getPath();

        // Remove the specific suffix for smithing templates
        if (path.endsWith("_smithing_template")) {
            path = path.replace("_smithing_template", "");
        }

        // Split by underscores, capitalize, and join
        String name = Arrays.stream(path.split("_"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining(" "));

        // Handle specific hyphenation cases
        if (name.contains("Void Infused")) {
            name = name.replace("Void Infused", "Void-Infused");
        }

        builder.add(item, name);
    }
}

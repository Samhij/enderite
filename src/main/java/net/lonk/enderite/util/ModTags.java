package net.lonk.enderite.util;

import net.lonk.enderite.Enderite;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> ENDERITE_REPAIR = createTag("enderite_repair");
        public static final TagKey<Item> VOID_INFUSED_REPAIR = createTag("void_infused_repair");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(Enderite.MOD_ID, name));
        }
    }

    public static class Structures {
        public static final TagKey<Structure> ALL_STRUCTURES = createTag("all_structures");

        private static TagKey<Structure> createTag(String name) {
            return TagKey.of(RegistryKeys.STRUCTURE, Identifier.of(Enderite.MOD_ID, name));
        }
    }
}

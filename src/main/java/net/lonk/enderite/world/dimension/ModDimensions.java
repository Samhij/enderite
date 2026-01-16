package net.lonk.enderite.world.dimension;

import net.lonk.enderite.Enderite;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModDimensions {
    public static final RegistryKey<World> THE_VOID = RegistryKey.of(RegistryKeys.WORLD, Identifier.of(Enderite.MOD_ID, "the_void"));
}

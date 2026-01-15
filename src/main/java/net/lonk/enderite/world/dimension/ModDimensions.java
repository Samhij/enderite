package net.lonk.enderite.world.dimension;

import net.lonk.enderite.Enderite;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;

import java.util.OptionalLong;

public class ModDimensions {
    public static final RegistryKey<DimensionType> THE_VOID_TYPE_KEY = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            Identifier.of(Enderite.MOD_ID, "the_void_type"));
    public static final RegistryKey<World> THE_VOID_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD,
            Identifier.of(Enderite.MOD_ID, "the_void"));
    public static final RegistryKey<DimensionOptions> THE_VOID_OPTIONS_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
            Identifier.of(Enderite.MOD_ID, "the_void"));

    public static void bootstrapType(Registerable<DimensionType> context) {
        context.register(THE_VOID_TYPE_KEY, new DimensionType(
                OptionalLong.of(12000L), // fixedTime
                false, // hasSkylight
                true, // hasCeiling
                false, // ultraWarm
                false, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                -64, // minY
                384, // height
                384, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                DimensionTypes.THE_END_ID, // effectsLocation
                0.0f, // ambientLight
                new DimensionType.MonsterSettings(true, false, UniformIntProvider.create(0, 0), 0)
        ));
    }
}

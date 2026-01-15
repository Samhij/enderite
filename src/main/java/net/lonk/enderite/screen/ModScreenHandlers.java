package net.lonk.enderite.screen;

import net.lonk.enderite.Enderite;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<VoidInfusionTableScreenHandler> VOID_INFUSION_TABLE_SCREEN_HANDLER =
            Registry.register(
                    Registries.SCREEN_HANDLER,
                    Identifier.of(Enderite.MOD_ID, "void_infusion_table"),
                    new ScreenHandlerType<>(VoidInfusionTableScreenHandler::new, FeatureFlags.VANILLA_FEATURES)
            );

    public static void init() {}
}

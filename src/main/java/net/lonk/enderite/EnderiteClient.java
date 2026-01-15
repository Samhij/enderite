package net.lonk.enderite;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.lonk.enderite.entity.ModEntities;
import net.lonk.enderite.entity.client.EnderiteGolemRenderer;
import net.lonk.enderite.screen.ModScreenHandlers;
import net.lonk.enderite.screen.VoidInfusionTableScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class EnderiteClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.ENDERITE_GOLEM, EnderiteGolemRenderer::new);
        HandledScreens.register(ModScreenHandlers.VOID_INFUSION_TABLE_SCREEN_HANDLER, VoidInfusionTableScreen::new);
    }
}

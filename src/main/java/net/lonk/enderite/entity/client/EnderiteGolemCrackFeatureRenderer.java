package net.lonk.enderite.entity.client;

import net.lonk.enderite.Enderite;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.render.entity.state.IronGolemEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class EnderiteGolemCrackFeatureRenderer extends FeatureRenderer<IronGolemEntityRenderState, IronGolemEntityModel> {
    private static final Identifier LOW_CRACK = Identifier.of(Enderite.MOD_ID, "textures/entity/enderite_golem_crackiness_low.png");
    private static final Identifier MEDIUM_CRACK = Identifier.of(Enderite.MOD_ID, "textures/entity/enderite_golem_crackiness_medium.png");
    private static final Identifier HIGH_CRACK = Identifier.of(Enderite.MOD_ID, "textures/entity/enderite_golem_crackiness_high.png");

    public EnderiteGolemCrackFeatureRenderer(FeatureRendererContext<IronGolemEntityRenderState, IronGolemEntityModel> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, IronGolemEntityRenderState state, float limbAngle, float limbDistance) {
        if (!state.invisible) {
            Identifier crackTexture = getCrackTexture(state);
            if (crackTexture != null) {
                renderModel(this.getContextModel(), crackTexture, matrices, vertexConsumers, light, state, -1);
            }
        }
    }

    private Identifier getCrackTexture(IronGolemEntityRenderState state) {
        if (state.crackLevel == null) {
            return null;
        }

        return switch (state.crackLevel) {
            case LOW -> LOW_CRACK;
            case MEDIUM -> MEDIUM_CRACK;
            case HIGH -> HIGH_CRACK;
            default -> null;
        };
    }
}

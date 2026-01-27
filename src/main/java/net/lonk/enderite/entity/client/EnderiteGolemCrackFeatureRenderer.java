package net.lonk.enderite.entity.client;

import net.lonk.enderite.Enderite;
import net.lonk.enderite.entity.custom.EnderiteGolemEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class EnderiteGolemCrackFeatureRenderer extends FeatureRenderer<EnderiteGolemEntity, IronGolemEntityModel<EnderiteGolemEntity>> {
    private static final Identifier LOW_CRACK = Identifier.of(Enderite.MOD_ID, "textures/entity/enderite_golem_crackiness_low.png");
    private static final Identifier MEDIUM_CRACK = Identifier.of(Enderite.MOD_ID, "textures/entity/enderite_golem_crackiness_medium.png");
    private static final Identifier HIGH_CRACK = Identifier.of(Enderite.MOD_ID, "textures/entity/enderite_golem_crackiness_high.png");

    public EnderiteGolemCrackFeatureRenderer(FeatureRendererContext<EnderiteGolemEntity, IronGolemEntityModel<EnderiteGolemEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EnderiteGolemEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (!entity.isInvisible()) {
            Identifier crackTexture = getCrackTexture(entity);
            if (crackTexture != null) {
                renderModel(this.getContextModel(), crackTexture, matrices, vertexConsumers, light, entity, -1);
            }
        }
    }

    private Identifier getCrackTexture(EnderiteGolemEntity entity) {
        // Calculate crack level based on health percentage
        float healthPercent = entity.getHealth() / entity.getMaxHealth();

        if (healthPercent < 0.25f) {
            return HIGH_CRACK;
        } else if (healthPercent < 0.5f) {
            return MEDIUM_CRACK;
        } else if (healthPercent < 0.75f) {
            return LOW_CRACK;
        }
        return null;
    }
}

package net.lonk.enderite.entity.client;

import net.lonk.enderite.Enderite;
import net.lonk.enderite.entity.custom.EnderiteGolemEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.util.Identifier;

public class EnderiteGolemRenderer extends MobEntityRenderer<EnderiteGolemEntity, IronGolemEntityModel<EnderiteGolemEntity>> {
    private static final Identifier TEXTURE = Identifier.of(Enderite.MOD_ID, "textures/entity/enderite_golem.png");

    public EnderiteGolemRenderer(EntityRendererFactory.Context context) {
        super(context, new IronGolemEntityModel<>(context.getPart(EntityModelLayers.IRON_GOLEM)), 0.7f);
        this.addFeature(new EnderiteGolemCrackFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(EnderiteGolemEntity entity) {
        return TEXTURE;
    }
}

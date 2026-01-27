package net.lonk.enderite.entity;

import net.lonk.enderite.Enderite;
import net.lonk.enderite.entity.custom.EnderiteGolemEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<EnderiteGolemEntity> ENDERITE_GOLEM = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(Enderite.MOD_ID, "enderite_golem"),
            EntityType.Builder.create(EnderiteGolemEntity::new, SpawnGroup.MISC).dimensions(1.4f, 2.7f).maxTrackingRange(10).build("enderite_golem"));

    public static void init() {}
}

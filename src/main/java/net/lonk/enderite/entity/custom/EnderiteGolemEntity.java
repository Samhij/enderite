package net.lonk.enderite.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.world.World;

public class EnderiteGolemEntity extends IronGolemEntity {
    public EnderiteGolemEntity(EntityType<? extends IronGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        // Add goal to attack iron golems
        this.goalSelector.add(1, new ActiveTargetGoal<>(this, IronGolemEntity.class, 10, true, false,
            (entity, world) -> !(entity instanceof EnderiteGolemEntity)));
    }

    public static DefaultAttributeContainer.Builder createEnderiteGolemAttributes() {
        return IronGolemEntity.createIronGolemAttributes()
                .add(EntityAttributes.MAX_HEALTH, 200.0) // 100 health = 50 hearts, doubled to 200 = 100 hearts
                .add(EntityAttributes.ATTACK_DAMAGE, 15.0) // Iron golem has 7-21 damage, this sets base to 15
        ;
    }
}

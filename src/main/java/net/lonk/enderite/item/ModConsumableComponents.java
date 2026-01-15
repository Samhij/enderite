package net.lonk.enderite.item;

import net.lonk.enderite.effect.TeleportFromVoidConsumeEffect;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.item.consume.UseAction;
import net.minecraft.sound.SoundEvents;

public class ModConsumableComponents {
    public static final ConsumableComponent VOID_INFUSED_CHORUS_FRUIT =
            ConsumableComponent.builder()
                    .consumeSeconds(1.6f)
                    .useAction(UseAction.EAT)
                    .sound(SoundEvents.ENTITY_GENERIC_EAT)
                    .consumeParticles(true)
                    .consumeEffect(new TeleportFromVoidConsumeEffect())
                    .build();
}

package com.archecraft.minecraft.acorigins.registry;

import com.archecraft.minecraft.acorigins.ACOrigins;
import com.archecraft.minecraft.acorigins.powers.ChoosePower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.*;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.registry.Registry;

public class ACOEntityConditions {
    private static void register(ConditionFactory<Entity> conditionFactory) {
        Registry.register(ApoliRegistries.ENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
    
    public static void init() {
        register(new ConditionFactory<>(
                ACOrigins.identifier("chosen"),
                new SerializableData()
                        .add("option", SerializableDataTypes.STRING)
                        .add("power", ApoliDataTypes.POWER_TYPE),
                (data, entity) -> {
                     PowerType<?> type = data.get("power");
                     Power power = PowerHolderComponent.KEY.get(entity).getPower(type);
                     if (!(power instanceof ChoosePower)) {
                         ACOrigins.LOGGER.info("Failed to get power for type " + type.getIdentifier());
                         return false;
                     }
                     return ((ChoosePower) power).getCurrOption().equalsIgnoreCase(data.getString("option"));
                }
        ));
    }
}

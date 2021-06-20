package com.archecraft.minecraft.acorigins.powers;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;

import java.util.*;
import java.util.function.Predicate;

public class DelegatePower extends Power {
    private PowerType<?> wrappedPower;
    private List<Predicate<LivingEntity>> conditions;
    
    public DelegatePower(PowerType<?> type, LivingEntity entity, PowerType<?> wrappedPower) {
        super(type, entity);
        
        this.wrappedPower = wrappedPower;
        conditions = new LinkedList<>();
    }
    
    @Override
    public Power addCondition(Predicate<LivingEntity> condition) {
        conditions.add(condition);
        return this;
    }
    
    @Override
    public void onAdded() {
        PowerHolderComponent.KEY.get(entity).addPower(wrappedPower, type.getIdentifier());
        Power power = PowerHolderComponent.KEY.get(entity).getPower(wrappedPower);
        conditions.forEach(power::addCondition);
    }
    
    @Override
    public void onRemoved() {
        PowerHolderComponent.KEY.get(entity).removeAllPowersFromSource(type.getIdentifier());
    }
}

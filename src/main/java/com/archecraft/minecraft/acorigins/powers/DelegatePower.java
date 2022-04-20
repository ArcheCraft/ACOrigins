package com.archecraft.minecraft.acorigins.powers;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.function.Predicate;

public class DelegatePower extends Power {
    private PowerType<?> wrappedPower;
    private final Identifier wrappedPowerId;
    private List<Predicate<Entity>> conditions;
    
    public DelegatePower(PowerType<?> type, LivingEntity entity, Identifier wrappedPowerId) {
        super(type, entity);
        
        this.wrappedPowerId = wrappedPowerId;
        conditions = new LinkedList<>();
    }
    
    @Override
    public Power addCondition(Predicate<Entity> condition) {
        conditions.add(condition);
        return this;
    }
    
    @Override
    public void onAdded() {
        if(wrappedPower == null) {
            PowerType<?> reference = PowerTypeRegistry.get(wrappedPowerId);
            type.setTranslationKeys(reference.getOrCreateNameTranslationKey(), reference.getOrCreateDescriptionTranslationKey());
            wrappedPower = reference;
        }
        
        PowerHolderComponent.KEY.get(entity).addPower(wrappedPower, type.getIdentifier());
        Power power = PowerHolderComponent.KEY.get(entity).getPower(wrappedPower);
        conditions.forEach(power::addCondition);
    }
    
    @Override
    public void onRemoved() {
        PowerHolderComponent.KEY.get(entity).removeAllPowersFromSource(type.getIdentifier());
    }
}

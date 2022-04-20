package com.archecraft.minecraft.acorigins.powers;

import com.archecraft.minecraft.acorigins.components.ACODataComponent;
import com.archecraft.minecraft.acorigins.registry.ACOComponents;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public abstract class ACODataPower extends Power {
    protected ACODataComponent data;
    
    public ACODataPower(PowerType<?> type, LivingEntity player) {
        super(type, player);
    }
    
    @Override
    public void onAdded() {
        data = ACOComponents.DATA.get(entity);
    }
}

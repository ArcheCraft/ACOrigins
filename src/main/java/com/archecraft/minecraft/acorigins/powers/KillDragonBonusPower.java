package com.archecraft.minecraft.acorigins.powers;

import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;

public class KillDragonBonusPower extends ACODataPower {
    public KillDragonBonusPower(PowerType<?> type, LivingEntity player) {
        super(type, player);
    }
    
    @Override
    public void onAdded() {
        data.setTicksPerHeart(data.getTicksPerHeart() + 50);
        data.setRange(data.getRange() + 1);
        data.setHealth(data.getHealth() + 5);
        
        data.sync();
    }
    
    @Override
    public void onRemoved() {
        data.setTicksPerHeart(data.getTicksPerHeart() - 50);
        data.setRange(data.getRange() - 1);
        data.setHealth(data.getHealth() - 5);
    
        data.sync();
    }
}

package com.archecraft.minecraft.acorigins.powers;

import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class KillDragonBonusPower extends ACODataPower {
    public KillDragonBonusPower(PowerType<?> type, LivingEntity player) {
        super(type, player);
    }
    
    @Override
    public void onAdded() {
        data.setTicksPerHeartWrongDimension(data.getTicksPerHeartWrongDimension() + 100);
        data.setRange(data.getRange() + 1);
        data.setHealth(data.getHealth() + 5);
    }
    
    @Override
    public void onRemoved() {
        data.setTicksPerHeartWrongDimension(data.getTicksPerHeartWrongDimension() - 100);
        data.setRange(data.getRange() - 1);
        data.setHealth(data.getHealth() - 5);
    }
}

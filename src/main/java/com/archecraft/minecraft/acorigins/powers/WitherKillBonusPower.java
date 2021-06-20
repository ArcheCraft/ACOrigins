package com.archecraft.minecraft.acorigins.powers;

import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;

public class WitherKillBonusPower extends ACODataPower {
    public WitherKillBonusPower(PowerType<?> type, LivingEntity player) {
        super(type, player);
    }
    
    public void killedWither() {
        if (isActive()) {
            for (int i = 0; i < data.getWithersKilled(); i++) {
                double random = entity.getRandom().nextDouble();
        
                if (random < 0.5) {
                    data.setTicksPerHeart(data.getTicksPerHeart() + 10);
                }
            }
            
            for (int i = 0; i < 5; i++) {
                double random = entity.getRandom().nextDouble();
                
                if (random < 0.02) {
                    data.setSpeed(data.getSpeed() + 0.5);
                } else if (random < 0.04) {
                    data.setRange(data.getRange() + 0.5);
                } else if (random < 0.1) {
                    data.setArmor(data.getArmor() + 1);
                } else if (random < 0.3) {
                    data.setHealth(data.getHealth() + 1);
                } else if (random < 0.5) {
                    data.setDamage(data.getDamage() + 1);
                }
            }
    
            data.sync();
        }
    }
}

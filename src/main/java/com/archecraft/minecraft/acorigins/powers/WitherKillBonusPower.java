package com.archecraft.minecraft.acorigins.powers;

import com.archecraft.minecraft.acorigins.ACOrigins;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class WitherKillBonusPower extends ACODataPower {
    public WitherKillBonusPower(PowerType<?> type, LivingEntity player) {
        super(type, player);
    }
    
    public void killedWither() {
        ACOrigins.LOGGER.debug("{} killed a Wither", entity.getEntityName());
        
        if (isActive()) {
            int ticks = 0;
            double speed = 0;
            double range = 0;
            int armor = 0;
            int health = 0;
            int damage = 0;
            
            for (int i = 0; i < data.getWithersKilled(); i++) {
                double random = entity.getRandom().nextDouble();
        
                if (random < 0.5) {
                    data.setTicksPerHeart(data.getTicksPerHeart() + 10);
                    ticks += 10;
                }
            }
            
            for (int i = 0; i < 5; i++) {
                double random = entity.getRandom().nextDouble();
                
                if (random < 0.02) {
                    data.setSpeed(Math.max(Math.min(data.getSpeed() + 0.1, 2), data.getSpeed()));
                    speed += 0.5;
                } else if (random < 0.05) {
                    data.setRange(data.getRange() + 0.5);
                    range += 0.5;
                } else if (random < 0.1) {
                    data.setArmor(data.getArmor() + 1);
                    armor += 1;
                } else if (random < 0.3) {
                    data.setHealth(data.getHealth() + 1);
                    health += 1;
                } else if (random < 0.5) {
                    data.setDamage(data.getDamage() + 1);
                    damage += 1;
                }
            }
    
            data.sync();
            ACOrigins.LOGGER.debug("{} received {} ticks, {} speed, {} range, {} armor, {} health, {} damage", entity.getEntityName(), ticks, speed, range, armor, health, damage);
        }
    }
}

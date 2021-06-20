package com.archecraft.minecraft.acorigins.components;

import com.archecraft.minecraft.acorigins.registry.ACOComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public interface ACODataComponent extends AutoSyncedComponent {
    void sync();
    
    static void sync(LivingEntity entity) {
        ACOComponents.DATA.sync(entity);
    }
    
    int getWithersKilled();
    
    void setWithersKilled(int withersKilled);
    
    default void incrementWithersKilled() {
        setWithersKilled(getWithersKilled() + 1);
    }
    
    double getHealth();
    
    void setHealth(double health);
    
    double getRange();
    
    void setRange(double range);
    
    double getSpeed();
    
    void setSpeed(double speed);
    
    double getArmor();
    
    void setArmor(double armor);
    
    double getDamage();
    
    void setDamage(double damage);
    
    int getTicks();
    
    void setTicks(int ticks);
    
    int getTicksPerHeart();
    
    void setTicksPerHeart(int ticksPerHeart);
}

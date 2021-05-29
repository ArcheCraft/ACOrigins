package com.archecraft.minecraft.acorigins.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

public interface ACODataComponent extends AutoSyncedComponent {
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
    
    int getTicksWrongDimension();
    
    void setTicksWrongDimension(int ticks);
    
    int getTicksPerHeartWrongDimension();
    
    void setTicksPerHeartWrongDimension(int ticksPerHeart);
}

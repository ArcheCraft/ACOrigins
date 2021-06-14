package com.archecraft.minecraft.acorigins.components;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PlayerDataComponent implements ACODataComponent {
    private PlayerEntity player;
    
    private int withersKilled = 0;
    
    private double health = 0;
    private double range = 0;
    private double speed = 0;
    private double armor = 0;
    private double damage = 0;
    
    private int ticks = 0;
    private int ticksPerHealth = 100;
    
    public PlayerDataComponent(PlayerEntity player) {
        this.player = player;
    }
    
    @Override
    public void readFromNbt(NbtCompound tag) {
        withersKilled = tag.getInt("WithersKilled");
        health = tag.getDouble("Health");
        range = tag.getDouble("Range");
        speed = tag.getDouble("Speed");
        armor = tag.getDouble("Armor");
        damage = tag.getDouble("Damage");
        ticks = tag.getInt("Ticks");
        ticksPerHealth = tag.getInt("TicksPerHealth");
    }
    
    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("WithersKilled", withersKilled);
        tag.putDouble("Health", health);
        tag.putDouble("Range", range);
        tag.putDouble("Speed", speed);
        tag.putDouble("Armor", armor);
        tag.putDouble("Damage", damage);
        tag.putInt("Ticks", ticks);
        tag.putInt("TicksPerHealth", ticksPerHealth);
    }
    
    @Override
    public int getWithersKilled() {
        return withersKilled;
    }
    
    @Override
    public void setWithersKilled(int withersKilled) {
        this.withersKilled = withersKilled;
    }
    
    @Override
    public double getHealth() {
        return health;
    }
    
    @Override
    public void setHealth(double health) {
        this.health = health;
    }
    
    @Override
    public double getRange() {
        return range;
    }
    
    @Override
    public void setRange(double range) {
        this.range = range;
    }
    
    @Override
    public double getSpeed() {
        return speed;
    }
    
    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
    @Override
    public double getArmor() {
        return armor;
    }
    
    @Override
    public void setArmor(double armor) {
        this.armor = armor;
    }
    
    @Override
    public double getDamage() {
        return damage;
    }
    
    @Override
    public void setDamage(double damage) {
        this.damage = damage;
    }
    
    @Override
    public int getTicksWrongDimension() {
        return ticks;
    }
    
    @Override
    public void setTicksWrongDimension(int ticks) {
        this.ticks = ticks;
        
        if (this.ticks < 0) this.ticks = 0;
    }
    
    @Override
    public int getTicksPerHeartWrongDimension() {
        return ticksPerHealth * 2;
    }
    
    @Override
    public void setTicksPerHeartWrongDimension(int ticksPerHeart) {
        ticksPerHealth = ticksPerHeart / 2;
    }
}

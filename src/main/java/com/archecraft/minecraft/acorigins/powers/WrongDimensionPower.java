package com.archecraft.minecraft.acorigins.powers;

import io.github.apace100.origins.power.PowerType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class WrongDimensionPower extends ACODataPower {
    private EntityAttributeModifier currentModifier = null;
    
    public WrongDimensionPower(PowerType<?> type, PlayerEntity player) {
        super(type, player);
        setTicking(true);
    }
    
    @Override
    public void tick() {
        if (isActive()) {
            data.setTicksWrongDimension(data.getTicksWrongDimension() + 1);
        } else {
            data.setTicksWrongDimension(data.getTicksWrongDimension() - 1);
        }
        
        if (data.getTicksWrongDimension() >= getTicksForDeath()) {
            player.kill();
        } else {
            if (currentModifier != null) {
                if ((int) currentModifier.getValue() != getValue()) {
                    updateModifier();
                }
            } else {
                updateModifier();
            }
            
            if (data.getTicksWrongDimension() != 0) {
                player.sendMessage(Text.of(getSecondsLeft() + "s left"), true);
            }
        }
    }
    
    private int getValue() {
        return -data.getTicksWrongDimension() / data.getTicksPerHeartWrongDimension();
    }
    
    private int getTicksForDeath() {
        return (int) (player.getMaxHealth() - (currentModifier == null ? 0 : currentModifier.getValue())) * data.getTicksPerHeartWrongDimension();
    }
    
    private int getSecondsLeft() {
        int ticksLeft = getTicksForDeath() - data.getTicksWrongDimension();
        return ticksLeft / 20;
    }
    
    private void updateModifier() {
        if (currentModifier != null) {
            player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).removeModifier(currentModifier);
        }
        
        currentModifier = new EntityAttributeModifier("Wrong Dimension", getValue(), EntityAttributeModifier.Operation.ADDITION);
        player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addTemporaryModifier(currentModifier);
    }
}

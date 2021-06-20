package com.archecraft.minecraft.acorigins.powers;

import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.TranslatableText;

public class WrongDimensionPower extends ACODataPower {
    private EntityAttributeModifier currentModifier = null;
    
    private ConditionFactory<LivingEntity>.Instance condition;
    
    public WrongDimensionPower(PowerType<?> type, LivingEntity player, ConditionFactory<LivingEntity>.Instance condition) {
        super(type, player);
        setTicking(true);
        
        this.condition = condition;
    }
    
    @Override
    public void tick() {
        if (condition.test(entity)) {
            data.setTicks(data.getTicks() + 1);
        } else {
            data.setTicks(Math.max(0, data.getTicks() - 2));
        }
        
        if (data.getTicks() > getTicksForDeath()) {
            entity.kill();
            data.setTicks(0);
        } else {
            updateModifier();
        }
        
        data.sync();
        
        if (data.getTicks() > 0 && entity instanceof PlayerEntity) {
            ((PlayerEntity) entity).sendMessage(new TranslatableText("message.acorigins.wrong_dimension.seconds_left", getTicksLeft() / 20), true);
        }
    }
    
    @Override
    public NbtElement toTag() {
        NbtCompound tag = new NbtCompound();
        if (currentModifier != null) {
            tag.put("modifier", currentModifier.toNbt());
        }
        return tag;
    }
    
    @Override
    public void fromTag(NbtElement tag) {
        if (((NbtCompound) tag).contains("modifier")) {
            currentModifier = EntityAttributeModifier.fromNbt(((NbtCompound) tag).getCompound("modifier"));
        }
    }
    
    private int getValue() {
        return -(data.getTicks() / data.getTicksPerHeart());
    }
    
    private int getTicksForDeath() {
        return (int) (entity.getMaxHealth() - (currentModifier == null ? 0 : currentModifier.getValue())) * data.getTicksPerHeart();
    }
    
    private int getTicksLeft() {
        return getTicksForDeath() - data.getTicks();
    }
    
    private void updateModifier() {
        if (currentModifier != null) {
            entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).removeModifier(currentModifier);
        }
        
        if (getValue() != 0) {
            currentModifier = new EntityAttributeModifier("Wrong Dimension", getValue(), EntityAttributeModifier.Operation.ADDITION);
            entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addTemporaryModifier(currentModifier);
        }
    }
}

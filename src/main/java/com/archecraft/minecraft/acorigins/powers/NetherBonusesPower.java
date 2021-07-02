package com.archecraft.minecraft.acorigins.powers;

import com.archecraft.minecraft.acorigins.ACOrigins;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.util.AttributedEntityAttributeModifier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class NetherBonusesPower extends ACODataPower {
    private List<AttributedEntityAttributeModifier> modifiers = new ArrayList<>();
    
    public NetherBonusesPower(PowerType<?> type, LivingEntity player) {
        super(type, player);
        
        setTicking(true);
    }
    
    @Override
    public void tick() {
        ACOrigins.LOGGER.debug("Clearing modifiers...");
        modifiers.forEach((modifier) -> entity.getAttributeInstance(modifier.getAttribute()).removeModifier(modifier.getModifier()));
        modifiers.clear();
        
        if (isActive()) {
            ACOrigins.LOGGER.debug("Adding modifiers...");
            
            AttributedEntityAttributeModifier mod = new AttributedEntityAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier("Nether Bonus", data.getHealth(), EntityAttributeModifier.Operation.ADDITION));
            entity.getAttributeInstance(mod.getAttribute()).addTemporaryModifier(mod.getModifier());
            modifiers.add(mod);
            
            mod = new AttributedEntityAttributeModifier(ReachEntityAttributes.REACH, new EntityAttributeModifier("Nether Bonus", data.getRange(), EntityAttributeModifier.Operation.ADDITION));
            entity.getAttributeInstance(mod.getAttribute()).addTemporaryModifier(mod.getModifier());
            modifiers.add(mod);
            mod = new AttributedEntityAttributeModifier(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier("Nether Bonus", data.getRange(), EntityAttributeModifier.Operation.ADDITION));
            entity.getAttributeInstance(mod.getAttribute()).addTemporaryModifier(mod.getModifier());
            modifiers.add(mod);
            
            mod = new AttributedEntityAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("Nether Bonus", data.getSpeed(), EntityAttributeModifier.Operation.ADDITION));
            entity.getAttributeInstance(mod.getAttribute()).addTemporaryModifier(mod.getModifier());
            modifiers.add(mod);
            
            mod = new AttributedEntityAttributeModifier(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier("Nether Bonus", data.getArmor(), EntityAttributeModifier.Operation.ADDITION));
            entity.getAttributeInstance(mod.getAttribute()).addTemporaryModifier(mod.getModifier());
            modifiers.add(mod);
            
            mod = new AttributedEntityAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("Nether Bonus", data.getDamage(), EntityAttributeModifier.Operation.ADDITION));
            entity.getAttributeInstance(mod.getAttribute()).addTemporaryModifier(mod.getModifier());
            modifiers.add(mod);
        }
    }
}

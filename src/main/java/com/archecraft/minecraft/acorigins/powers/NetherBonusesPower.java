package com.archecraft.minecraft.acorigins.powers;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.util.AttributedEntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class NetherBonusesPower extends ACODataPower {
    private List<AttributedEntityAttributeModifier> modifiers = new ArrayList<>();
    
    public NetherBonusesPower(PowerType<?> type, PlayerEntity player) {
        super(type, player);
    }
    
    @Override
    public void tick() {
        modifiers.forEach((modifier) -> player.getAttributeInstance(modifier.getAttribute()).removeModifier(modifier.getModifier()));
        modifiers.clear();
        
        if (isActive()) {
            AttributedEntityAttributeModifier mod = new AttributedEntityAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier("Nether Bonus", data.getHealth(), EntityAttributeModifier.Operation.ADDITION));
            player.getAttributeInstance(mod.getAttribute()).addTemporaryModifier(mod.getModifier());
            modifiers.add(mod);
            
            mod = new AttributedEntityAttributeModifier(Registry.ATTRIBUTE.get(new Identifier(ReachEntityAttributes.MOD_ID, "reach")), new EntityAttributeModifier("Nether Bonus", data.getRange(), EntityAttributeModifier.Operation.ADDITION));
            player.getAttributeInstance(mod.getAttribute()).addTemporaryModifier(mod.getModifier());
            modifiers.add(mod);
            
            mod = new AttributedEntityAttributeModifier(Registry.ATTRIBUTE.get(new Identifier(ReachEntityAttributes.MOD_ID, "attack_range")), new EntityAttributeModifier("Nether Bonus", data.getRange(), EntityAttributeModifier.Operation.ADDITION));
            player.getAttributeInstance(mod.getAttribute()).addTemporaryModifier(mod.getModifier());
            modifiers.add(mod);
            
            mod = new AttributedEntityAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("Nether Bonus", data.getSpeed(), EntityAttributeModifier.Operation.ADDITION));
            player.getAttributeInstance(mod.getAttribute()).addTemporaryModifier(mod.getModifier());
            modifiers.add(mod);
            
            mod = new AttributedEntityAttributeModifier(EntityAttributes.GENERIC_FLYING_SPEED, new EntityAttributeModifier("Nether Bonus", data.getSpeed(), EntityAttributeModifier.Operation.ADDITION));
            player.getAttributeInstance(mod.getAttribute()).addTemporaryModifier(mod.getModifier());
            modifiers.add(mod);
            
            mod = new AttributedEntityAttributeModifier(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier("Nether Bonus", data.getArmor(), EntityAttributeModifier.Operation.ADDITION));
            player.getAttributeInstance(mod.getAttribute()).addTemporaryModifier(mod.getModifier());
            modifiers.add(mod);
            mod = new AttributedEntityAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("Nether Bonus", data.getDamage(), EntityAttributeModifier.Operation.ADDITION));
            player.getAttributeInstance(mod.getAttribute()).addTemporaryModifier(mod.getModifier());
            modifiers.add(mod);
        }
    }
}

package com.archecraft.minecraft.acorigins.powers;

import io.github.apace100.apoli.power.PowerType;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Random;

public class WitherKillBonusPower extends ACODataPower {
    public WitherKillBonusPower(PowerType<?> type, LivingEntity player) {
        super(type, player);
    }
    
    public void killedWither() {
        if (isActive()) {
            double random = entity.getRandom().nextDouble();
            if (random < 0.05) {
                data.setSpeed(data.getSpeed() + 0.5);
            } else if (random < 0.1) {
                data.setRange(data.getRange() + 0.5);
            } else if (random < 0.2) {
                data.setArmor(data.getArmor() + 1);
            } else if (random < 0.6) {
                data.setHealth(data.getHealth() + 1);
            } else {
                data.setDamage(data.getDamage() + 1);
            }
        }
    }
}

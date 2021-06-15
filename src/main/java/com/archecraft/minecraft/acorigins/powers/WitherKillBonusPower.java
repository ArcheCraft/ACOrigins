package com.archecraft.minecraft.acorigins.powers;

import io.github.apace100.apoli.power.PowerType;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Random;

public class WitherKillBonusPower extends ACODataPower {
    public WitherKillBonusPower(PowerType<?> type, LivingEntity player) {
        super(type, player);
    }
    
    public void killedWither() {
        if (isActive()) {
            for (int i = 0; i < 5; i++) {
                double random = entity.getRandom().nextDouble();
                
                if (random < 0.4) {
                    data.setTicksPerHeartWrongDimension(data.getTicksPerHeartWrongDimension() + 10);
                }
                
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
        }
    }
}

package com.archecraft.minecraft.acorigins.powers;

import io.github.apace100.origins.power.PowerType;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Random;

public class WitherKillBonusPower extends ACODataPower {
    public WitherKillBonusPower(PowerType<?> type, PlayerEntity player) {
        super(type, player);
    }
    
    public void killedWither() {
        if (isActive()) {
            data.incrementWithersKilled();
            
            double random = player.getRandom().nextDouble();
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
            
            if (data.getWithersKilled() >= 100) {
                if (player instanceof ServerPlayerEntity) {
                    Advancement advancement = player.getServer().getAdvancementLoader().get(Identifier.tryParse("acorigins:withers_killed"));
                    AdvancementProgress progress = ((ServerPlayerEntity) player).getAdvancementTracker().getProgress(advancement);
                    if (!progress.isDone()) {
                        for (String criteria : progress.getUnobtainedCriteria()) {
                            ((ServerPlayerEntity) player).getAdvancementTracker().grantCriterion(advancement, criteria);
                        }
                    }
                }
            }
        }
    }
}

package com.archecraft.minecraft.acorigins.powers;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.fabricmc.fabric.mixin.container.ServerPlayerEntityAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class FlightPower extends Power {
    public FlightPower(PowerType<?> type, PlayerEntity player) {
        super(type, player);
    }
    
    @Override
    public void onChosen(boolean isOrbOfOrigin) {
        setFlyingAllowed();
    }
    
    @Override
    public void onLost() {
        removeFlyingAllowed();
    }
    
    private void setFlyingAllowed() {
        if (player instanceof ServerPlayerEntity) {
            player.abilities.allowFlying = true;
            player.sendAbilitiesUpdate();
        }
    }
    
    private void removeFlyingAllowed() {
        if (player instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) player).interactionManager.getGameMode().setAbilities(player.abilities);
            player.sendAbilitiesUpdate();
        }
    }
}

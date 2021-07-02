package com.archecraft.minecraft.acorigins;

import com.archecraft.minecraft.acorigins.components.ACODataComponent;
import com.archecraft.minecraft.acorigins.powers.WitherKillBonusPower;
import com.archecraft.minecraft.acorigins.registry.*;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ACOrigins implements ModInitializer {
    public static final String MODID = "acorigins";
    public static final Logger LOGGER = LogManager.getLogger(ACOrigins.class);
    
    @Override
    public void onInitialize() {
        LOGGER.info("Initializing ACOrigins...");
    
        ACOEntityConditions.init();
        ACOPowers.init();
    }
    
    public static Identifier identifier(String path) {
        return new Identifier(MODID, path);
    }
}

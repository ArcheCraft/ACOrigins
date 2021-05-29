package com.archecraft.minecraft.acorigins;

import com.archecraft.minecraft.acorigins.registry.ACOPowers;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ACOrigins implements ModInitializer {
    public static final String MODID = "acorigins";
    public static final Logger LOGGER = LogManager.getLogger(ACOrigins.class);
    
    @Override
    public void onInitialize() {
        LOGGER.info("Initializing ACOrigins...");
        
        ACOPowers.init();
    }
}

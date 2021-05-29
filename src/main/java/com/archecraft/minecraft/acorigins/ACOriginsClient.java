package com.archecraft.minecraft.acorigins;

import com.archecraft.minecraft.acorigins.config.ClientConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;

public class ACOriginsClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        AutoConfig.register(ClientConfig.class, Toml4jConfigSerializer::new);
        ClientConfig config = AutoConfig.getConfigHolder(ClientConfig.class).getConfig();
    }
}

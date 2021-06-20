package com.archecraft.minecraft.acorigins.registry;

import com.archecraft.minecraft.acorigins.ACOrigins;
import com.archecraft.minecraft.acorigins.components.ACODataComponent;
import com.archecraft.minecraft.acorigins.components.PlayerDataComponent;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.*;
import net.minecraft.util.Identifier;

public class ACOComponents implements EntityComponentInitializer {
    public static final ComponentKey<ACODataComponent> DATA;
    
    static {
        DATA = ComponentRegistry.getOrCreate(ACOrigins.identifier("data"), ACODataComponent.class);
    }
    
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(DATA, PlayerDataComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}

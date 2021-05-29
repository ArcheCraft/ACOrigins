package com.archecraft.minecraft.acorigins.powers;

import com.archecraft.minecraft.acorigins.components.ACODataComponent;
import com.archecraft.minecraft.acorigins.registry.ACOComponents;
import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.entity.player.PlayerEntity;

public abstract class ACODataPower extends Power {
    protected ACODataComponent data;
    
    public ACODataPower(PowerType<?> type, PlayerEntity player) {
        super(type, player);
        data = ACOComponents.DATA.get(player);
    }
}

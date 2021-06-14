package com.archecraft.minecraft.acorigins.registry;

import com.archecraft.minecraft.acorigins.ACOrigins;
import com.archecraft.minecraft.acorigins.powers.*;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.*;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class ACOPowers {
    private static final Map<PowerFactory<?>, Identifier> POWER_FACTORIES = new LinkedHashMap<>();
    
    public static final PowerType<Power> FIRE_ASPECT = new PowerTypeReference<>(new Identifier(ACOrigins.MODID, "fire_aspect"));
    public static final PowerType<Power> WITHER_INFLICT = new PowerTypeReference<>(new Identifier(ACOrigins.MODID, "wither_inflict"));
    
    public static final PowerFactory<Power> WRONG_DIMENSION = create(new PowerFactory<Power>(
            new Identifier(ACOrigins.MODID, "wrong_dimension"),
            new SerializableData(),
            instance -> WrongDimensionPower::new
    ).allowCondition());
    public static final PowerFactory<Power> NETHER_BONUS = create(new PowerFactory<Power>(
            new Identifier(ACOrigins.MODID, "nether_bonus"),
            new SerializableData(),
            instance -> NetherBonusesPower::new
    ).allowCondition());
    public static final PowerFactory<Power> WITHER_BONUS = create(new PowerFactory<Power>(
            new Identifier(ACOrigins.MODID, "wither_bonus"),
            new SerializableData(),
            instance -> WitherKillBonusPower::new
    ).allowCondition());
    public static final PowerFactory<Power> DRAGON_BONUS = create(new PowerFactory<>(
            new Identifier(ACOrigins.MODID, "dragon_bonus"),
            new SerializableData(),
            instance -> KillDragonBonusPower::new
    ));
    public static final PowerFactory<Power> FURNACE_POWER = create(new PowerFactory<>(new Identifier(ACOrigins.MODID, "furnace"),
            new SerializableData()
                    .add("name", SerializableDataTypes.STRING, "container.inventory")
                    .add("key", ApoliDataTypes.KEY, new Active.Key()),
            data ->
                    (type, player) -> {
                        FurnacePower power = new FurnacePower(type, player, data.getString("name"));
                        power.setKey((Active.Key) data.get("key"));
                        return power;
                    }));
    
    
    private static <T extends Power> PowerFactory<T> create(PowerFactory<T> factory) {
        POWER_FACTORIES.put(factory, factory.getSerializerId());
        return factory;
    }
    
    public static void init() {
        POWER_FACTORIES.keySet().forEach(powerType -> Registry.register(ApoliRegistries.POWER_FACTORY, POWER_FACTORIES.get(powerType), powerType));
    }
}

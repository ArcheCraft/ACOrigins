package com.archecraft.minecraft.acorigins.registry;

import com.archecraft.minecraft.acorigins.ACOrigins;
import com.archecraft.minecraft.acorigins.powers.*;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.*;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class ACOPowers {
    public static final PowerType<Power> FIRE_ASPECT = new PowerTypeReference<>(ACOrigins.identifier("fire_aspect"));
    public static final PowerType<Power> WITHER_INFLICT = new PowerTypeReference<>(ACOrigins.identifier("wither_inflict"));
    
    
    private static <T extends Power> void register(PowerFactory<T> factory) {
        Registry.register(ApoliRegistries.POWER_FACTORY, factory.getSerializerId(), factory);
    }
    
    
    public static void init() {
        register(new PowerFactory<>(
                ACOrigins.identifier("wrong_dimension"),
                new SerializableData()
                        .add("condition", ApoliDataTypes.ENTITY_CONDITION),
                data ->
                        (type, entity) -> new WrongDimensionPower(type, entity, (ConditionFactory<LivingEntity>.Instance) data.get("condition"))
        ));
        
        register(new PowerFactory<>(
                ACOrigins.identifier("nether_bonus"),
                new SerializableData(),
                data -> NetherBonusesPower::new
        ).allowCondition());
        
        register(new PowerFactory<>(
                ACOrigins.identifier("wither_bonus"),
                new SerializableData(),
                data -> WitherKillBonusPower::new
        ).allowCondition());
        
        register(new PowerFactory<>(
                ACOrigins.identifier("dragon_bonus"),
                new SerializableData(),
                data -> KillDragonBonusPower::new
        ));
        
        register(new PowerFactory<>(
                ACOrigins.identifier("furnace"),
                new SerializableData()
                        .add("name", SerializableDataTypes.STRING, "container.inventory")
                        .add("key", ApoliDataTypes.KEY, new Active.Key()),
                data ->
                        (type, player) -> {
                            FurnacePower power = new FurnacePower(type, player, data.getString("name"));
                            power.setKey((Active.Key) data.get("key"));
                            return power;
                        }));
        
        register(new PowerFactory<>(
                ACOrigins.identifier("choose"),
                new SerializableData()
                        .add("options", SerializableDataType.list(SerializableDataTypes.STRING))
                        .add("key", ApoliDataTypes.KEY, new Active.Key()),
                data ->
                        (type, player) -> {
                            ChoosePower power = new ChoosePower(type, player, (List<String>) data.get("options"));
                            power.setKey((Active.Key) data.get("key"));
                            return power;
                        }
        ).allowCondition());
        
        register(new PowerFactory<>(
                ACOrigins.identifier("power"),
                new SerializableData()
                        .add("power", SerializableDataTypes.IDENTIFIER),
                data ->
                        (type, player) -> {
                            PowerType<?> reference = PowerTypeRegistry.get(data.getId("power"));
                            type.setTranslationKeys(reference.getOrCreateNameTranslationKey(), reference.getOrCreateDescriptionTranslationKey());
                            return new DelegatePower(type, player, reference);
                        }
        ).allowCondition());
    }
}

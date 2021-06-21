package com.archecraft.minecraft.acorigins.powers;

import io.github.apace100.apoli.power.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.TranslatableText;

import java.util.List;

public class ChoosePower extends Power implements Active {
    private List<String> options;
    private int currOption = 0;
    
    public ChoosePower(PowerType<?> type, LivingEntity entity, List<String> options) {
        super(type, entity);
        setTicking(true);
        
        this.options = options;
    }
    
    @Override
    public NbtElement toTag() {
        NbtCompound tag = new NbtCompound();
        tag.putInt("CurrOption", currOption);
        return tag;
    }
    
    @Override
    public void fromTag(NbtElement tag) {
        currOption = ((NbtCompound) tag).getInt("CurrOption") % options.size();
    }
    
    @Override
    public void onUse() {
        if (isActive()) {
            currOption = (currOption + 1) % options.size();
        }
    }
    
    @Override
    public void tick() {
        if (entity instanceof PlayerEntity) {
            ((PlayerEntity) entity).sendMessage(new TranslatableText("message.acorigins.choose.chosen_" + getCurrOption()), true);
        }
    }
    
    public String getCurrOption() {
        return options.get(currOption);
    }
    
    private Key key;
    
    @Override
    public Key getKey() {
        return key;
    }
    
    @Override
    public void setKey(Key key) {
        this.key = key;
    }
}

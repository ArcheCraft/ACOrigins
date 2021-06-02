package com.archecraft.minecraft.acorigins.powers;

import io.github.apace100.origins.power.*;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.*;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;

public class FurnacePower extends Power implements Active, Inventory {
    private DefaultedList<ItemStack> inventory;
    private final TranslatableText containerName;
    private final ScreenHandlerFactory factory;
    private final PropertyDelegate properties;
    
    public FurnacePower(PowerType<?> type, PlayerEntity player, String containerName) {
        super(type, player);
        
        this.inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
        this.containerName = new TranslatableText(containerName);
        this.properties = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0:
                    case 1:
                        return player.isOnFire() ? 2 : 0;
                    case 3:
                        return 1;
                    default:
                        return 0;
                }
            }
            
            @Override
            public void set(int index, int value) {
            
            }
            
            @Override
            public int size() {
                return 4;
            }
        };
        this.factory = (i, playerInventory, playerEntity) -> new FurnaceScreenHandler(i, playerInventory, this, properties);
        
        setTicking();
    }
    
    @Override
    public void onUse() {
        if (!player.world.isClient) {
            player.openHandledScreen(new SimpleNamedScreenHandlerFactory(factory, containerName));
        }
    }
    
    @Override
    public Tag toTag() {
        CompoundTag tag = new CompoundTag();
        Inventories.toTag(tag, inventory);
        return tag;
    }
    
    @Override
    public void fromTag(Tag tag) {
        inventory = DefaultedList.ofSize(size(), ItemStack.EMPTY);
        Inventories.fromTag((CompoundTag) tag, inventory);
    }
    
    
    @Override
    public void tick() {
        if (!player.world.isClient) {
            if (player.isOnFire()) {
                Recipe<?> recipe = player.world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, this, player.world).orElse(null);
                
                if (canAcceptRecipeOutput(recipe)) {
                    craftRecipe(recipe);
                }
            }
        }
    }
    
    protected boolean canAcceptRecipeOutput(@Nullable Recipe<?> recipe) {
        if (!this.inventory.get(0).isEmpty() && recipe != null) {
            ItemStack result = recipe.getOutput();
            
            if (result.isEmpty()) {
                return false;
            } else {
                ItemStack output = inventory.get(2);
                
                if (output.isEmpty()) {
                    return true;
                } else if (!output.isItemEqualIgnoreDamage(result)) {
                    return false;
                } else if (output.getCount() < output.getMaxCount()) {
                    return true;
                } else {
                    return output.getCount() < result.getMaxCount();
                }
            }
        } else {
            return false;
        }
    }
    
    private void craftRecipe(@Nullable Recipe<?> recipe) {
        if (recipe != null && canAcceptRecipeOutput(recipe)) {
            ItemStack input = inventory.get(0);
            ItemStack result = recipe.getOutput();
            ItemStack output = inventory.get(2);
            
            if (output.isEmpty()) {
                inventory.set(2, result.copy());
            } else if (output.getItem() == result.getItem()) {
                output.increment(1);
            }
            
            input.decrement(1);
        }
    }
    
    
    @Override
    public int size() {
        return inventory.size();
    }
    
    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }
    
    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }
    
    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(inventory, slot, amount);
    }
    
    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inventory, slot);
    }
    
    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }
    
    @Override
    public void markDirty() {
    
    }
    
    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return player == this.player;
    }
    
    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return slot == 0;
    }
    
    @Override
    public void clear() {
        setStack(0, ItemStack.EMPTY);
        setStack(1, ItemStack.EMPTY);
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

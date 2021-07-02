package com.archecraft.minecraft.acorigins.mixin;

import com.archecraft.minecraft.acorigins.ACOrigins;
import com.archecraft.minecraft.acorigins.components.ACODataComponent;
import com.archecraft.minecraft.acorigins.powers.WitherKillBonusPower;
import com.archecraft.minecraft.acorigins.registry.ACOComponents;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;onDeath(Lnet/minecraft/entity/damage/DamageSource;)V"))
    private void invokeKillAction(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Entity killer = source.getAttacker();
        LivingEntity killed = (LivingEntity) (Object) this;
    
        if (killer == null) {
            return;
        }
        
        ACOrigins.LOGGER.debug("{} has killed {}", killer.getEntityName(), killed.getType().getUntranslatedName());
        
        if (killed.getType() == EntityType.WITHER) {
            ACODataComponent data = ACOComponents.DATA.get(killer);
            
            data.incrementWithersKilled();
            
            PowerHolderComponent.getPowers(killer, WitherKillBonusPower.class).forEach(WitherKillBonusPower::killedWither);
            
            if (data.getWithersKilled() >= 10) {
                completeAdvancement(killer, "acorigins:withers_killed");
            }
            if (data.getWithersKilled() >= 100) {
                completeAdvancement(killer, "acorigins:more_withers_killed");
            }
        }
    }
    
    private void completeAdvancement(Entity killer, String name) {
        if (killer instanceof ServerPlayerEntity) {
            Advancement advancement = killer.getServer().getAdvancementLoader().get(Identifier.tryParse(name));
            AdvancementProgress progress = ((ServerPlayerEntity) killer).getAdvancementTracker().getProgress(advancement);
            if (!progress.isDone()) {
                for (String criteria : progress.getUnobtainedCriteria()) {
                    ((ServerPlayerEntity) killer).getAdvancementTracker().grantCriterion(advancement, criteria);
                }
            }
        }
    }
}

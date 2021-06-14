package com.archecraft.minecraft.acorigins.mixin;

import com.archecraft.minecraft.acorigins.components.ACODataComponent;
import com.archecraft.minecraft.acorigins.powers.WitherKillBonusPower;
import com.archecraft.minecraft.acorigins.registry.ACOComponents;
import com.archecraft.minecraft.acorigins.registry.ACOPowers;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.registry.ModComponents;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.EffectCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private void onAttack(Entity target, CallbackInfo ci) {
        if (((PlayerEntity)(Object) this).isOnFire() && ACOPowers.FIRE_ASPECT.isActive((PlayerEntity)(Object) this)) {
            target.setOnFireFor(15);
        }
        if (target instanceof LivingEntity && ACOPowers.WITHER_INFLICT.isActive((PlayerEntity)(Object) this)) {
            ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 15, 0));
        }
    }
    
    @Inject(method = "onKilledOther", at = @At("HEAD"))
    private void witherKilled(ServerWorld serverWorld, LivingEntity livingEntity, CallbackInfo ci) {
        if (livingEntity.getType() == EntityType.WITHER) {
            ACODataComponent data = ACOComponents.DATA.get(this);
            
            data.incrementWithersKilled();
    
            PowerHolderComponent.getPowers((PlayerEntity)(Object) this, WitherKillBonusPower.class).forEach(WitherKillBonusPower::killedWither);
    
            if (data.getWithersKilled() >= 100) {
                completeAdvancement("acorigins:withers_killed");
            }
            if (data.getWithersKilled() >= 10000) {
                completeAdvancement("acorigins:more_withers_killed");
            }
        }
    }
    
    private void completeAdvancement(String name) {
        if ((Object) this instanceof ServerPlayerEntity) {
            Advancement advancement = ((PlayerEntity)(Object) this).getServer().getAdvancementLoader().get(Identifier.tryParse(name));
            AdvancementProgress progress = ((ServerPlayerEntity)(Object) this).getAdvancementTracker().getProgress(advancement);
            if (!progress.isDone()) {
                for (String criteria : progress.getUnobtainedCriteria()) {
                    ((ServerPlayerEntity)(Object) this).getAdvancementTracker().grantCriterion(advancement, criteria);
                }
            }
        }
    }
}

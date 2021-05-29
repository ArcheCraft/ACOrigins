package com.archecraft.minecraft.acorigins.mixin;

import com.archecraft.minecraft.acorigins.powers.WitherKillBonusPower;
import com.archecraft.minecraft.acorigins.registry.ACOPowers;
import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.power.StatusEffectPower;
import io.github.apace100.origins.registry.ModComponents;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.EffectCommand;
import net.minecraft.server.world.ServerWorld;
import org.graalvm.compiler.virtual.phases.ea.EffectsClosure;
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
            ModComponents.ORIGIN.get(this).getPowers(WitherKillBonusPower.class).forEach(WitherKillBonusPower::killedWither);
        }
    }
}

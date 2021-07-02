package com.archecraft.minecraft.acorigins.mixin;

import com.archecraft.minecraft.acorigins.registry.ACOPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private void onAttack(Entity target, CallbackInfo ci) {
        if (((PlayerEntity) (Object) this).isOnFire() && ACOPowers.FIRE_ASPECT.isActive((PlayerEntity) (Object) this)) {
            target.setOnFireFor(15);
        }
        if (target instanceof LivingEntity && ACOPowers.WITHER_INFLICT.isActive((PlayerEntity) (Object) this)) {
            ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 15, 0));
        }
    }
}

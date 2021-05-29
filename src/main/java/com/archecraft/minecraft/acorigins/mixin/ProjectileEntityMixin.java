package com.archecraft.minecraft.acorigins.mixin;

import com.archecraft.minecraft.acorigins.registry.ACOPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin {
    @Inject(method = "setOwner", at = @At("TAIL"))
    private void onSetOwner(Entity entity, CallbackInfo cb) {
        if (entity.isOnFire() && ACOPowers.FIRE_ASPECT.isActive(entity)) {
            ((ProjectileEntity)(Object) this).setOnFireFor(15);
        }
    }
}

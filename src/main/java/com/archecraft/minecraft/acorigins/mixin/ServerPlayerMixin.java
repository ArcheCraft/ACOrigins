package com.archecraft.minecraft.acorigins.mixin;

import com.archecraft.minecraft.acorigins.powers.FlightPower;
import io.github.apace100.origins.command.OriginCommand;
import io.github.apace100.origins.component.OriginComponent;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerMixin {
    @Inject(method = "sendAbilitiesUpdate", at = @At(value = "HEAD"))
    private void onAbilitiesUpdate(CallbackInfo ci) {
        if (OriginComponent.hasPower((ServerPlayerEntity)(Object) this, FlightPower.class)) {
            ((ServerPlayerEntity)(Object) this).abilities.allowFlying = true;
        }
    }
}

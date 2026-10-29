package com.jellybeast.nicknames.mixin;

import com.jellybeast.nicknames.NicknameMod;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class NicknameServerPlayerMixin {
    @Inject(method = "getTabListDisplayName", at = @At("HEAD"), cancellable = true)
    private void minecraftNicknames$overrideTabListDisplayName(CallbackInfoReturnable<Component> cir) {
        Component nickname = NicknameMod.getNickname((ServerPlayer) (Object) this);
        if (nickname != null) {
            cir.setReturnValue(nickname);
        }
    }
}

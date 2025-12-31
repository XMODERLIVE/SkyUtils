package net.notnightsky.skyutils.mixins;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.Mouse;
import net.minecraft.entity.player.PlayerInventory;
import net.notnightsky.skyutils.config.keyBindingHelper.keyBinding;
import net.notnightsky.skyutils.modules.zoom.zoomHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    @Inject(method = "onMouseScroll", at = @At(value = "RETURN"))
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci){
        zoomHelper.onMouseScroll(vertical);
    }
    @WrapWithCondition(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;setSelectedSlot(I)V"), method = "onMouseScroll")
    private boolean wrapOnMouseScroll(PlayerInventory instance, int slot) {
        return !keyBinding.zoomKey.isPressed();
    }

}

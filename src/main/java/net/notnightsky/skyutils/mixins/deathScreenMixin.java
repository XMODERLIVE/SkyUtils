package net.notnightsky.skyutils.mixins;

import net.minecraft.client.gui.screen.DeathScreen;
import net.notnightsky.skyutils.config.modConfig;
import net.notnightsky.skyutils.modules.coords.deathCoords.deathCoordWiget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public abstract class deathScreenMixin {
    @Inject(method = "init", at = @At("TAIL"))
    private void skyutils$addDeathCoordButton(CallbackInfo ci) {
        if (modConfig.hookDeathScreen){
            DeathScreen screen = (DeathScreen)(Object)this;
            deathCoordWiget deathCordButton = new deathCoordWiget(screen.width / 2 - 59, screen.height / 4 + 48, 120, 20);
            screen.addDrawableChild(deathCordButton);
        }
    }
}
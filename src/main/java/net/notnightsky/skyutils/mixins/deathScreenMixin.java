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
    private void addDeathCoordButton(CallbackInfo ci) {
        if (modConfig.hookDeathScreen){
            DeathScreen screen = (DeathScreen)(Object)this;
            int buttonWidth = 120;
            int buttonHeight = 20;
            int screenWidth = screen.width;
            int centeredX = (screenWidth - buttonWidth) / 2;

            deathCoordWiget deathCordButton = new deathCoordWiget(centeredX, 180, buttonWidth, buttonHeight);
            screen.addDrawableChild(deathCordButton);
        }
    }
}
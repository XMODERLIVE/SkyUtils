package net.notnightsky.skyutils.mixins;

import net.minecraft.client.gui.screen.ChatScreen;
import net.notnightsky.skyutils.config.modConfig;
import net.notnightsky.skyutils.modules.coords.chatHudCurrentCoords.chatCurrentCoords;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class chatScreenMixin {
    @Inject(method = "init", at = @At("TAIL"))
    private void addButtons(CallbackInfo ci) {
        if (modConfig.hookChatScreen) {
            ChatScreen screen = (ChatScreen)(Object)this;
            int buttonWidth = 120;
            int buttonHeight = 20;
            int screenWidth = screen.width;
            int screenHeight = screen.height;
            int bottomRightX = screenWidth - buttonWidth - 2;
            int bottomRightY = screenHeight - buttonHeight - 15;

            chatCurrentCoords currentCoordsButton = new chatCurrentCoords(bottomRightX, bottomRightY, buttonWidth, buttonHeight);
            screen.addDrawableChild(currentCoordsButton);
        }
    }
}
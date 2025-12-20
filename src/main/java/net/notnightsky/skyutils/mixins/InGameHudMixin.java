package net.notnightsky.skyutils.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.notnightsky.skyutils.config.modConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @WrapOperation(method = "renderMiscOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/Identifier;F)V"))
    private void modifyIfPumpkin(InGameHud instance, DrawContext context, Identifier texture, float opacity, Operation<Void> original) {
        float modifiedOpacity = (float) (modConfig.pumpkinOverlayOpacity / 100);
        PlayerEntity player = MinecraftClient.getInstance().player;

        if(player == null){
            return;
        }

        if (modConfig.pumpkinOverlay && player.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.CARVED_PUMPKIN)) {
            original.call(instance, context, texture, modifiedOpacity);
        } else {
            original.call(instance, context, texture, opacity);
        }
    }
}

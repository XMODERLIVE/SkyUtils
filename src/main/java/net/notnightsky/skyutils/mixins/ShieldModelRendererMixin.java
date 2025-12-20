package net.notnightsky.skyutils.mixins;

import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.model.special.ShieldModelRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.ComponentMap;
import net.minecraft.item.ItemDisplayContext;
import net.notnightsky.skyutils.config.modConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShieldModelRenderer.class)
public class ShieldModelRendererMixin {

    @Inject(method = "render(Lnet/minecraft/component/ComponentMap;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;IIZI)V", at = @At(value = "HEAD", target = "Lnet/minecraft/client/render/item/model/special/ShieldModelRenderer;render(Lnet/minecraft/component/ComponentMap;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;IIZI)V"))
    private static void skyutils$lowShield(ComponentMap componentMap, ItemDisplayContext itemDisplayContext, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, int i, int j, boolean bl, int k, CallbackInfo ci){
        if(modConfig.lowShield && itemDisplayContext.isFirstPerson() && itemDisplayContext.equals(ItemDisplayContext.FIRST_PERSON_LEFT_HAND) || itemDisplayContext.equals(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)){
            matrixStack.translate(0, modConfig.shieldTranslate * -1, 0);
        }
    }
}

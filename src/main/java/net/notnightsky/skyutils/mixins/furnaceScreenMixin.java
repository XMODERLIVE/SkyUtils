package net.notnightsky.skyutils.mixins;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.notnightsky.skyutils.modules.furnace.furnaceCalculations;
import net.notnightsky.skyutils.modules.furnace.furnaceGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceScreen.class)
public abstract class furnaceScreenMixin {

    @Inject(method = "drawBackground", at = @At("TAIL"))
    private void onDrawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY, CallbackInfo ci) {

        AbstractFurnaceScreen<?> screen = (AbstractFurnaceScreen<?>) (Object) this;
        AbstractFurnaceScreenHandler handler = screen.getScreenHandler();

        PropertyDelegate props = ((AbstractFurnaceScreenHandlerAccessor) handler).getPropertyDelegate();

        int fr = props.get(0);
        int ft = props.get(1);
        int ce = props.get(2);
        int ct = props.get(3);

        int totalItemsToSmelt = 0;

        if (handler.getSlot(0) != null && !handler.getSlot(0).getStack().isEmpty()) {
            totalItemsToSmelt = handler.getSlot(0).getStack().getCount();
        }

        furnaceCalculations.FurnaceInfo info = furnaceCalculations.fromProperties(fr, ft, ce, ct, totalItemsToSmelt, 0);

        furnaceGui.renderTooltipIfHovered(context, screen, info, mouseX, mouseY);
    }
}
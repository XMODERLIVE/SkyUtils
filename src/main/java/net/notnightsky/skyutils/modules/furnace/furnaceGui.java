package net.notnightsky.skyutils.modules.furnace;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class furnaceGui {
    private static final int PROGRESS_ARROW_X = 79;
    private static final int PROGRESS_ARROW_Y = 34;
    private static final int PROGRESS_ARROW_WIDTH = 24;
    private static final int PROGRESS_ARROW_HEIGHT = 17;

    public static void renderTooltipIfHovered(DrawContext ctx, AbstractFurnaceScreen<?> screen, furnaceCalculations.FurnaceInfo info, int mouseX, int mouseY) {
        int screenX = screen.x;
        int screenY = screen.y;

        if (isMouseOverArea(mouseX, mouseY, screenX, screenY)) {
            List<Text> tooltip = createTooltip(info);
            ctx.drawTooltip(MinecraftClient.getInstance().textRenderer, tooltip, mouseX, mouseY);
        }
    }

    private static boolean isMouseOverArea(int mouseX, int mouseY, int screenX, int screenY) {
        return mouseX >= screenX + PROGRESS_ARROW_X &&
               mouseX <= screenX + PROGRESS_ARROW_X + PROGRESS_ARROW_WIDTH &&
               mouseY >= screenY + PROGRESS_ARROW_Y &&
               mouseY <= screenY + PROGRESS_ARROW_Y + PROGRESS_ARROW_HEIGHT;
    }

    private static List<Text> createTooltip(furnaceCalculations.FurnaceInfo info) {
        List<Text> tooltip = new ArrayList<>();

        tooltip.add(Text.literal("Remaining: " + info.remainingTimeString()));
        tooltip.add(Text.literal("Total: " + info.totalTimeString()));
        tooltip.add(Text.literal("Fuel Left: " + info.fuelLeftString()));
        tooltip.add(Text.literal("Cook %: " + info.cookPercentString()));
        tooltip.add(Text.literal("Fuel %: " + info.fuelPercentString()));

        return tooltip;
    }

}

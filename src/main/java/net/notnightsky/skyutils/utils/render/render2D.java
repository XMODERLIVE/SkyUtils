package net.notnightsky.skyutils.utils.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public final class render2D {
    private static final RenderPipeline GUI_PIPELINE = RenderPipelines.GUI_TEXTURED;
    private render2D() {}

    public static void rect(DrawContext ctx, int x, int y, int width, int height, int color) {
        ctx.fill(x, y, x + width, y + height, color);
    }

    public static void roundedRect(DrawContext ctx, int x, int y, int width, int height, int radius, int color) {
        radius = Math.min(radius, Math.min(width, height) / 2);

        rect(ctx, x + radius, y, width - radius * 2, height, color);

        rect(ctx, x, y + radius, radius, height - radius * 2, color);
        rect(ctx, x + width - radius, y + radius, radius, height - radius * 2, color);

        drawQuarterCircle(ctx, x + radius, y + radius, radius, color, Corner.TOP_LEFT);
        drawQuarterCircle(ctx, x + width - radius - 1, y + radius, radius, color, Corner.TOP_RIGHT);
        drawQuarterCircle(ctx, x + radius, y + height - radius - 1, radius, color, Corner.BOTTOM_LEFT);
        drawQuarterCircle(ctx, x + width - radius - 1, y + height - radius - 1, radius, color, Corner.BOTTOM_RIGHT);
    }

    private static void drawQuarterCircle(DrawContext ctx, int cx, int cy, int radius, int color, Corner corner) {
        for (int dx = 0; dx <= radius; dx++) {
            for (int dy = 0; dy <= radius; dy++) {
                if (dx * dx + dy * dy <= radius * radius) {
                    int px = switch (corner) {
                        case TOP_LEFT, BOTTOM_LEFT -> cx - dx;
                        case TOP_RIGHT, BOTTOM_RIGHT -> cx + dx;
                    };

                    int py = switch (corner) {
                        case TOP_LEFT, TOP_RIGHT -> cy - dy;
                        case BOTTOM_LEFT, BOTTOM_RIGHT -> cy + dy;
                    };

                    ctx.fill(px, py, px + 1, py + 1, color);
                }
            }
        }
    }

    private enum Corner {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    public static void texture(DrawContext ctx, Identifier texture, int x, int y, int width, int height) {
        ctx.drawTexture(GUI_PIPELINE ,texture, x, y, 0, 0, width, height, width, height);
    }

    public static void texture(DrawContext ctx, Identifier texture, int x, int y, int width, int height, int tintColor) {
        texture(ctx, texture, x, y, width, height);

        if ((tintColor >>> 24) < 255) {
            rect(ctx, x, y, width, height, tintColor);
        }
    }
}

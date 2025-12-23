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

    public static void texture(DrawContext ctx, Identifier texture, int x, int y, int width, int height) {
        ctx.drawTexture(GUI_PIPELINE, texture, x, y, 0, 0, width, height, width, height);
    }

    public static void texture(DrawContext ctx, Identifier texture, int x, int y, int width, int height, int tintColor) {
        texture(ctx, texture, x, y, width, height);

        if ((tintColor >>> 24) < 255) {
            rect(ctx, x, y, width, height, tintColor);
        }
    }
}
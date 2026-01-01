package net.notnightsky.skyutils.utils.render;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.notnightsky.skyutils.utils.render.states.RoundRectGuiElementRenderState;

import java.awt.*;

public final class render2D {
    private static final RenderPipeline GUI_PIPELINE = RenderPipelines.GUI_TEXTURED;

    private render2D() {}

    public static void rect(DrawContext ctx, int x, int y, int width, int height, int color) {
        ctx.fill(x, y, x + width, y + height, color);
    }

    public static void roundedRect(DrawContext context, int x, int y, int w, int h, int r, Color color){
        context.state.addSimpleElement(new RoundRectGuiElementRenderState(context, x, y, w, h, r, color.getRGB()));
    }

    public static void texture(DrawContext ctx, Identifier texture, int x, int y, int width, int height) {
        ctx.drawTexture(GUI_PIPELINE, texture, x, y, 0, 0, width, height, width, height);
    }

    /**
     * Draws a given picture
     *
     * @param x        Left X coordinate of the image
     * @param y        Top Y coordinate of the image
     * @param w        Width of the image
     * @param h        Height of the image
     * @param color    The Color of the image, if value is 0, normal color is used
     * @param texture The location of the image to be loaded from
     */

    public static void drawPicture(DrawContext context, int x, int y, int w, int h, int color, Identifier texture) {
        GlStateManager._enableBlend();

        context.drawTexture(GUI_PIPELINE, texture, x, y, 0, 0, w, h, w, h, color);

        GlStateManager._disableBlend();
    }
}
package net.notnightsky.skyutils.utils.render;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

import java.awt.*;

public final class render2D {
    private static final RenderPipeline GUI_PIPELINE = RenderPipelines.GUI_TEXTURED;

    private render2D() {}

    private static final RenderPipeline GUI_TITLE_PIPELINE = RenderPipeline.builder(RenderPipelines.GUI_SNIPPET).withVertexFormat(VertexFormats.POSITION_COLOR , VertexFormat.DrawMode.TRIANGLE_FAN).withCull(false).build();

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


    public static void texture(DrawContext ctx, Identifier texture, int x, int y, int width, int height, int tintColor) {
        texture(ctx, texture, x, y, width, height);

        if ((tintColor >>> 24) < 255) {
            rect(ctx, x, y, width, height, tintColor);
        }
    }
//    public static void circle(DrawContext ctx, int x, int y, int r, int h, int j, int color){
//        GlStateManager._enableBlend();
//        GlStateManager._disableCull();
//
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
//
//        for (int angle = h; angle <= j; angle++) {
//            float rad = (float) Math.toRadians(angle);
//            float px = (float) (Math.cos(rad) * r + x);
//            float py = (float) (Math.sin(rad) * r + y);
//
//            buffer.vertex(ctx.getMatrices(), px, py).color(color);
//        }
//        GlStateManager._disableBlend();
//        GlStateManager._enableCull();
//    }
//    public static void drawCircle(float x, float y, float r, int h, int j, int color) {
//        GL11.glEnable(GL_BLEND);
//        GL11.glDisable(GL11.GL_CULL_FACE);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        glBegin(GL_TRIANGLE_FAN);
//
//        ColorHelper.color(color);
//
//        float var;
//        glVertex2f(x, y);
//        for (var = h; var <= j; var++) {
//            ColorHelper.color(color);
//            glVertex2f(
//                    (float) (r * Math.cos(Math.PI * var / 180) + x),
//                    (float) (r * Math.sin(Math.PI * var / 180) + y)
//            );
//        }
//
//        glEnd();
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL11.GL_CULL_FACE);
//        GL11.glDisable(GL_BLEND);
//    }
}
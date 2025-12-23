package net.notnightsky.skyutils.gui.titleMenu.widget;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.notnightsky.skyutils.utils.render.render2D;

public class AbstractIconButton extends AbstractButton {
    private final Identifier icon;
    private final Runnable action;

    public AbstractIconButton(int x, int y, int size, Identifier icon, Runnable action) {
        super(x, y, size, size, Text.empty());
        this.icon = icon;
        this.action = action;
    }

    @Override
    protected void renderAni(DrawContext ctx, int mouseX, int mouseY, float delta) {
        int alpha = animValue();

        // Lower alpha for icon button too
        int bgAlpha;
        if (isHovered()) {
            bgAlpha = 80 + alpha; // More visible when hovered
        } else {
            bgAlpha = 50 + alpha; // Very subtle when not hovered
        }

        bgAlpha = Math.min(bgAlpha, 120); // Keep it transparent

        // Dark background with low alpha
        int bgColor = (bgAlpha << 24) | 0x222222;

        // Simple square button
        render2D.rect(ctx, getX(), getY(), width, height, bgColor);

        // Calculate centered icon position
        int iconSize = Math.max(width - 8, 1);
        int iconX = getX() + (width - iconSize) / 2;
        int iconY = getY() + (height - iconSize) / 2;

        // Draw icon with some transparency
        try {
            ctx.drawTexture(RenderPipelines.GUI_TEXTURED, icon, iconX, iconY, 0, 0, iconSize, iconSize, iconSize, iconSize);
        } catch (Exception e) {
            // If icon fails to load, draw a subtle placeholder
            int placeholderAlpha = 150; // Semi-transparent placeholder
            int placeholderColor = (placeholderAlpha << 24) | 0xFFFFFF;
            ctx.fill(iconX, iconY, iconX + iconSize, iconY + 1, placeholderColor);
            ctx.fill(iconX, iconY + iconSize - 1, iconX + iconSize, iconY + iconSize, placeholderColor);
            ctx.fill(iconX, iconY, iconX + 1, iconY + iconSize, placeholderColor);
            ctx.fill(iconX + iconSize - 1, iconY, iconX + iconSize, iconY + iconSize, placeholderColor);
        }
    }

    @Override
    public void onClick(Click click, boolean doubled) {
        if (click.buttonInfo().button() == 0 && action != null) {
            action.run();
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        this.appendDefaultNarrations(builder);
    }
}
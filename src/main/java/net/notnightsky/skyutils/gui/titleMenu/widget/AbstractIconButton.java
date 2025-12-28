package net.notnightsky.skyutils.gui.titleMenu.widget;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.notnightsky.skyutils.utils.animation.Animate;
import net.notnightsky.skyutils.utils.render.render2D;

import java.awt.*;

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
        animate.update().setReversed(!isHovered());

        render2D.roundedRect(ctx, getX(), getY(), width, height,8, new Color(30, 30, 30, animate.getValueI() + 60));

        int iconSize = Math.max(width - 8, 1);
        int iconX = getX() + (width - iconSize) / 2;
        int iconY = getY() + (height - iconSize) / 2;

        ctx.drawTexture(RenderPipelines.GUI_TEXTURED, icon, iconX, iconY, 0, 0, iconSize, iconSize, iconSize, iconSize);
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
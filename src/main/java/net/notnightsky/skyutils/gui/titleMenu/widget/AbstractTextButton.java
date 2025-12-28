package net.notnightsky.skyutils.gui.titleMenu.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.text.Text;
import net.notnightsky.skyutils.utils.render.render2D;

import java.awt.*;

public class AbstractTextButton extends AbstractButton {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final Runnable action;

    public AbstractTextButton(int x, int y, int width, int height, Text text, Runnable action) {
        super(x, y, width, height, text);
        this.action = action;
    }

    @Override
    protected void renderAni(DrawContext ctx, int mouseX, int mouseY, float delta) {
        animate.update().setReversed(!isHovered());

        render2D.roundedRect(ctx, getX(), getY(), width, height,8, new Color(30, 30, 30, animate.getValueI() + 60));

        int textColor = 0xFFFFFFFF;
        ctx.drawCenteredTextWithShadow(client.textRenderer, getMessage(), getX() + width / 2, getY() + (height - client.textRenderer.fontHeight) / 2, textColor);
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
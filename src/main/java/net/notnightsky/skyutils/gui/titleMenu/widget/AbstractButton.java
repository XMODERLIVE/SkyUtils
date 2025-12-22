package net.notnightsky.skyutils.gui.titleMenu.widget;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.notnightsky.skyutils.utils.animation.Animate;
import net.notnightsky.skyutils.utils.animation.Easing;

public abstract class AbstractButton extends ClickableWidget {
    protected final Animate animate;

    protected AbstractButton(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
        this.animate = new Animate()
                .setEase(Easing.LINEAR)
                .setMin(0)
                .setMax(25)
                .setSpeed(200);
    }

    @Override
    protected final void renderWidget(DrawContext ctx, int mouseX, int mouseY, float delta) {
        // Update animation based on hover state
        if (this.isHovered()) {
            animate.getMax();
        } else {
            animate.getMin();
        }

        animate.update();
        renderAni(ctx, mouseX, mouseY, delta);
    }

    /**
     * Value for hover animations etc.
     */
    protected final int animValue() {
        return (int) animate.getValueInt();
    }

    /**
     * Draw visuals here ONLY
     */
    protected abstract void renderAni(DrawContext ctx, int mouseX, int mouseY, float delta);

    /**
     * Override this method for click handling
     */
    @Override
    public abstract void onClick(Click click, boolean doubled);
}
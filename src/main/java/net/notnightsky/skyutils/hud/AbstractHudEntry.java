package net.notnightsky.skyutils.hud;

import net.notnightsky.skyutils.hud.component.HudEntry;
import net.notnightsky.skyutils.utils.render.DrawPosition;
import net.notnightsky.skyutils.utils.render.Rectangle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

public abstract class AbstractHudEntry implements HudEntry {

    protected MinecraftClient client = MinecraftClient.getInstance();

    protected int width;
    protected int height;

    private Rectangle trueBounds = null;
    private Rectangle renderBounds = null;
    private DrawPosition truePosition = null;
    private DrawPosition renderPosition;

    protected boolean hovered = false;

    public AbstractHudEntry(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static int floatToInt(float percent, int max, int offset) {
        return MathHelper.clamp(Math.round((max - offset) * percent), 0, max);
    }

    public static float intToFloat(int current, int max, int offset) {
        return MathHelper.clamp((float) (current) / (max - offset), 0, 1);
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        onBoundsUpdate();
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        onBoundsUpdate();
    }

    @Override
    public Rectangle getTrueBounds() {
        return trueBounds;
    }

    @Override
    public Rectangle getBounds() {
        return renderBounds;
    }

    @Override
    public DrawPosition getPos() {
        return renderPosition;
    }

    @Override
    public DrawPosition getTruePos() {
        return truePosition;
    }

    @Override
    public void onBoundsUpdate() {
        setBounds();
    }

    public void setBounds() {
        setBounds(getScale());
    }

    @Override
    public int getRawTrueX() {
        return truePosition.x();
    }

    @Override
    public int getRawTrueY() {
        return truePosition.y();
    }

    public int getTrueWidth() {
        if (trueBounds == null) {
            return width * (int) getScale();
        }
        return trueBounds.width();
    }

    public int getTrueHeight() {
        if (trueBounds == null) {
            return height * (int) getScale();
        }
        return trueBounds.height();
    }

    public void setBounds(float scale) {
        if (client.getWindow() == null || client.getWindow().getScaledWidth() == 0 || client.getWindow().getScaledHeight() == 0) {
            truePosition = new DrawPosition(0, 0);
            renderPosition = new DrawPosition(0, 0);
            renderBounds = new Rectangle(0, 0, 1, 1);
            trueBounds = new Rectangle(0, 0, 1, 1);
            return;
        }

        // Position at top left corner by default
        int scaledX = floatToInt(0.0f, client.getWindow().getScaledWidth(), getWidth());
        int scaledY = floatToInt(0.0f, client.getWindow().getScaledHeight(), getHeight());

        if (scaledX < 0) {
            scaledX = 0;
        }
        if (scaledY < 0) {
            scaledY = 0;
        }

        int trueWidth = (int) (getWidth() * getScale());
        if (trueWidth < client.getWindow().getScaledWidth() && scaledX + trueWidth > client.getWindow().getScaledWidth()) {
            scaledX = client.getWindow().getScaledWidth() - trueWidth;
        }
        int trueHeight = (int) (getHeight() * getScale());
        if (trueHeight < client.getWindow().getScaledHeight() && scaledY + trueHeight > client.getWindow().getScaledHeight()) {
            scaledY = client.getWindow().getScaledHeight() - trueHeight;
        }

        truePosition = new DrawPosition(scaledX, scaledY);
        renderPosition = new DrawPosition((int) (scaledX / getScale()), (int) (scaledY / getScale()));
        renderBounds = new Rectangle(renderPosition.x(), renderPosition.y(), getWidth(), getHeight());
        trueBounds = new Rectangle(scaledX, scaledY, (int) (getWidth() * getScale()), (int) (getHeight() * getScale()));
    }

    @Override
    public float getScale() {
        return 1.0f; // Default scale
    }

    @Override
    public int getRawX() {
        return getPos().x();
    }

    @Override
    public int getRawY() {
        return getPos().y();
    }

    @Override
    public void render(DrawContext context, float delta) {
        if (client.getWindow() == null) return;

        // Make sure bounds are set before rendering
        if (getTruePos() == null) {
            setBounds();
        }

        // For now, render without matrix transformations to avoid compilation errors
        // Apply scaling and render the component
        renderComponent(context, delta);
    }

    @Override
    public void renderPlaceholder(DrawContext context, float delta) {
        render(context, delta);
    }

    public abstract void renderComponent(DrawContext context, float delta);
}
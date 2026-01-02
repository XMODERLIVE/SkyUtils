package net.notnightsky.skyutils.hud.layout;

public enum AnchorPoint {
    TOP_LEFT(0, 0),
    TOP_MIDDLE(0.5f, 0),
    TOP_RIGHT(1, 0),
    MIDDLE_LEFT(0, 0.5f),
    MIDDLE_MIDDLE(0.5f, 0.5f),
    MIDDLE_RIGHT(1, 0.5f),
    BOTTOM_LEFT(0, 1),
    BOTTOM_MIDDLE(0.5f, 1),
    BOTTOM_RIGHT(1, 1);

    private final float x;
    private final float y;

    AnchorPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
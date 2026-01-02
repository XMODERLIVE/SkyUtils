package net.notnightsky.skyutils.utils.render;

public class DrawPosition {
    private final int x;
    private final int y;

    public DrawPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public DrawPosition add(int x, int y) {
        return new DrawPosition(this.x + x, this.y + y);
    }

    public DrawPosition subtract(int x, int y) {
        return new DrawPosition(this.x - x, this.y - y);
    }

    public DrawPosition multiply(float scale) {
        return new DrawPosition((int) (this.x * scale), (int) (this.y * scale));
    }

    public DrawPosition divide(float scale) {
        if (scale == 0) {
            return new DrawPosition(0, 0);
        }
        return new DrawPosition((int) (this.x / scale), (int) (this.y / scale));
    }

    @Override
    public String toString() {
        return "DrawPosition{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
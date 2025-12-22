package net.notnightsky.skyutils.utils.animation;

public class Delta {
    private static long lastTime = System.nanoTime();

    public static float getDeltaTime() {
        long current = System.nanoTime();
        float delta = (current - lastTime) / 1000000000f;
        lastTime = current;
        return delta;
    }
}

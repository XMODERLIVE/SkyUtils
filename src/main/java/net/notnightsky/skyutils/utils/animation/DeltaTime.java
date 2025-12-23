package net.notnightsky.skyutils.utils.animation;

public class DeltaTime {
    private static long lastTime = System.nanoTime();

    public static float getDeltaTime() {
        long current = System.nanoTime();
        float delta = (current - lastTime) / 1_000_000_000.0f; // Convert nanoseconds to seconds
        lastTime = current;
        return Math.min(delta, 0.1f); // Cap delta time to prevent large jumps
    }
}
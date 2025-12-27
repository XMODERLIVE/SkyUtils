package net.notnightsky.skyutils.utils.animation;

public class DeltaTime {
    private static long lastFrame = System.nanoTime();
    private static float deltaTime;

    public static void update() {
        long now = System.nanoTime();
        deltaTime = (now - lastFrame) / 1_000_000f;
        lastFrame = now;

        if (deltaTime > 50f) deltaTime = 50f;
    }

    public static float get() {
        return deltaTime;
    }
}

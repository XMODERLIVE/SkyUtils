package net.notnightsky.skyutils.hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.notnightsky.skyutils.SkyutilsClient;

public class FpsHud extends AbstractHudEntry {

    public static final Identifier ID = Identifier.of(SkyutilsClient.modID, "fpshud");

    private boolean enabled = true;
    private long lastUpdate = 0;
    private int lastFps = 0;

    public FpsHud() {
        super(60, 20); // Set initial width and height
    }

    @Override
    public void renderComponent(DrawContext context, float delta) {
        updateFps();

        String fpsText = "FPS: " + lastFps;

        // Get the position for rendering
        int hudX = getRawX();
        int hudY = getRawY();

        // Simple rendering of FPS
        int textX = hudX + 2; // Offset from the HUD position
        int textY = hudY + 2;

        context.drawTextWithShadow(
            client.textRenderer,
            fpsText,
            textX,
            textY,
            0xFFFFFFFF // White color
        );
    }

    private void updateFps() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdate >= 1000) { // Update every second
            lastFps = client.getCurrentFps();
            lastUpdate = currentTime;
        }
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean value) {
        this.enabled = value;
    }
}
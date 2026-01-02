package net.notnightsky.skyutils.hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.notnightsky.skyutils.SkyutilsClient;

public class CoordsHud extends AbstractHudEntry {

    public static final Identifier ID = Identifier.of(SkyutilsClient.modID, "coordshud");

    private boolean enabled = true;

    public CoordsHud() {
        super(100, 40); // Set initial width and height
    }

    @Override
    public void renderComponent(DrawContext context, float delta) {
        if (client.player != null) {
            double x = client.player.getX();
            double y = client.player.getY();
            double z = client.player.getZ();

            String coordText = String.format("X: %.2f\nY: %.2f\nZ: %.2f", x, y, z);

            // Get the position for rendering
            int hudX = getRawX();
            int hudY = getRawY();

            // Simple rendering of coordinates
            int textX = hudX + 2; // Offset from the HUD position
            int textY = hudY + 2;

            String[] lines = coordText.split("\n");
            for (int i = 0; i < lines.length; i++) {
                context.drawTextWithShadow(
                    client.textRenderer,
                    lines[i],
                    textX,
                    textY + i * 10,
                    0xFFFFFFFF // White color
                );
            }
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
package net.notnightsky.skyutils.modules.deathcords;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class deathCoordWiget extends ButtonWidget {
    public deathCoordWiget(int x, int y, int width, int height) {
        super(x, y, width, height, Text.literal("Copy Death Coords"), button -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null) {
                String coords = String.format("%.0f, %.0f, %.0f",
                    client.player.getX(), client.player.getY(), client.player.getZ());
                client.keyboard.setClipboard(coords);
                client.player.sendMessage(Text.literal("Death coordinates copied to clipboard"), false);

                button.setFocused(false);
            }
        }, DEFAULT_NARRATION_SUPPLIER);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderWidget(context, mouseX, mouseY, delta);

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        int textColor = this.active ? 0xFFFFFF : 0x808080;
        int textX = this.getX() + (this.width - textRenderer.getWidth(this.getMessage())) / 2;
        int textY = this.getY() + (this.height - 8) / 2;
        context.drawTextWithShadow(textRenderer, this.getMessage(), textX, textY, textColor);
    }

    @Override
    public void appendClickableNarrations(NarrationMessageBuilder builder) {
        this.appendDefaultNarrations(builder);
    }
}
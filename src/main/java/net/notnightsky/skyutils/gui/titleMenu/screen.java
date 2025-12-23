package net.notnightsky.skyutils.gui.titleMenu;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.notnightsky.skyutils.gui.titleMenu.widget.AbstractIconButton;
import net.notnightsky.skyutils.gui.titleMenu.widget.AbstractTextButton;
import net.notnightsky.skyutils.utils.font.FontRenderer;
import net.notnightsky.skyutils.utils.font.fontSizes;

public class screen extends Screen {
    private static final Identifier CROSS_ICON = Identifier.of("skyutils", "icon/cross.png");

    public screen() {
        super(Text.literal("SkyUtils Menu"));
    }

    @Override
    protected void init() {
        int centerX = width / 2;
        int centerY = height / 2;

        int buttonWidth = 140;
        int buttonHeight = 22;
        int spacing = 26;

        // Singleplayer button
        addDrawableChild(new AbstractTextButton(
                centerX - buttonWidth / 2,
                centerY - 10,
                buttonWidth,
                buttonHeight,
                Text.literal("Singleplayer"),
                () -> {
                    if (client != null) {
                        client.setScreen(new SelectWorldScreen(this));
                    }
                }));

        // Multiplayer button
        addDrawableChild(new AbstractTextButton(
                centerX - buttonWidth / 2,
                centerY + spacing - 10,
                buttonWidth,
                buttonHeight,
                Text.literal("Multiplayer"),
                () -> {
                    if (client != null) {
                        client.setScreen(new MultiplayerScreen(this));
                    }
                }));

        // Options button
        addDrawableChild(new AbstractTextButton(
                centerX - buttonWidth / 2,
                centerY + spacing * 2 - 10,
                buttonWidth,
                buttonHeight,
                Text.literal("Options"),
                () -> {
                    if (client != null) {
                        client.setScreen(new OptionsScreen(this, client.options));
                    }
                }));

        // Close/Exit button (top-right)
        addDrawableChild(new AbstractIconButton(
                width - 28,
                12,
                20,
                Identifier.of("skyutils", "icon/cross.png"),
                () -> {
                    if (client != null) {
                        client.scheduleStop();
                    }
                }));
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        // Semi-transparent dark background
        ctx.fill(0, 0, width, height, 0x88000000);
        drawHeader(ctx);

        // Render all widgets
        super.render(ctx, mouseX, mouseY, delta);
        drawCopyright(ctx);
    }

    private void drawDebugGrid(DrawContext ctx) {
        // Draw vertical lines every 50px
        for (int x = 0; x < width; x += 50) {
            ctx.fill(x, 0, x + 1, height, 0x33FFFFFF);
        }
        // Draw horizontal lines every 50px
        for (int y = 0; y < height; y += 50) {
            ctx.fill(0, y, width, y + 1, 0x33FFFFFF);
        }
    }

    private void drawHeader(DrawContext ctx) {
        int centerX = width / 2;

        // Draw icon (if texture exists)
        // Note: Make sure this texture path is correct
        ctx.drawTexture(RenderPipelines.GUI_TEXTURED,CROSS_ICON, centerX - 8, height / 2 - 70, 0, 0, 16, 16, 16, 16);

        // Draw title
        ctx.drawCenteredTextWithShadow(
                MinecraftClient.getInstance().textRenderer,
                Text.literal("SkyUtils"),
                centerX,
                height / 2 - 45,
                0xFFFFFFFF);
    }
    private void drawCopyright(DrawContext ctx) {
        String copyright = "Copyright Mojang Studios. Do not distribute!";
        FontRenderer.drawText(ctx, copyright,width - FontRenderer.getStringWidth(copyright, fontSizes.MEDIUM), height - 24,0x50ffffff, fontSizes.MEDIUM);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true; // Allow ESC to close the screen
    }
}
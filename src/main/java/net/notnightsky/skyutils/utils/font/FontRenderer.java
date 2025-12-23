package net.notnightsky.skyutils.utils.font;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.text.TextColor;
import net.minecraft.text.Style;
import java.util.List;

public class FontRenderer {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    // === TEXT RENDERING METHODS ===

    // Basic method with RGB color
    public static void drawText(DrawContext context, String text, int x, int y,
                                int rgbColor, fontSizes fontSize) {
        // Create colored text
        Text coloredText = Text.literal(text)
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(rgbColor)));

        context.drawText(
                client.textRenderer,
                coloredText,
                x, y,
                rgbColor,
                false
        );
    }

    public static void drawText(DrawContext context, Text text, int x, int y,
                                int rgbColor, fontSizes fontSize) {
        // Apply custom color to the text
        Text coloredText = text.copy().setStyle(
                text.getStyle().withColor(TextColor.fromRgb(rgbColor))
        );

        context.drawText(
                client.textRenderer,
                coloredText,
                x, y,
                rgbColor,
                false
        );
    }

    public static void drawText(DrawContext context, OrderedText text, int x, int y,
                                int rgbColor, fontSizes fontSize) {
        // Draw OrderedText directly
        context.drawText(
                client.textRenderer,
                text,
                x, y,
                rgbColor,
                false
        );
    }

    // Method with Minecraft formatting
    public static void drawText(DrawContext context, String text, int x, int y,
                                Formatting formatting, fontSizes fontSize) {
        Text styledText = Text.literal(text).formatted(formatting);
        context.drawText(
                client.textRenderer,
                styledText,
                x, y,
                getColorFromFormatting(formatting),
                false
        );
    }

    // === WIDTH MEASUREMENT METHODS ===

    public static int getStringWidth(String text, fontSizes fontSize) {
        return client.textRenderer.getWidth(text);
    }

    public static int getStringWidth(Text text, fontSizes fontSize) {
        return client.textRenderer.getWidth(text);
    }

    public static int getStringWidth(OrderedText text, fontSizes fontSize) {
        return client.textRenderer.getWidth(text);
    }

    // === CHARACTER POSITION METHODS (SIMPLIFIED) ===

    /**
     * Get character position at specific X coordinate
     * Simple implementation that doesn't rely on TextHandler
     */
    public static int getCharacterPosition(String text, fontSizes fontSize, int mouseX, int textX) {
        TextRenderer textRenderer = client.textRenderer;

        // Simple implementation: iterate through characters to find position
        float relativeX = mouseX - textX;
        float currentX = 0;

        for (int i = 0; i < text.length(); i++) {
            String charStr = text.substring(i, i + 1);
            float charWidth = textRenderer.getWidth(charStr);

            // Check if mouse is within this character's bounds
            if (relativeX >= currentX && relativeX < currentX + charWidth) {
                // Determine if closer to left or right edge
                float charCenter = currentX + charWidth / 2;
                return relativeX < charCenter ? i : i + 1;
            }

            currentX += charWidth;
        }

        // If past the end of text, return text length
        return text.length();
    }

    /**
     * Alternative: Get approximate position using substring widths
     */
    public static int getCharacterPositionSimple(String text, fontSizes fontSize, int mouseX, int textX) {
        TextRenderer textRenderer = client.textRenderer;
        float relativeX = mouseX - textX;

        if (relativeX <= 0) return 0;

        float totalWidth = textRenderer.getWidth(text);
        if (relativeX >= totalWidth) return text.length();

        // Binary search for character position
        int low = 0;
        int high = text.length();

        while (low < high) {
            int mid = (low + high) / 2;
            float width = textRenderer.getWidth(text.substring(0, mid));

            if (width < relativeX) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        // Adjust for character boundaries
        if (low > 0) {
            float widthBefore = textRenderer.getWidth(text.substring(0, low - 1));
            float widthAt = textRenderer.getWidth(text.substring(0, low));
            float charWidth = widthAt - widthBefore;

            // If closer to previous character
            if (relativeX - widthBefore < charWidth / 2) {
                return low - 1;
            }
        }

        return low;
    }

    /**
     * Get X position of a specific character index
     */
    public static int getXPositionAtIndex(String text, fontSizes fontSize, int charIndex, int startX) {
        if (charIndex <= 0) return startX;
        if (charIndex > text.length()) charIndex = text.length();

        TextRenderer textRenderer = client.textRenderer;
        String substring = text.substring(0, charIndex);
        return startX + textRenderer.getWidth(substring);
    }

    // === TEXT TRIMMING METHODS ===

    public static String trimToWidth(String text, fontSizes fontSize, int maxWidth) {
        return trimToWidth(text, fontSize, maxWidth, "...");
    }

    public static String trimToWidth(String text, fontSizes fontSize, int maxWidth, String suffix) {
        TextRenderer textRenderer = client.textRenderer;
        int suffixWidth = textRenderer.getWidth(suffix);
        int availableWidth = maxWidth - suffixWidth;

        if (availableWidth <= 0) return suffix;

        return textRenderer.trimToWidth(text, availableWidth) + suffix;
    }

    // === CENTERING UTILITIES ===

    public static void drawCenteredText(DrawContext context, String text, int centerX, int y,
                                        int rgbColor, fontSizes fontSize) {
        int width = getStringWidth(text, fontSize);
        drawText(context, text, centerX - width / 2, y, rgbColor, fontSize);
    }

    public static void drawCenteredText(DrawContext context, Text text, int centerX, int y,
                                        int rgbColor, fontSizes fontSize) {
        int width = getStringWidth(text, fontSize);
        drawText(context, text, centerX - width / 2, y, rgbColor, fontSize);
    }

    public static void drawCenteredText(DrawContext context, OrderedText text, int centerX, int y,
                                        int rgbColor, fontSizes fontSize) {
        int width = getStringWidth(text, fontSize);
        context.drawText(
                client.textRenderer,
                text,
                centerX - width / 2, y,
                rgbColor,
                false
        );
    }

    public static void drawRightAligned(DrawContext context, String text,
                                        int rightX, int y,
                                        int color, fontSizes fontSize) {
        int width = getStringWidth(text, fontSize);
        drawText(context, text, rightX - width, y, color, fontSize);
    }

    // === MULTI-LINE TEXT SUPPORT ===

    public static int getMultilineHeight(String text, fontSizes fontSize, int maxWidth) {
        TextRenderer textRenderer = client.textRenderer;
        return textRenderer.getWrappedLinesHeight(text, maxWidth);
    }

    public static List<OrderedText> wrapLines(String text, int maxWidth) {
        TextRenderer textRenderer = client.textRenderer;
        return textRenderer.wrapLines(Text.literal(text), maxWidth);
    }

    public static void drawWrappedText(DrawContext context, String text,
                                       int x, int y, int maxWidth,
                                       int color, fontSizes fontSize) {
        TextRenderer textRenderer = client.textRenderer;
        List<OrderedText> lines = textRenderer.wrapLines(Text.literal(text), maxWidth);

        for (int i = 0; i < lines.size(); i++) {
            context.drawText(
                    textRenderer,
                    lines.get(i),
                    x, y + (i * 9),
                    color,
                    false
            );
        }
    }

    // === SHADOW EFFECTS ===

    public static void drawTextWithShadow(DrawContext context, String text, int x, int y,
                                          int rgbColor, fontSizes fontSize) {
        Text coloredText = Text.literal(text)
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(rgbColor)));

        context.drawText(
                client.textRenderer,
                coloredText,
                x, y,
                rgbColor,
                true
        );
    }

    public static void drawTextWithShadow(DrawContext context, Text text, int x, int y,
                                          int rgbColor, fontSizes fontSize) {
        Text coloredText = text.copy().setStyle(
                text.getStyle().withColor(TextColor.fromRgb(rgbColor))
        );

        context.drawText(
                client.textRenderer,
                coloredText,
                x, y,
                rgbColor,
                true
        );
    }

    public static void drawTextWithShadow(DrawContext context, OrderedText text, int x, int y,
                                          int rgbColor, fontSizes fontSize) {
        context.drawText(
                client.textRenderer,
                text,
                x, y,
                rgbColor,
                true
        );
    }

    // === COLOR UTILITIES ===

    private static int getColorFromFormatting(Formatting formatting) {
        return formatting.getColorValue() != null ? formatting.getColorValue() : 0xFFFFFF;
    }

    public static class Colors {
        public static final int WHITE = 0xFFFFFF;
        public static final int RED = 0xFF0000;
        public static final int GREEN = 0x00FF00;
        public static final int BLUE = 0x0000FF;
        public static final int YELLOW = 0xFFFF00;
        public static final int PURPLE = 0xFF00FF;
        public static final int CYAN = 0x00FFFF;
        public static final int ORANGE = 0xFFA500;
        public static final int GRAY = 0x808080;
        public static final int DARK_RED = 0x8B0000;
        public static final int DARK_GREEN = 0x006400;
        public static final int GOLD = 0xFFD700;

        public static int rainbow(float progress) {
            float hue = (progress % 1.0f) * 6.0f;
            float c = 1.0f;
            float x = (1.0f - Math.abs((hue % 2.0f) - 1.0f)) * c;

            float r = 0, g = 0, b = 0;

            if (hue < 1) { r = c; g = x; }
            else if (hue < 2) { r = x; g = c; }
            else if (hue < 3) { g = c; b = x; }
            else if (hue < 4) { g = x; b = c; }
            else if (hue < 5) { r = x; b = c; }
            else { r = c; b = x; }

            return ((int)(r * 255) << 16) | ((int)(g * 255) << 8) | (int)(b * 255);
        }
    }

    // === UTILITY METHODS ===

    /**
     * Check if text fits within a given width
     */
    public static boolean fitsInWidth(String text, fontSizes fontSize, int maxWidth) {
        return getStringWidth(text, fontSize) <= maxWidth;
    }

    /**
     * Create a blinking cursor effect
     */
    public static void drawCursor(DrawContext context, int x, int y, int tickCounter) {
        if ((tickCounter / 10) % 2 == 0) {
            context.fill(x, y, x + 1, y + 9, 0xFFFFFFFF);
        }
    }

    /**
     * Draw text selection highlight
     */
    public static void drawSelection(DrawContext context, String text, fontSizes fontSize,
                                     int startIndex, int endIndex, int textX, int textY) {
        if (startIndex == endIndex) return;

        int leftIndex = Math.min(startIndex, endIndex);
        int rightIndex = Math.max(startIndex, endIndex);

        int leftX = getXPositionAtIndex(text, fontSize, leftIndex, textX);
        int rightX = getXPositionAtIndex(text, fontSize, rightIndex, textX);

        context.fill(leftX, textY, rightX, textY + 9, 0x800000FF);
    }
}
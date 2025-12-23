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

    public static int getFontHeight(){
        return client.textRenderer.fontHeight;
    }

    public static void drawText(DrawContext context, String text, int x, int y, int rgbColor) {
        Text coloredText = Text.literal(text).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(rgbColor)));

        context.drawText(client.textRenderer, coloredText, x, y, rgbColor, false);
    }

    public static void drawText(DrawContext context, Text text, int x, int y, int rgbColor) {
        Text coloredText = text.copy().setStyle(text.getStyle().withColor(TextColor.fromRgb(rgbColor)));

        context.drawText(client.textRenderer, coloredText, x, y, rgbColor, false);
    }

    public static void drawText(DrawContext context, OrderedText text, int x, int y, int rgbColor) {
        context.drawText(client.textRenderer, text, x, y, rgbColor, false);
    }

    public static int getStringWidth(String text) {
        return client.textRenderer.getWidth(text);
    }

    public static int getStringWidth(Text text) {
        return client.textRenderer.getWidth(text);
    }

    public static int getStringWidth(OrderedText text) {
        return client.textRenderer.getWidth(text);
    }
    public static int getCharacterPosition(String text , int mouseX, int textX) {
        TextRenderer textRenderer = client.textRenderer;

        float relativeX = mouseX - textX;
        float currentX = 0;

        for (int i = 0; i < text.length(); i++) {
            String charStr = text.substring(i, i + 1);
            float charWidth = textRenderer.getWidth(charStr);

            if (relativeX >= currentX && relativeX < currentX + charWidth) {
                float charCenter = currentX + charWidth / 2;
                return relativeX < charCenter ? i : i + 1;
            }

            currentX += charWidth;
        }
        return text.length();
    }

    public static int getXPositionAtIndex(String text, int charIndex, int startX) {
        if (charIndex <= 0) return startX;
        if (charIndex > text.length()) charIndex = text.length();

        TextRenderer textRenderer = client.textRenderer;
        String substring = text.substring(0, charIndex);
        return startX + textRenderer.getWidth(substring);
    }

    public static String trimToWidth(String text , int maxWidth) {
        return trimToWidth(text, maxWidth, "...");
    }

    public static String trimToWidth(String text , int maxWidth, String suffix) {
        TextRenderer textRenderer = client.textRenderer;
        int suffixWidth = textRenderer.getWidth(suffix);
        int availableWidth = maxWidth - suffixWidth;

        if (availableWidth <= 0) return suffix;

        return textRenderer.trimToWidth(text, availableWidth) + suffix;
    }

    public static void drawCenteredText(DrawContext context, String text, int centerX, int y,
                                        int rgbColor) {
        int width = getStringWidth(text);
        drawText(context, text, centerX - width / 2, y, rgbColor);
    }

    public static void drawCenteredText(DrawContext context, Text text, int centerX, int y,
                                        int rgbColor) {
        int width = getStringWidth(text);
        drawText(context, text, centerX - width / 2, y, rgbColor);
    }

    public static void drawCenteredText(DrawContext context, OrderedText text, int centerX, int y,
                                        int rgbColor) {
        int width = getStringWidth(text);
        context.drawText(client.textRenderer, text, centerX - width / 2, y, rgbColor, false);
    }

    public static void drawRightAligned(DrawContext context, String text, int rightX, int y, int color) {
        int width = getStringWidth(text);
        drawText(context, text, rightX - width, y, color );
    }

    public static int getMultilineHeight(String text, int maxWidth) {
        TextRenderer textRenderer = client.textRenderer;
        return textRenderer.getWrappedLinesHeight(text, maxWidth);
    }

    public static List<OrderedText> wrapLines(String text, int maxWidth) {
        TextRenderer textRenderer = client.textRenderer;
        return textRenderer.wrapLines(Text.literal(text), maxWidth);
    }

    public static void drawWrappedText(DrawContext context, String text,
                                       int x, int y, int maxWidth,
                                       int color) {
        TextRenderer textRenderer = client.textRenderer;
        List<OrderedText> lines = textRenderer.wrapLines(Text.literal(text), maxWidth);

        for (int i = 0; i < lines.size(); i++) {
            context.drawText(textRenderer, lines.get(i), x, y + (i * 9), color, false);
        }
    }

    public static void drawTextWithShadow(DrawContext context, String text, int x, int y,
                                          int rgbColor) {
        Text coloredText = Text.literal(text).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(rgbColor)));

        context.drawText(client.textRenderer, coloredText, x, y, rgbColor, true);
    }

    public static void drawTextWithShadow(DrawContext context, Text text, int x, int y, int rgbColor ) {
        Text coloredText = text.copy().setStyle(text.getStyle().withColor(TextColor.fromRgb(rgbColor)));

        context.drawText(client.textRenderer, coloredText, x, y, rgbColor, true);
    }

    public static void drawTextWithShadow(DrawContext context, OrderedText text, int x, int y, int rgbColor ) {
        context.drawText(client.textRenderer, text, x, y, rgbColor, true);
    }

    private static int getColorFromFormatting(Formatting formatting) {
        return formatting.getColorValue() != null ? formatting.getColorValue() : 0xFFFFFF;
    }

    public static boolean fitsInWidth(String text, int maxWidth) {
        return getStringWidth(text) <= maxWidth;
    }

    public static void drawSelection(DrawContext context, String text, int startIndex, int endIndex, int textX, int textY) {
        if (startIndex == endIndex) return;

        int leftIndex = Math.min(startIndex, endIndex);
        int rightIndex = Math.max(startIndex, endIndex);

        int leftX = getXPositionAtIndex(text, leftIndex, textX);
        int rightX = getXPositionAtIndex(text, rightIndex, textX);

        context.fill(leftX, textY, rightX, textY + 9, 0x800000FF);
    }
}
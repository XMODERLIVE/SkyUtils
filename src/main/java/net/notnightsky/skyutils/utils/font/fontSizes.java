package net.notnightsky.skyutils.utils.font;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.text.TextColor;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

public enum fontSizes {
    SMALL("inter18light"),
    MEDIUM("inter24light"),
    LARGE("inter28light");

    public final Identifier id;

    fontSizes(String path) {
        this.id = Identifier.of("skyutils", path);
    }

    // Helper method to get colored text
    public static Text getTextWithColor(String text, int rgbColor, fontSizes size) {
        return Text.literal(text)
                .setStyle(Style.EMPTY
                        .withColor(TextColor.fromRgb(rgbColor))
                );
    }

    public static Text getTextWithFormatting(String text, Formatting formatting, fontSizes size) {
        return Text.literal(text).formatted(formatting);
    }
}
package net.notnightsky.skyutils.utils.font;


import net.minecraft.util.Identifier;

public enum fontSizes {
    SMALL("inter18light"),
    MEDIUM("inter24light"),
    LARGE("inter28light");

    public final Identifier id;

    fontSizes(String path) {
        this.id = Identifier.of("skyutils", path);
    }
}

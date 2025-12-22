package net.notnightsky.skyutils.utils.render;

public class miscHelper {
    public static boolean withinBox(int x, int y, int w, int h, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }
}

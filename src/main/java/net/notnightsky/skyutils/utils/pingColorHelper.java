package net.notnightsky.skyutils.utils;

public class pingColorHelper {
    public static String pingColor(int ping) {
        if (ping <= 60) return "§a";
        if (ping <= 120) return "§e";
        if (ping <= 200) return "§6";
        return "§c";
    }
}

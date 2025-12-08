package net.notnightsky.skyutils.modules.discordRpc;

import meteordevelopment.discordipc.DiscordIPC;
import meteordevelopment.discordipc.RichPresence;
import net.notnightsky.skyutils.config.modConfig;

import java.time.Instant;

public class IPCManager {
    public static final long CLIENT_ID = 1447589690256396480L;

    private static boolean running = false;

    public static void startIfEnabled() {
        if (running) return;

        if (!modConfig.IPCEnabled)
            return;

        boolean ok = DiscordIPC.start(CLIENT_ID, () ->
                System.out.println("Logged into Discord as: " + DiscordIPC.getUser().username)
        );

        if (!ok) {
            System.err.println("[IPC] Failed to connect to Discord");
            return;
        }

        running = true;
        updatePresence();
    }

    public static void stop() {
        if (!running) return;
        DiscordIPC.stop();
        running = false;
    }

    public static void updatePresence() {
        if (!running) return;

        RichPresence presence = new RichPresence();
        presence.setDetails("Playing Minecraft");
        presence.setState("Fabric 1.21.10");
        presence.setLargeImage("large", "Minecraft");
        presence.setSmallImage("small", "Fabric");
        presence.setStart(Instant.now().getEpochSecond());

        DiscordIPC.setActivity(presence);
    }

    public static void reload() {
        if (modConfig.IPCEnabled) {
            startIfEnabled();
        } else {
            stop();
        }
    }
}

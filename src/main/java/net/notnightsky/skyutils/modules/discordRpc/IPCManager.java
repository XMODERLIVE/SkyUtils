package net.notnightsky.skyutils.modules.discordRpc;

import meteordevelopment.discordipc.DiscordIPC;
import meteordevelopment.discordipc.RichPresence;
import net.notnightsky.skyutils.SkyutilsClient;
import net.notnightsky.skyutils.config.modConfig;

import java.time.Instant;

public class IPCManager {
    static RichPresence presence = new RichPresence();
    public static final long CLIENT_ID = 1447589690256396480L;

    private static boolean running = false;

    public static void startIfEnabled() {
        if (running) return;

        if (!modConfig.IPCEnabled)
            return;

        boolean ok = DiscordIPC.start(CLIENT_ID, () -> SkyutilsClient.LOGGER.info("Logged into Discord as: {}", DiscordIPC.getUser().username));

        if (!ok) {
            SkyutilsClient.LOGGER.error("[IPC] Failed to connect to Discord");
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
        presence.setDetails(modConfig.IPCplayText);
        presence.setState("Fabric 1.21.10");
        presence.setLargeImage("large", "Minecraft");
        presence.setSmallImage("small", "Fabric");
        presence.setStart(Instant.now().getEpochSecond());

        DiscordIPC.setActivity(presence);
    }

    public static void updateCurrentPresence(){
        presence.setDetails(modConfig.IPCplayText);
        presence.setState("Fabric 1.21.10");

        DiscordIPC.setActivity(presence);
    }

    public static void reload() {
        if (modConfig.IPCEnabled && !running) {
            startIfEnabled();
        } else if (modConfig.IPCEnabled) {
            updateCurrentPresence();
        } else {
            stop();
        }
    }
}

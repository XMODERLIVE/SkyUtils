package net.notnightsky.skyutils;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.notnightsky.skyutils.config.keyBindingHelper.keyBinding;
import net.notnightsky.skyutils.config.keyBindingHelper.toggleHandler;
import net.notnightsky.skyutils.config.modConfig;
import net.notnightsky.skyutils.modmenu.modMenuIntegration;
import net.notnightsky.skyutils.modules.discordRpc.IPCManager;
import net.notnightsky.skyutils.modules.fullbright.fullBright;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyutilsClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("SkyUtils");
    public static final String modID = "skyutils";
    public static final String modName = "SkyUtils";
    public static final String modVersion = "1.3.1";
    public static final String mcVer = "[" + MinecraftClient.getInstance().getGameVersion() + " " + SharedConstants.getGameVersion().id() + "]";

    @Override
    public void onInitializeClient() {
        LOGGER.info("Loaded SkyUtils");
        modConfig.HANDLER.load();
        IPCManager.startIfEnabled();
        keyBinding.registerKeybinds();
        toggleHandler.registerToggle();
        new modMenuIntegration();
        ClientTickEvents.END_CLIENT_TICK.register(client -> new fullBright().noDarknessEffect());
    }
}
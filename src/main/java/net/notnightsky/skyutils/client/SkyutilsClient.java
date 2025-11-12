package net.notnightsky.skyutils.client;

import net.fabricmc.api.ClientModInitializer;
import net.notnightsky.skyutils.config.keyBindingHelper.keyBinding;
import net.notnightsky.skyutils.config.keyBindingHelper.toggleHandler;
import net.notnightsky.skyutils.config.modConfig;
import net.notnightsky.skyutils.modmenu.modMenuIntegration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyutilsClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("fullbright");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Loaded SkyUtils");
        modConfig.HANDLER.load();
        keyBinding.registerKeybinds();
        toggleHandler.registerToggle();
        new modMenuIntegration();
    }
}
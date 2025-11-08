package net.notnightsky.skyutils;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Skyutils implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("SkyUtils");
    public static final String translationKey = "skyutils";
    public static MinecraftClient MC = MinecraftClient.getInstance();

    @Override
    public void onInitialize() {
    }
}

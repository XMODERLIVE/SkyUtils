package net.notnightsky.skyutils.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import net.notnightsky.skyutils.config.keyBindingHelper.keybinding;
import net.notnightsky.skyutils.config.modConfig;
import net.notnightsky.skyutils.modmenu.modMenuIntegration;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class SkyutilsClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("fullbright");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Loaded SkyUtils");
        modConfig.HANDLER.load();
        keybinding.register();
    }
}

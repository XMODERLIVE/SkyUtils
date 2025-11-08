package net.notnightsky.skyutils.config.keyBindingHelper;

import com.google.common.base.Function;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import net.notnightsky.skyutils.config.modConfig;
import net.notnightsky.skyutils.modmenu.modMenuIntegration;
import org.lwjgl.glfw.GLFW;

public class keybinding {
    public static KeyBinding openMenu;
    public static KeyBinding toggleFullBright;
    private static boolean loaded = false;
    private final modMenuIntegration modMenu = new modMenuIntegration();
    private boolean openConfigScreen = false;

    public static void register(){
        KeyBinding.Category skyutilsCategory = KeyBinding.Category.create(Identifier.of("skyutils"));
        
        toggleFullBright = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.skyutils.toggleFullBright",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_ALT,
                skyutilsCategory
        ));

        openMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
           "key.skyutils.openMenu",
           InputUtil.Type.KEYSYM,
           GLFW.GLFW_KEY_B,
           skyutilsCategory
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(!loaded && MinecraftClient.getInstance().options != null) {
                loaded = true;
                if(modConfig.fullBright) {
                    enableFullbright();
                }
            }

            if (openMenu.wasPressed()) {
                Screen configScreen = modConfig.openConfigScreen(MinecraftClient.getInstance().currentScreen);
                MinecraftClient.getInstance().setScreen(configScreen);
            }

            while (toggleFullBright.wasPressed()) {
                toggleFullbright();
            }
        });
    }

    public static void enableFullbright() {
        MinecraftClient.getInstance().options.getGamma().setValue(modConfig.gamma / 100.0);
        modConfig.fullBright = true;
    }

    public static void disableFullbright() {
        MinecraftClient.getInstance().options.getGamma().setValue(modConfig.defaultGamma / 100.0);
        modConfig.fullBright = false;
    }

    public static void toggleFullbright() {
        if (modConfig.fullBright) {
            disableFullbright();
        } else {
            enableFullbright();
        }
        modConfig.HANDLER.save();
    }

    public static void setFullbright(boolean fullbright) {
        if (fullbright) {
            enableFullbright();
        } else {
            disableFullbright();
        }
        modConfig.HANDLER.save();
    }
}

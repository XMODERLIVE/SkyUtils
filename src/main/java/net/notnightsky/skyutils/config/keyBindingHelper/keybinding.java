package net.notnightsky.skyutils.config.keyBindingHelper;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.notnightsky.skyutils.config.modConfig;

import org.lwjgl.glfw.GLFW;

public class keybinding {
    public static KeyBinding openMenu;
    public static KeyBinding toggleFullBright;
    public static KeyBinding incrementFullBright;
    public static KeyBinding decrementFullBright;
    private static boolean loaded = false;

    public static void register(){
        KeyBinding.Category skyutilsCategory = KeyBinding.Category.create(Identifier.of("skyutils"));
        openMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.skyutils.openMenu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                skyutilsCategory
        ));

        toggleFullBright = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.skyutils.toggleFullBright",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_ALT,
                skyutilsCategory
        ));

        incrementFullBright = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.skyutils.incrementFullBright",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_ADD,
                skyutilsCategory
        ));

        decrementFullBright = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.skyutils.decrementFullBright",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_SUBTRACT,
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

            if (incrementFullBright.wasPressed()){
                incrementFullBright();
            }

            if (decrementFullBright.wasPressed()){
                decrementFullBright();
            }

            while (toggleFullBright.wasPressed()) {
                toggleFullbright();
            }
        });
    }

    public static void enableFullbright(){
        MinecraftClient.getInstance().options.getGamma().setValue(modConfig.gamma / 100.0);
        if (MinecraftClient.getInstance().player != null){
            MinecraftClient.getInstance().player.sendMessage(Text.of("Gamma Value is Set to " + (int)modConfig.gamma + "%"), true);
        }
        modConfig.fullBright = true;
    }

    public static void disableFullbright(){
        MinecraftClient.getInstance().options.getGamma().setValue(modConfig.defaultGamma / 100.0);
        if (MinecraftClient.getInstance().player != null){
            MinecraftClient.getInstance().player.sendMessage(Text.of("Gamma Value is Set to " + (int)modConfig.defaultGamma + "%"), true);
        }
        modConfig.fullBright = false;
    }

    public static void incrementFullBright(){
            if(modConfig.fullBright){
                if( !(modConfig.gamma > 1000.0 || modConfig.gamma + modConfig.increment > 1000.0)) {
                    modConfig.gamma = modConfig.gamma + modConfig.increment;
                    enableFullbright();
                } else if (modConfig.gamma <= 1000.0){
                    modConfig.gamma = 1000.0;
                    if (MinecraftClient.getInstance().player != null){
                        MinecraftClient.getInstance().player.sendMessage(Text.of("Gamma Value Reached 1000%"), true);
                    }
                }
                modConfig.HANDLER.save();
            }
    }

    public static void decrementFullBright(){
        if(modConfig.fullBright){
            if( !(modConfig.gamma < 0.0 || modConfig.gamma - modConfig.increment < 0.0)) {
                modConfig.gamma = modConfig.gamma - modConfig.decrement;
                enableFullbright();
            } else if (modConfig.gamma < 0.0){
                modConfig.gamma = 0.0;
                if (MinecraftClient.getInstance().player != null){
                    MinecraftClient.getInstance().player.sendMessage(Text.of("Gamma Value Reached 1%"), true);
                }
            }
            modConfig.HANDLER.save();
        }
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

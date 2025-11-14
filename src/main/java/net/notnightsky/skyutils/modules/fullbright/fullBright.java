package net.notnightsky.skyutils.modules.fullbright;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import net.notnightsky.skyutils.config.modConfig;

public class fullBright {
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

    public void noDarknessEffect(){
        if (modConfig.nodarkness){
            if (MinecraftClient.getInstance().player != null) {
                StatusEffectInstance darknessEffect = MinecraftClient.getInstance().player.getStatusEffect(StatusEffects.DARKNESS);
                if (darknessEffect != null) {
                    MinecraftClient.getInstance().player.removeStatusEffect(StatusEffects.DARKNESS);
                }
            }
        }
    }
}

package net.notnightsky.skyutils.modules.zoom;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import net.notnightsky.skyutils.config.keyBindingHelper.keyBinding;
import net.notnightsky.skyutils.utils.DeltaTime;
import net.notnightsky.skyutils.utils.math;

public class zoomHelper {

    private static final double defaultZoom = 3.0;
    private static final double lerpSpeed = 12.0;

    private static double baseZoom = 1.0;
    private static double scrollZoom = 1.0;
    private static double scrollVelocity = 0.0;

    private static double currentLevel = 1.0;
    private static double targetLevel  = 1.0;

    private static Double defaultMouseSensitivity;

    public static float changeFovOnZoom(float fov) {
        SimpleOption<Double> mouseSensitivitySetting = MinecraftClient.getInstance().options.getMouseSensitivity();
        baseZoom = keyBinding.zoomKey.isPressed() ? defaultZoom : 1.0;
        if(keyBinding.zoomKey.isPressed()){

            baseZoom = defaultZoom;
        } else {
            baseZoom = 1.0;
            if(defaultMouseSensitivity != null)
            {
                mouseSensitivitySetting.setValue(defaultMouseSensitivity);
                defaultMouseSensitivity = null;
            }
        }

        if(defaultMouseSensitivity == null)
            defaultMouseSensitivity = mouseSensitivitySetting.getValue();

        mouseSensitivitySetting.setValue(defaultMouseSensitivity * (1.0 / currentLevel));

        return (float) (fov / currentLevel);
    }

    public static void onMouseScroll(double vert) {
        if (!keyBinding.zoomKey.isPressed()) return;
        scrollVelocity += vert * 2.5;
    }

    public static void tick() {
        double dt = DeltaTime.getSec();

        if (Math.abs(scrollVelocity) > 1e-4) {
            scrollZoom *= Math.exp(scrollVelocity * dt);
            scrollZoom = Math.clamp(scrollZoom, 1.0 / defaultZoom, 50.0 / defaultZoom);
        }

        scrollVelocity *= Math.exp(-12.0 * dt);

        if (!keyBinding.zoomKey.isPressed()) {
            scrollZoom = 1.0;
        }

        targetLevel = baseZoom * scrollZoom;

        currentLevel = math.lerpLog(currentLevel, targetLevel, lerpSpeed, dt);
    }
}

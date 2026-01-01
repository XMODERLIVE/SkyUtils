package net.notnightsky.skyutils.utils;

import net.notnightsky.skyutils.modules.zoom.InterpolationMode;

public final class math {

    public static double interpolate(double start, double end, double speed, double delta, InterpolationMode mode) {
        double t = 1.0 - Math.exp(-speed * delta);
        t = Math.clamp(t, 0.0, 1.0);
        double result;

        return switch (mode) {
            case LINEAR -> lerp(start, end, t);
            case LOGARITHMIC -> lerpLog(start, end, t);
            case EASEINOUTSINE -> lerpLog(start, end, easeInOutSine(t));
            case EASEOUTCUBIC -> lerpLog(start, end, easeOutCubic(t));
            case EASEOUTQUAD -> lerpLog(start, end, easeOutQuad(t));
            case EASEOUTEXPO -> lerpLog(start, end, easeOutExpo(t));
        };
    }

    public static double lerp(double start, double end, double t) {
        return start + (end - start) * t;
    }

    public static double lerpLog(double start, double end, double t) {
        if (start <= 0.0) start = 1e-6;
        if (end   <= 0.0) end   = 1e-6;

        return Math.exp(Math.log(start) + (Math.log(end) - Math.log(start)) * t);
    }

    public static double easeInOutSine(double t) {
        return -(Math.cos(Math.PI * t) - 1) / 2;
    }

    public static double easeOutCubic(double t) {
        return 1.0 - Math.pow(1.0 - t, 3.0);
    }

    public static double easeOutQuad(double t) {
        return 1.0 - (1.0 - t) * (1.0 - t);
    }

    public static double easeOutExpo(double t) {
        return t == 1.0 ? 1.0 : 1.0 - Math.pow(2.0, -10.0 * t);
    }
}

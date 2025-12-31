package net.notnightsky.skyutils.utils;

public final class math {
    public static double lerp(double start, double end, double factor){
        return start + factor * (end - start);
    }

    public static float lerp(float start,float end, float factor){
        return start + factor * (end - start);
    }

    public static double lerpLog(double start, double end, double factor, double delta){
//        double currentLog = Math.log(currentZoom);
//        double targetLog  = Math.log(targetZoom);
//
//        // Exponential smoothing (frame-rate independent)
//        double t = 1.0 - Math.exp(-speed * deltaSeconds);
//        double newLog = currentLog + (targetLog - currentLog) * t;
//        return Math.exp(newLog);
//        return Math.exp(Math.log(start) + (Math.log(end) - Math.log(start)) * Math.exp(-factor * delta));
        return Math.exp(Math.log(start) + (Math.log(end) - Math.log(start)) * (1.0 - Math.exp(-factor * delta)));
    }
}

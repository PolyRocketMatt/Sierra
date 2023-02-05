package com.github.polyrocketmatt.sierra.lib.math;

public class Interpolation {

    public static double lerp(double min, double max, double x) {
        return min + x * (max - min);
    }

    public static double smoothStep(double x) {
        return x * x * (3 - 2 * x);
    }

    public static float smoothStep(float x) {
        return x * x * (3 - 2 * x);
    }

    public static double smootherStep(double x) {
        return x * x * x * (x * (x * 6 - 15) + 10);
    }

    public static float smootherStep(float x) {
        return x * x * x * (x * (x * 6 - 15) + 10);
    }

    public enum InterpolationType {
        LINEAR,
        HERMITE,
        QUINTIC
    }

}

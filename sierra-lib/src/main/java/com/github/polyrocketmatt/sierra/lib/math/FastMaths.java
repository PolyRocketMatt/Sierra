package com.github.polyrocketmatt.sierra.lib.math;

public class FastMaths {

    public static int fastFloor(float value) {
        return (value >= 0) ? (int) value : (int) value - 1;
    }

    public static int fastFloor(double value) {
        return (value >= 0) ? (int) value : (int) value - 1;
    }

    public static float fastAbs(float value) {
        return (value >= 0) ? value : -value;
    }

    public static double fastAbs(double value) {
        return (value >= 0) ? value : -value;
    }

}

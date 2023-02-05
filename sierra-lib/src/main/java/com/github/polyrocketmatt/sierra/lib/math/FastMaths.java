package com.github.polyrocketmatt.sierra.lib.math;

public class FastMaths {

    public static int fastFloor(float x) {
        return (x >= 0) ? (int) x : (int) x - 1;
    }

    public static int fastFloor(double x) {
        return (x >= 0) ? (int) x : (int) x - 1;
    }

    public static float fastAbs(float x) {
        return (x >= 0) ? x : -x;
    }

    public static double fastAbs(double x) {
        return (x >= 0) ? x : -x;
    }

}

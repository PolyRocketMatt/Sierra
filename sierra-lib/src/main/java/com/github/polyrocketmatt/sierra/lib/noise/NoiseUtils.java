package com.github.polyrocketmatt.sierra.lib.noise;

public class NoiseUtils {

    record Float2(float x, float z) {}
    record Float3(float x, float z, float y) {}

    private static final Float2[] GRAD_2D = new Float2[]{
            new Float2(-1.0f, -1.0f),
            new Float2(1.0f, -1.0f),
            new Float2(-1.0f, 1.0f),
            new Float2(1.0f, 1.0f),
            new Float2(0f, -1.0f),
            new Float2(-1.0f, 0f),
            new Float2(0f, 1.0f),
            new Float2(1.0f, 0f),
    };

    public static final int X_PRIME = 1619;
    public static final int Y_PRIME = 31337;
    public static final int Z_PRIME = 6971;
    public static final float F3 = (float) (1.0 / 3.0);
    public static final float G3 = (float) (1.0 / 6.0);
    public static final float G33 = G3 * 3.0f - 1.0f;
    public static final double SQRT3 = 1.7320508075688772935274463415059;
    public static final double F2 = 0.5 * (SQRT3 - 1.0);
    public static final double G2 = (3.0 - SQRT3) / 6.0;

    //  TODO: Should this be a signed/unsigned shift?
    public static double gradCoord2d(int seed, int x, int z, double fX, double fZ) {
        int hash = seed;
        hash = hash ^ X_PRIME * x;
        hash = hash ^ Y_PRIME * z;
        hash *= hash * hash * 60493;
        hash = (hash >> 13) ^ hash;

        Float2 grad = GRAD_2D[hash & 7];

        return fX * grad.x + fZ * grad.z;
    }

}

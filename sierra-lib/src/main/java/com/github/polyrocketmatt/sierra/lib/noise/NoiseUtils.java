package com.github.polyrocketmatt.sierra.lib.noise;

public class NoiseUtils {

    public interface NoiseVector<T> {}

    public record Double2(double x, double z) implements NoiseVector<Double> {}
    public record Double3(double x, double z, double y) implements NoiseVector<Double> {}

    public record Float2(float x, float z) implements NoiseVector<Float> {}
    public record Float3(float x, float z, float y) implements NoiseVector<Float> {}

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

    private static final Float3[] GRAD_3D = new Float3[]{
            new Float3(1.0f, 1.0f, 0.0f),
            new Float3(-1.0f, 1.0f, 0.0f),
            new Float3(1.0f, -1.0f, 0.0f),
            new Float3(-1.0f, -1.0f, 0.0f),
            new Float3(1.0f, 0.0f, 1.0f),
            new Float3(-1.0f, 0.0f, 1.0f),
            new Float3(1.0f, 0.0f, -1.0f),
            new Float3(-1.0f, 0.0f, -1.0f),
            new Float3(0.0f, 1.0f, 1.0f),
            new Float3(0.0f, -1.0f, 1.0f),
            new Float3(0.0f, 1.0f, -1.0f),
            new Float3(0.0f, -1.0f, -1.0f),
            new Float3(1.0f, 1.0f, 0.0f),
            new Float3(0.0f, -1.0f, 1.0f),
            new Float3(-1.0f, 1.0f, 0.0f),
            new Float3(0.0f, -1.0f, -1.0f),
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

    public static double gradCoord3d(int seed, int x, int y, int z, double fX, double fY, double fZ) {
        var hash = seed;
        hash = hash ^ X_PRIME * x;
        hash = hash ^ Y_PRIME * y;
        hash = hash ^ Z_PRIME * z;
        hash *= hash * hash * 60493;
        hash = (hash >> 13) ^ hash;

        Float3 grad = GRAD_3D[hash & 15];

        return fX * grad.x + fY * grad.y + fZ * grad.z;
    }

    public static double valCoord3d(int seed, int x, int y, int z) {
        int n = seed;
        n = n ^ X_PRIME * x;
        n = n ^ Y_PRIME * y;
        n = n ^ Z_PRIME * z;

        return (n * n * n * 60493) / (2147483648.0);
    }

}

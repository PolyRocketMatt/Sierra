package com.github.polyrocketmatt.sierra.lib.noise.provider;

import com.github.polyrocketmatt.sierra.lib.buffer.AsyncDoubleBuffer;
import com.github.polyrocketmatt.sierra.lib.buffer.AsyncFloatBuffer;
import com.github.polyrocketmatt.sierra.lib.exception.SierraNoiseException;
import com.github.polyrocketmatt.sierra.lib.noise.NoiseType;
import com.github.polyrocketmatt.sierra.lib.noise.data.SimplexNoiseData;

import static com.github.polyrocketmatt.sierra.lib.math.FastMaths.fastFloor;
import static com.github.polyrocketmatt.sierra.lib.noise.NoiseUtils.*;

public class SimplexNoiseProvider implements NoiseProvider<SimplexNoiseData> {

    private static final SimplexNoiseProvider INSTANCE = new SimplexNoiseProvider();

    public static SimplexNoiseProvider getInstance() {
        return INSTANCE;
    }

    @Override
    public void provide(AsyncFloatBuffer buffer, SimplexNoiseData data) throws SierraNoiseException {
        int seed = data.seed();

        buffer.mapIndexed((x, z, value) -> (float) eval(seed, x, 0.0, z));
    }

    @Override
    public void provide(AsyncDoubleBuffer buffer, SimplexNoiseData data) throws SierraNoiseException {
        int seed = data.seed();

        buffer.mapIndexed((x, z, value) -> eval(seed, x, 0.0, z));
    }

    @Override
    public float noise(float x, float y, float z, SimplexNoiseData data) throws SierraNoiseException {
        return (float) eval(data.seed(), x, y, z);
    }

    @Override
    public double noise(double x, double y, double z, SimplexNoiseData data) throws SierraNoiseException {
        return eval(data.seed(), (float) x, (float) y, (float) z);
    }

    private double eval(int seed, double nX, double nY, double nZ) {
        double x = nX * 0.01;
        double y = nY * 0.01;
        double z = nZ * 0.01;

        double t = (x + y + z) * F3;
        int i = fastFloor(x + t);
        int j = fastFloor(y + t);
        int k = fastFloor(z + t);

        t = (i + j + k) * G3;
        double x0 = x - (i - t);
        double y0 = y - (j - t);
        double z0 = z - (k - t);

        int i1;
        int j1;
        int k1;
        int i2;
        int j2;
        int k2;
        if(x0 >= y0) {
            if(y0 >= z0) {
                i1 = 1;
                j1 = 0;
                k1 = 0;
                i2 = 1;
                j2 = 1;
                k2 = 0;
            } else if(x0 >= z0) {
                i1 = 1;
                j1 = 0;
                k1 = 0;
                i2 = 1;
                j2 = 0;
                k2 = 1;
            } else {
                i1 = 0;
                j1 = 0;
                k1 = 1;
                i2 = 1;
                j2 = 0;
                k2 = 1;
            }
        } else {
            if(y0 < z0) {
                i1 = 0;
                j1 = 0;
                k1 = 1;
                i2 = 0;
                j2 = 1;
                k2 = 1;
            } else if(x0 < z0) {
                i1 = 0;
                j1 = 1;
                k1 = 0;
                i2 = 0;
                j2 = 1;
                k2 = 1;
            } else {
                i1 = 0;
                j1 = 1;
                k1 = 0;
                i2 = 1;
                j2 = 1;
                k2 = 0;
            }
        }

        double x1 = x0 - i1 + G3;
        double y1 = y0 - j1 + G3;
        double z1 = z0 - k1 + G3;
        double x2 = x0 - i2 + F3;
        double y2 = y0 - j2 + F3;
        double z2 = z0 - k2 + F3;
        double x3 = x0 + G33;
        double y3 = y0 + G33;
        double z3 = z0 + G33;

        double n0;
        double n1;
        double n2;
        double n3;
        t = 0.6 - x0 * x0 - y0 * y0 - z0 * z0;

        if (t < 0)
            n0 = 0.0;
        else {
            t *= t;
            n0 = t * t * gradCoord3d(seed, i, j, k, x0, y0, z0);
        }

        t = 0.6 - x1 * x1 - y1 * y1 - z1 * z1;
        if (t < 0)
            n1 = 0.0;
        else {
            t *= t;
            n1 = t * t * gradCoord3d(seed, i + i1, j + j1, k + k1, x1, y1, z1);
        }

        t = 0.6 - x2 * x2 - y2 * y2 - z2 * z2;
        if (t < 0)
            n2 = 0.0;
        else {
            t *= t;
            n2 = t * t * gradCoord3d(seed, i + i2, j + j2, k + k2, x2, y2, z2);
        }

        t = 0.6 - x3 * x3 - y3 * y3 - z3 * z3;
        if (t < 0)
            n3 = 0.0;
        else {
            t *= t;
            n3 = t * t * gradCoord3d(seed, i + 1, j + 1, k + 1, x3, y3, z3);
        }

        return 32.0 * (n0 + n1 + n2 + n3);
    }

    @Override
    public NoiseType getProviderType() {
        return NoiseType.SIMPLEX;
    }
}

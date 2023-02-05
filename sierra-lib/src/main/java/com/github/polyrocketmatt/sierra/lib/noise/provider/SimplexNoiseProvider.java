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

        buffer.mapIndexed((x, z, value) -> (float) eval(seed, x, z));
    }

    @Override
    public void provide(AsyncDoubleBuffer buffer, SimplexNoiseData data) throws SierraNoiseException {

    }

    @Override
    public float noise(float x, float y, float z, SimplexNoiseData data) throws SierraNoiseException {
        return (float) eval(data.seed(), x, z);
    }

    @Override
    public double noise(double x, double y, double z, SimplexNoiseData data) throws SierraNoiseException {
        return eval(data.seed(), (float) x, (float) z);
    }

    private double eval(int seed, double x, double z) {
        x *= 0.01;
        z *= 0.01;

        double t = (x + z) * F2;
        int i = fastFloor(x + t);
        int j = fastFloor(z + t);

        t = (i + j) * G2;
        double x0 = x - (i - t);
        double z0 = z - (j - t);

        int i1;
        int j1;
        if (x0 > z0) {
            i1 = 1;
            j1 = 0;
        } else {
            i1 = 0;
            j1 = 1;
        }

        double x1 = x0 - i1 + G2;
        double y1 = z0 - j1 + G2;
        double x2 = x0 - 1.0 + 2.0 * G2;
        double y2 = z0 - 1.0 + 2.0 * G2;

        double n0;
        double n1;
        double n2;

        t = 0.5 - x0 * x0 - z0 * z0;
        if (t < 0.0)
            n0 = 0.0;
        else {
            t *= t;
            n0 = t * t * gradCoord2d(seed, i, j, x0, z0);
        }

        t = 0.5 - x1 * x1 - y1 * y1;
        if (t < 0.0)
            n1 = 0.0;
        else {
            t *= t;
            n1 = t * t * gradCoord2d(seed, i + i1, j + j1, x1, y1);
        }

        t = 0.5 - x2 * x2 - y2 * y2;
        if (t < 0.0)
            n2 = 0.0;
        else {
            t *= t;
            n2 = t * t * gradCoord2d(seed, i + 1, j + 1, x2, y2);
        }

        return 50.0 * (n0 + n1 + n2);
    }

    @Override
    public NoiseType getProviderType() {
        return NoiseType.SIMPLEX;
    }
}

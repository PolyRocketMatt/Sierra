package com.github.polyrocketmatt.sierra.lib.noise.provider;

import com.github.polyrocketmatt.sierra.lib.buffer.AsyncDoubleBuffer;
import com.github.polyrocketmatt.sierra.lib.buffer.AsyncFloatBuffer;
import com.github.polyrocketmatt.sierra.lib.exception.SierraNoiseException;
import com.github.polyrocketmatt.sierra.lib.math.Interpolation;
import com.github.polyrocketmatt.sierra.lib.noise.NoiseType;
import com.github.polyrocketmatt.sierra.lib.noise.data.ValueNoiseData;

import static com.github.polyrocketmatt.sierra.lib.math.FastMaths.fastFloor;
import static com.github.polyrocketmatt.sierra.lib.math.Interpolation.*;
import static com.github.polyrocketmatt.sierra.lib.noise.NoiseUtils.valCoord3d;

public class ValueNoiseProvider implements NoiseProvider<ValueNoiseData> {

    private static final ValueNoiseProvider INSTANCE = new ValueNoiseProvider();

    public static ValueNoiseProvider getInstance() {
        return INSTANCE;
    }

    @Override
    public void provide(AsyncFloatBuffer buffer, ValueNoiseData data) throws SierraNoiseException {
        int seed = data.seed();
        Interpolation.InterpolationType interpolationType = data.interpolationType();

        buffer.mapIndexed((x, z, value) -> (float) eval(seed, interpolationType, x, 0.0, z));
    }

    @Override
    public void provide(AsyncDoubleBuffer buffer, ValueNoiseData data) throws SierraNoiseException {
        int seed = data.seed();
        Interpolation.InterpolationType interpolationType = data.interpolationType();

        buffer.mapIndexed((x, z, value) -> eval(seed, interpolationType, x, 0.0, z));
    }

    @Override
    public float noise(float x, float y, float z, ValueNoiseData data) throws SierraNoiseException {
        return (float) noise((double) x, (double) y, (double) z, data);
    }

    @Override
    public double noise(double x, double y, double z, ValueNoiseData data) throws SierraNoiseException {
        return eval(data.seed(), data.interpolationType(), x, y, z);
    }

    @Override
    public NoiseType getProviderType() {
        return NoiseType.VALUE;
    }

    private double eval(int seed, Interpolation.InterpolationType interpolationType, double nX, double nY, double nZ) {
        double x = nX * 0.01;
        double y = nY * 0.01;
        double z = nZ * 0.01;

        int x0 = fastFloor(x);
        int y0 = fastFloor(y);
        int z0 = fastFloor(z);
        int x1 = x0 + 1;
        int y1 = y0 + 1;
        int z1 = z0 + 1;
        double xs = 0.0;
        double ys = 0.0;
        double zs = 0.0;
        switch (interpolationType) {
            case LINEAR -> {
                xs = x - x0;
                ys = y - y0;
                zs = z - z0;
            }


            case HERMITE -> {
                xs = smoothStep(x - x0);
                ys = smoothStep(y - y0);
                zs = smoothStep(z - z0);
            }

            case QUINTIC -> {
                xs = smootherStep(x - x0);
                ys = smootherStep(y - y0);
                zs = smootherStep(z - z0);
            }
        }

        double xf00 = lerp(valCoord3d(seed, x0, y0, z0), valCoord3d(seed, x1, y0, z0), xs);
        double xf10 = lerp(valCoord3d(seed, x0, y1, z0), valCoord3d(seed, x1, y1, z0), xs);
        double xf01 = lerp(valCoord3d(seed, x0, y0, z1), valCoord3d(seed, x1, y0, z1), xs);
        double xf11 = lerp(valCoord3d(seed, x0, y1, z1), valCoord3d(seed, x1, y1, z1), xs);

        double yf0 = lerp(xf00, xf10, ys);
        double yf1 = lerp(xf01, xf11, ys);

        return lerp(yf0, yf1, zs);
    }
}

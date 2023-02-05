package com.github.polyrocketmatt.sierra.lib.noise.provider;

import com.github.polyrocketmatt.sierra.lib.buffer.AsyncDoubleBuffer;
import com.github.polyrocketmatt.sierra.lib.buffer.AsyncFloatBuffer;
import com.github.polyrocketmatt.sierra.lib.exception.SierraNoiseException;
import com.github.polyrocketmatt.sierra.lib.noise.NoiseType;
import com.github.polyrocketmatt.sierra.lib.noise.data.SimplexFractalNoiseData;
import com.github.polyrocketmatt.sierra.lib.noise.data.SimplexNoiseData;

import static com.github.polyrocketmatt.sierra.lib.math.FastMaths.fastAbs;

public class SimplexFractalNoiseProvider implements NoiseProvider<SimplexFractalNoiseData> {

    private static final SimplexFractalNoiseProvider INSTANCE = new SimplexFractalNoiseProvider();

    public static SimplexFractalNoiseProvider getInstance() {
        return INSTANCE;
    }

    @Override
    public void provide(AsyncFloatBuffer buffer, SimplexFractalNoiseData data) throws SierraNoiseException {
        int seed = data.seed();
        int octaves = data.octaves();
        float scale = data.scale();
        float gain = data.gain();
        float lacunarity = data.lacunarity();
        FractalType fractalType = data.fractalType();
        SimplexNoiseProvider noise = new SimplexNoiseProvider();
        SimplexNoiseData simplexData = new SimplexNoiseData(seed);

         switch (fractalType) {
             case FBM -> buffer.mapIndexed((x, z, value) -> {
                 double sum = noise.noise(x * scale, 0.0, z * scale, simplexData);
                 double amp = 1.0;
                 double sX = x;
                 double sZ = z;

                 for (int i = 1; i < octaves; i++) {
                     sX *= lacunarity;
                     sZ *= lacunarity;

                     amp *= gain;
                     sum += noise.noise(sX * scale, 0.0, sZ * scale, simplexData) * amp;
                 }

                 return (float) sum;
             });

             case BILLOW -> buffer.mapIndexed((x, z, value) -> {
                 double sum = fastAbs(noise.noise(x * scale, 0.0, z * scale, simplexData)) * 2 - 1;
                 double amp = 1.0;
                 double sX = x;
                 double sZ = z;

                 for (int i = 1; i < octaves; i++) {
                     sX *= lacunarity;
                     sZ *= lacunarity;

                     amp *= gain;
                     sum += (fastAbs(noise.noise(sX * scale, 0.0, sZ * scale, simplexData)) * 2 - 1) * amp;
                 }

                 return (float) sum;
             });

             case RIDGED -> buffer.mapIndexed((x, z, value) -> {
                 double sum = 1.0 - noise.noise(x * scale, 0.0, z * scale, simplexData);
                 double amp = 1.0;
                 double sX = x;
                 double sZ = z;

                 for (int i = 1; i < octaves; i++) {
                     sX *= lacunarity;
                     sZ *= lacunarity;

                     amp *= gain;
                     sum += (1.0f - fastAbs(noise.noise(sX * scale, 0.0, sZ * scale, simplexData))) * amp;
                 }

                 return (float) sum;
             });
         }
    }

    @Override
    public void provide(AsyncDoubleBuffer buffer, SimplexFractalNoiseData data) throws SierraNoiseException {

    }

    @Override
    public float noise(float x, float y, float z, SimplexFractalNoiseData data) throws SierraNoiseException {
        int seed = data.seed();
        int octaves = data.octaves();
        float scale = data.scale();
        float gain = data.gain();
        float lacunarity = data.lacunarity();
        FractalType fractalType = data.fractalType();
        SimplexNoiseProvider noise = new SimplexNoiseProvider();
        SimplexNoiseData simplexData = new SimplexNoiseData(seed);

        switch (fractalType) {
            case FBM -> {
                double sum = noise.noise(x * scale, 0.0, z * scale, simplexData);
                double amp = 1.0;
                double sX = x;
                double sZ = z;

                for (int i = 1; i < octaves; i++) {
                    sX *= lacunarity;
                    sZ *= lacunarity;

                    amp *= gain;
                    sum += noise.noise(sX * scale, 0.0, sZ * scale, simplexData) * amp;
                }

                return (float) sum;
            }

            case BILLOW -> {
                double sum = fastAbs(noise.noise(x * scale, 0.0, z * scale, simplexData)) * 2 - 1;
                double amp = 1.0;
                double sX = x;
                double sZ = z;

                for (int i = 1; i < octaves; i++) {
                    sX *= lacunarity;
                    sZ *= lacunarity;

                    amp *= gain;
                    sum += (fastAbs(noise.noise(sX * scale, 0.0, sZ * scale, simplexData)) * 2 - 1) * amp;
                }

                return (float) sum;
            }

            case RIDGED -> {
                double sum = 1.0 - noise.noise(x * scale, 0.0, z * scale, simplexData);
                double amp = 1.0;
                double sX = x;
                double sZ = z;

                for (int i = 1; i < octaves; i++) {
                    sX *= lacunarity;
                    sZ *= lacunarity;

                    amp *= gain;
                    sum += (1.0f - fastAbs(noise.noise(sX * scale, 0.0, sZ * scale, simplexData))) * amp;
                }

                return (float) sum;
            }
        }

        throw new SierraNoiseException("Invalid fractal type", NoiseType.SIMPLEX_FRACTAL, data);
    }

    @Override
    public double noise(double x, double y, double z, SimplexFractalNoiseData data) throws SierraNoiseException {
        return 0;
    }

    @Override
    public NoiseType getProviderType() {
        return NoiseType.SIMPLEX_FRACTAL;
    }

    public enum FractalType {
        FBM,
        BILLOW,
        RIDGED
    }

}

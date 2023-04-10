package com.github.polyrocketmatt.sierra.lib.noise.data;

import com.github.polyrocketmatt.sierra.lib.math.Interpolation;
import com.github.polyrocketmatt.sierra.lib.noise.provider.SimplexFractalNoiseProvider;

public record SimplexFractalNoiseData(int seed,
                                      int octaves,
                                      float scale,
                                      float gain,
                                      float lacunarity,
                                      SimplexFractalNoiseProvider.FractalType fractalType,
                                      Interpolation.InterpolationType interpolationType
) implements NoiseData {

    @Override
    public String toString() {
        return "SimplexFractalNoiseData\n" +
                "    Seed: %d\n".formatted(seed) +
                "    Octaves: %d\n".formatted(octaves) +
                "    Scale: %f\n".formatted(scale) +
                "    Gain: %f\n".formatted(gain) +
                "    Lacunarity: %f\n".formatted(lacunarity) +
                "    Fractal Type: %s\n".formatted(fractalType.name()) +
                "    Filter Function: %s\n".formatted(interpolationType.name());
    }
}

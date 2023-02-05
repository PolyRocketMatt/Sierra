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
) implements NoiseData {}

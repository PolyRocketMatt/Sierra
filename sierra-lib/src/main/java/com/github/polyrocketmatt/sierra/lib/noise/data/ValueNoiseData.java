package com.github.polyrocketmatt.sierra.lib.noise.data;

import com.github.polyrocketmatt.sierra.lib.math.Interpolation;

public record ValueNoiseData(int seed, Interpolation.InterpolationType interpolationType) implements NoiseData { }

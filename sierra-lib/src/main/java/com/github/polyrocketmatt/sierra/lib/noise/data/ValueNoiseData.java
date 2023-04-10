package com.github.polyrocketmatt.sierra.lib.noise.data;

import com.github.polyrocketmatt.sierra.lib.math.Interpolation;

public record ValueNoiseData(int seed,
                             Interpolation.InterpolationType interpolationType
) implements NoiseData {

    @Override
    public String toString() {
        return "SimplexFractalNoiseData\n" +
                "    Seed: %d\n".formatted(seed) +
                "    Filter Function: %s\n".formatted(interpolationType.name());
    }

}

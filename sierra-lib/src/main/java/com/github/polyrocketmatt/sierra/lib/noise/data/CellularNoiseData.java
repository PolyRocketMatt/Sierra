package com.github.polyrocketmatt.sierra.lib.noise.data;

import com.github.polyrocketmatt.sierra.lib.noise.provider.CellularNoiseProvider;

public record CellularNoiseData(int seed,
                                float lacunarity,
                                CellularNoiseProvider.DistanceFunction distance,
                                CellularNoiseProvider.FilterFunction filter
) implements NoiseData {

    @Override
    public String toString() {
        return "CellularNoiseData\n" +
                "    Seed: %d\n".formatted(seed) +
                "    Lacunarity: %f\n".formatted(lacunarity) +
                "    Distance Function: %s\n".formatted(distance.name()) +
                "    Filter Function: %s\n".formatted(filter.name());
    }
}

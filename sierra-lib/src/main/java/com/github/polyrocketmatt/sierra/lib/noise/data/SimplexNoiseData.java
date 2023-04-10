package com.github.polyrocketmatt.sierra.lib.noise.data;

public record SimplexNoiseData(int seed) implements NoiseData {

    @Override
    public String toString() {
        return "SimplexFractalNoiseData\n" +
                "    Seed: %d\n".formatted(seed);
    }

}

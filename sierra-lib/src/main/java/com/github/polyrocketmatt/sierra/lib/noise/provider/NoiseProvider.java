package com.github.polyrocketmatt.sierra.lib.noise.provider;

import com.github.polyrocketmatt.sierra.lib.buffer.AsyncDoubleBuffer;
import com.github.polyrocketmatt.sierra.lib.buffer.AsyncFloatBuffer;
import com.github.polyrocketmatt.sierra.lib.exception.SierraNoiseException;
import com.github.polyrocketmatt.sierra.lib.noise.NoiseType;
import com.github.polyrocketmatt.sierra.lib.noise.data.NoiseData;

public interface NoiseProvider<T extends NoiseData> {

    void provide(AsyncFloatBuffer buffer, T data) throws SierraNoiseException;

    void provide(AsyncDoubleBuffer buffer, T data) throws SierraNoiseException;

    float noise(float x, float y, float z, T data) throws SierraNoiseException;

    double noise(double x, double y, double z, T data) throws SierraNoiseException;

    NoiseType getProviderType();

}

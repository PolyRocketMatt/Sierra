package com.github.polyrocketmatt.sierra.lib.buffer;

import com.github.polyrocketmatt.sierra.lib.exception.SierraArgumentException;
import com.github.polyrocketmatt.sierra.lib.exception.SierraOperationException;
import com.github.polyrocketmatt.sierra.lib.math.Interpolation;
import com.github.polyrocketmatt.sierra.lib.noise.NoiseUtils;
import com.github.polyrocketmatt.sierra.lib.noise.data.NoiseData;
import com.github.polyrocketmatt.sierra.lib.noise.provider.NoiseProvider;

import static com.github.polyrocketmatt.sierra.lib.math.Interpolation.smoothStep;

public class AsyncDoubleBuffer extends AsyncBuffer<Double> {

    private final int size;
    private final int chunkSize;

    public AsyncDoubleBuffer(int size) throws SierraArgumentException {
        super(size, size, 1, 1, Double.class);

        this.size = size;
        this.chunkSize = 1;
    }

    public AsyncDoubleBuffer(int size, int chunkSize) throws SierraArgumentException {
        super(size, size, chunkSize, chunkSize, Double.class);

        this.size = size;
        this.chunkSize = chunkSize;
    }

    @Override
    public void plus(AsyncBuffer<Double> buffer) throws SierraOperationException {
        this.mapIndexed((x, z, value) -> value + buffer.get(x, z));
    }

    @Override
    public void minus(AsyncBuffer<Double> buffer) throws SierraOperationException {
        this.mapIndexed((x, z, value) -> value - buffer.get(x, z));
    }

    @Override
    public void multiply(AsyncBuffer<Double> buffer) throws SierraOperationException {
        this.mapIndexed((x, z, value) -> value * buffer.get(x, z));
    }

    @Override
    public void divide(AsyncBuffer<Double> buffer) throws SierraOperationException {
        this.mapIndexed((x, z, value) -> value / buffer.get(x, z));
    }

    @Override
    public void clip(Double min, Double max) throws SierraOperationException {
        this.map(value -> Math.min(Math.max(value, min), max));
    }

    @Override
    public void invert() throws SierraOperationException {
        this.map(value -> 1 - value);
    }

    @Override
    public void lerp(Double min, Double max) throws SierraOperationException {
        this.map(value -> min + (max - min) * value);
    }

    @Override
    public void normalise(Double min, Double max) throws SierraOperationException {
        this.map(value -> (value - min) / (max - min));
    }

    @Override
    public void scale(Double scale) throws SierraOperationException {
        this.map(value -> value * scale);
    }

    @Override
    public void pow(Double exponent) throws SierraOperationException {
        this.map(value -> Math.pow(value, exponent));
    }

    @Override
    public void softSmooth() throws SierraOperationException {
        this.map(Interpolation::smoothStep);
    }

    @Override
    public void hardSmooth() throws SierraOperationException {
        this.map(Interpolation::smootherStep);
    }

    @Override
    public <K extends NoiseData> void warp(NoiseProvider<K> provider, K data, NoiseUtils.NoiseVector<Double> offsetX, NoiseUtils.NoiseVector<Double> offsetZ, float warp) throws SierraOperationException {
        if (!(offsetX instanceof NoiseUtils.Double2 oX))
            throw new SierraOperationException("Offset X must be a Double2 vector");
        if (!(offsetZ instanceof NoiseUtils.Double2 oZ))
            throw new SierraOperationException("Offset Z must be a Double2 vector");

        this.mapIndexed((x, z, value) -> {
            NoiseUtils.Float2 q = new NoiseUtils.Float2(
                    (float) provider.noise((double) x + oX.x(), 0.0, (double) z + oX.z(), data),
                    (float) provider.noise((double) x + oZ.x(), 0.0, (double) z + oZ.z(), data)
            );

            return provider.noise(q.x() * warp + x, 0.0, q.z() * warp + z, data);
        });
    }

    @Override
    protected AsyncDoubleBuffer clone() throws CloneNotSupportedException {
        try {
            AsyncDoubleBuffer clone = new AsyncDoubleBuffer(size, chunkSize);

            for (int x = 0; x < size; x++)
                for (int z = 0; z < size; z++)
                    clone.set(x, z, get(x, z));

            return clone;
        } catch (SierraArgumentException ex) { throw new CloneNotSupportedException(ex.getMessage()); }
    }
}

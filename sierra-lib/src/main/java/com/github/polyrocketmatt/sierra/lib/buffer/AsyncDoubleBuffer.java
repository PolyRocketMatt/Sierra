package com.github.polyrocketmatt.sierra.lib.buffer;

import com.github.polyrocketmatt.sierra.lib.exception.SierraArgumentException;
import com.github.polyrocketmatt.sierra.lib.exception.SierraOperationException;

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

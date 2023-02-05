package com.github.polyrocketmatt.sierra.lib.buffer;

import com.github.polyrocketmatt.sierra.lib.exception.SierraArgumentException;
import com.github.polyrocketmatt.sierra.lib.exception.SierraOperationException;

public class AsyncFloatBuffer extends AsyncBuffer<Float> {

    private final int size;
    private final int chunkSize;

    public AsyncFloatBuffer(int size) throws SierraArgumentException {
        super(size, size, 1, 1, Float.class);
        this.size = size;
        this.chunkSize = 1;
    }

    public AsyncFloatBuffer(int size, int chunkSize) throws SierraArgumentException {
        super(size, size, chunkSize, chunkSize, Float.class);
        this.size = size;
        this.chunkSize = chunkSize;
    }

    public AsyncFloatBuffer(int size, int chunkSize, float value) throws SierraArgumentException {
        super(size, size, chunkSize, chunkSize, Float.class);
        this.size = size;
        this.chunkSize = chunkSize;
        this.fill(value);
    }

    @Override
    public void plus(AsyncBuffer<Float> buffer) throws SierraOperationException {
        this.mapIndexed((x, z, value) -> value + buffer.get(x, z));
    }

    @Override
    public void minus(AsyncBuffer<Float> buffer) throws SierraOperationException {
        this.mapIndexed((x, z, value) -> value - buffer.get(x, z));
    }

    @Override
    public void multiply(AsyncBuffer<Float> buffer) throws SierraOperationException {
        this.mapIndexed((x, z, value) -> value * buffer.get(x, z));
    }

    @Override
    public void divide(AsyncBuffer<Float> buffer) throws SierraOperationException {
        this.mapIndexed((x, z, value) -> value / buffer.get(x, z));
    }

    @Override
    public void clip(Float min, Float max) throws SierraOperationException {
        this.map(value -> Math.min(Math.max(value, min), max));
    }

    @Override
    public void invert() throws SierraOperationException {
        this.map(value -> 1 - value);
    }

    @Override
    public void lerp(Float min, Float max) throws SierraOperationException {
        this.map(value -> min + (max - min) * value);
    }

    @Override
    public void normalise(Float min, Float max) throws SierraOperationException {
        this.map(value -> (value - min) / (max - min));
    }

    @Override
    protected AsyncFloatBuffer clone() throws CloneNotSupportedException {
        try {
            AsyncFloatBuffer clone = new AsyncFloatBuffer(size, chunkSize);

            for (int x = 0; x < size; x++)
                for (int z = 0; z < size; z++)
                    clone.set(x, z, get(x, z));

            return clone;
        } catch (SierraArgumentException ex) { throw new CloneNotSupportedException(ex.getMessage()); }
    }
}

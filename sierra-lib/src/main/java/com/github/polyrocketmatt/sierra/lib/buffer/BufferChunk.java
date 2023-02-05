package com.github.polyrocketmatt.sierra.lib.buffer;

import java.lang.reflect.Array;

public class BufferChunk<T> implements Cloneable {

    private final int x;
    private final int z;
    private final int width;
    private final int height;
    private final Class<T> clazz;
    private final T[] data;

    @SuppressWarnings("unchecked")
    public BufferChunk(int x, int z, int width, int height, Class<T> clazz) {
        this.x = x;
        this.z = z;
        this.width = width;
        this.height = height;
        this.clazz = clazz;
        this.data = (T[]) Array.newInstance(clazz, width * height);
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public int getChunkWidth() {
        return width;
    }

    public int getChunkHeight() {
        return height;
    }

    public T get(int x, int z) {
        return data[x + z * width];
    }

    public void set(int x, int z, T value) {
        data[x + z * width] = value;
    }

    public void flipHorizontal() {
        for (int x = 0; x < width; x++)
            for (int z = 0; z < height / 2; z++) {
                int index = x + (height - z - 1) * width;

                T temp = data[x + z * width];
                data[x + z * width] = data[index];
                data[index] = temp;
            }
    }

    public void flipVertical() {
        for (int x = 0; x < width / 2; x++)
            for (int z = 0; z < height; z++) {
                int index = (width - x - 1) + z * width;

                T temp = data[x + z * width];
                data[x + z * width] = data[index];
                data[index] = temp;
            }
    }

    public BufferChunk<T> to(int x, int z) {
        BufferChunk<T> newChunk = new BufferChunk<>(x, z, width, height, clazz);
        System.arraycopy(data, 0, newChunk.data, 0, data.length);

        return newChunk;
    }

    @Override
    protected BufferChunk<T> clone() {
        return to(x, z);
    }
}

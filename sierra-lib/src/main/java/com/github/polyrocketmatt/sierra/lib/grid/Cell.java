package com.github.polyrocketmatt.sierra.lib.grid;

import com.github.polyrocketmatt.sierra.lib.buffer.AsyncBuffer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Cell {

    private final CellPosition position;
    private final int scale;
    private final int seed;
    private final List<AsyncBuffer<Double>> buffers;

    private final int width;
    private final int height;

    public Cell(int x, int z, int scale, int seed) {
        this.position = new CellPosition(x, z);
        this.scale = scale;
        this.seed = seed;
        this.buffers = new ArrayList<>();

        this.width = scale;
        this.height = scale;
    }

    public int getX() {
        return position.x();
    }

    public int getZ() {
        return position.z();
    }

    public int getSeed() {
        return seed;
    }

    public AsyncBuffer<Double> getBuffer(int index) {
        return buffers.get(index);
    }

    public void addBuffer(AsyncBuffer<Double> buffer) {
        buffers.add(buffer);
    }

    public void removeBuffer(int index) {
        buffers.remove(index);
    }

    public int getWorldX() {
        return position.x() * scale;
    }

    public int getWorldZ() {
        return position.z() * scale;
    }

    public BufferedImage[] compress() {
        BufferedImage[] compressedImages = new BufferedImage[(int) Math.ceil((double) buffers.size() / 3)];

        for (int i = 0; i < compressedImages.length; i++) {
            compressedImages[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int r = (int) (buffers.get(i * 3).get(x, y) * 255);
                    int g = (int) (buffers.get(i * 3 + 1).get(x, y) * 255);
                    int b = (int) (buffers.get(i * 3 + 2).get(x, y) * 255);

                    compressedImages[i].setRGB(x, y, (r << 16) | (g << 8) | b);
                }
            }
        }

        return compressedImages;
    }

}

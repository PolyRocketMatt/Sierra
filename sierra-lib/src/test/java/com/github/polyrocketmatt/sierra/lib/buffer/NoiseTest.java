package com.github.polyrocketmatt.sierra.lib.buffer;

import com.github.polyrocketmatt.sierra.lib.math.Interpolation;
import com.github.polyrocketmatt.sierra.lib.noise.data.SimplexNoiseData;
import com.github.polyrocketmatt.sierra.lib.noise.data.ValueNoiseData;
import com.github.polyrocketmatt.sierra.lib.noise.provider.SimplexNoiseProvider;
import com.github.polyrocketmatt.sierra.lib.noise.provider.ValueNoiseProvider;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class NoiseTest {

    @Test
    public void testFractal() {
        int size = 2048;
        int chunkSize = 512;

        AsyncFloatBuffer buffer = new AsyncFloatBuffer(size, chunkSize, 0.0f);
        SimplexNoiseProvider provider = SimplexNoiseProvider.getInstance();

        provider.provide(buffer, new SimplexNoiseData(0));

        //  Transform the buffer to an image
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        float max = Float.MIN_VALUE;
        float min = Float.MAX_VALUE;
        for (int z = 0; z < size; z++)
            for (int x = 0; x < size; x++) {
                float value = buffer.get(x, z);
                if (value > max) {
                    max = value;
                }
                if (value < min) {
                    min = value;
                }
            }

        for (int z = 0; z < size; z++) {
            for (int x = 0; x < size; x++) {
                float value = (buffer.get(x, z) - min) / (max - min);
                int color = (int) (value * 255);
                int c = min(255, max(0, color));
                image.setRGB(x, z, new Color(c, c, c).getRGB());
            }
        }

        try {
            ImageIO.write(image, "png", new File("noise.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSimplex() {
        int size = 512;
        int chunkSize = 64;

        AsyncFloatBuffer buffer = new AsyncFloatBuffer(size, chunkSize, 0.0f);
        SimplexNoiseProvider provider = SimplexNoiseProvider.getInstance();

        provider.provide(buffer, new SimplexNoiseData(0));

        //  Transform the buffer to an image
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        float max = Float.MIN_VALUE;
        float min = Float.MAX_VALUE;
        for (int z = 0; z < size; z++)
            for (int x = 0; x < size; x++) {
                float value = buffer.get(x, z);
                if (value > max) {
                    max = value;
                }
                if (value < min) {
                    min = value;
                }
            }

        for (int z = 0; z < size; z++) {
            for (int x = 0; x < size; x++) {
                float value = (buffer.get(x, z) - min) / (max - min);
                int color = (int) (value * 255);
                int c = min(255, max(0, color));
                image.setRGB(x, z, new Color(c, c, c).getRGB());
            }
        }

        try {
            ImageIO.write(image, "png", new File("noise.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

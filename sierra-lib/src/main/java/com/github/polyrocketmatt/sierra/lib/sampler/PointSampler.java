package com.github.polyrocketmatt.sierra.lib.sampler;

import com.github.polyrocketmatt.sierra.lib.vector.Double2;
import com.github.polyrocketmatt.sierra.lib.vector.Float2;

import java.util.List;

public interface PointSampler {

    List<Float2> sample(float radius, int samplesBeforeRejection, int size, int seed, List<Float2> predefinedPoints);

    List<Double2> sample(double radius, int samplesBeforeRejection, int size, int seed, List<Double2> predefinedPoints);

}

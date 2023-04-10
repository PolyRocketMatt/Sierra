package com.github.polyrocketmatt.sierra.lib.sampler;

import com.github.polyrocketmatt.sierra.lib.math.FastMaths;
import com.github.polyrocketmatt.sierra.lib.vector.Double2;
import com.github.polyrocketmatt.sierra.lib.vector.Float2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.github.polyrocketmatt.sierra.lib.math.Constants.*;

public class SimplePoissonSampler implements PointSampler {

    @Override
    public List<Float2> sample(float radius, int samplesBeforeRejection, int size, int seed, List<Float2> predefinedPoints) {
        var cellSize = radius / Math.sqrt(2.0f);
        var arraySize = FastMaths.fastCeil(size / cellSize);
        var grid = new Integer[arraySize][arraySize];
        var points = new ArrayList<Float2>();
        var spawnPoints = new ArrayList<Float2>();
        var prgn = new Random(seed);

        spawnPoints.add(new Float2(0.5f * size, 0.5f * size));

        while (!spawnPoints.isEmpty()) {
            var spawnIndex = prgn.nextInt(spawnPoints.size());
            var spawnCenter = spawnPoints.get(spawnIndex);
            var accepted = false;

            for (int i = 0; i <= samplesBeforeRejection; i++) {
                var angle = prgn.nextFloat() * TAU;
                var multiplier = radius * prgn.nextFloat() * (2.0f * radius - radius);
                var direction = new Float2(
                        (float) (multiplier * Math.sin(angle)),
                        (float) (multiplier * Math.cos(angle))
                );
                var candidate = new Float2(spawnCenter.x() + direction.x(), spawnCenter.y() + direction.y());
                if (isValidSample(candidate, radius, (float) cellSize, size, points, grid)) {
                    points.add(candidate);
                    spawnPoints.add(candidate);

                    var gridIndexX = (int) (candidate.x() / cellSize);
                    var gridIndexY = (int) (candidate.y() / cellSize);

                    grid[gridIndexX][gridIndexY] = points.size();
                    accepted = true;

                    break;
                }
            }

            if (!accepted)
                spawnPoints.remove(spawnIndex);
        }

        return points;
    }

    private boolean isValidSample(Float2 candidate, float radius, float cellSize, int size,
                                  List<Float2> points, Integer[][] grid) {
        var cX = candidate.x();
        var cY = candidate.y();
        var radiusSquared = radius * radius;

        if (cX >= 0.0f && cX <= size && cY >= 0.0f && cY <= size) {
            var cellX = (int) (cX / cellSize);
            var cellY = (int) (cY / cellSize);

            var searchStartX = Integer.max(0, cellX - 2);
            var searchEndX = Integer.min(cellX + 2, grid.length - 1);
            var searchStartY = Integer.max(0, cellY - 2);
            var searchEndY = Integer.min(cellY + 2, grid[0].length - 1);

            for (int x = searchStartX; x <= searchEndX; x++) {
                for (int y = searchStartY; y <= searchEndY; y++) {
                    var pointIndex = grid[x][y] - 1;
                    if (pointIndex != -1) {
                        var point = points.get(pointIndex);
                        if (candidate.distanceSquared(point) < radiusSquared)
                            return false;
                    }
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public List<Double2> sample(double radius, int samplesBeforeRejection, int size, int seed, List<Double2> predefinedPoints) {
        var cellSize = radius / Math.sqrt(2.0);
        var arraySize = FastMaths.fastCeil(size / cellSize);
        var grid = new Integer[arraySize][arraySize];
        var points = new ArrayList<Double2>();
        var spawnPoints = new ArrayList<Double2>();
        var prgn = new Random(seed);

        spawnPoints.add(new Double2(0.5 * size, 0.5 * size));

        while (!spawnPoints.isEmpty()) {
            var spawnIndex = prgn.nextInt(spawnPoints.size());
            var spawnCenter = spawnPoints.get(spawnIndex);
            var accepted = false;

            for (int i = 0; i <= samplesBeforeRejection; i++) {
                var angle = prgn.nextFloat() * TAU;
                var multiplier = radius * prgn.nextFloat() * (2.0 * radius - radius);
                var direction = new Double2(
                        (float) (multiplier * Math.sin(angle)),
                        (float) (multiplier * Math.cos(angle))
                );
                var candidate = new Double2(spawnCenter.x() + direction.x(), spawnCenter.y() + direction.y());
                if (isValidSample(candidate, radius, cellSize, size, points, grid)) {
                    points.add(candidate);
                    spawnPoints.add(candidate);

                    var gridIndexX = (int) (candidate.x() / cellSize);
                    var gridIndexY = (int) (candidate.y() / cellSize);

                    grid[gridIndexX][gridIndexY] = points.size();
                    accepted = true;

                    break;
                }
            }

            if (!accepted)
                spawnPoints.remove(spawnIndex);
        }

        return points;
    }

    private boolean isValidSample(Double2 candidate, double radius, double cellSize, int size,
                                  List<Double2> points, Integer[][] grid) {
        var cX = candidate.x();
        var cY = candidate.y();
        var radiusSquared = radius * radius;

        if (cX >= 0.0f && cX <= size && cY >= 0.0f && cY <= size) {
            var cellX = (int) (cX / cellSize);
            var cellY = (int) (cY / cellSize);

            var searchStartX = Integer.max(0, cellX - 2);
            var searchEndX = Integer.min(cellX + 2, grid.length - 1);
            var searchStartY = Integer.max(0, cellY - 2);
            var searchEndY = Integer.min(cellY + 2, grid[0].length - 1);

            for (int x = searchStartX; x <= searchEndX; x++) {
                for (int y = searchStartY; y <= searchEndY; y++) {
                    var pointIndex = grid[x][y] - 1;
                    if (pointIndex != -1) {
                        var point = points.get(pointIndex);
                        if (candidate.distanceSquared(point) < radiusSquared)
                            return false;
                    }
                }
            }

            return true;
        }

        return false;
    }

}

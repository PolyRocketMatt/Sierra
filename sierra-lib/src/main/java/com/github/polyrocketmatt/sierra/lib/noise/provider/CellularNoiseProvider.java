package com.github.polyrocketmatt.sierra.lib.noise.provider;

import com.github.polyrocketmatt.sierra.lib.buffer.AsyncDoubleBuffer;
import com.github.polyrocketmatt.sierra.lib.buffer.AsyncFloatBuffer;
import com.github.polyrocketmatt.sierra.lib.exception.SierraNoiseException;
import com.github.polyrocketmatt.sierra.lib.math.FastMaths;
import com.github.polyrocketmatt.sierra.lib.noise.NoiseType;
import com.github.polyrocketmatt.sierra.lib.noise.NoiseUtils;
import com.github.polyrocketmatt.sierra.lib.noise.data.CellularNoiseData;
import com.github.polyrocketmatt.sierra.lib.utils.TriFunction;

import java.util.function.BiFunction;

import static com.github.polyrocketmatt.sierra.lib.noise.NoiseUtils.CELL_3D;
import static com.github.polyrocketmatt.sierra.lib.noise.NoiseUtils.hash3d;

public class CellularNoiseProvider implements NoiseProvider<CellularNoiseData> {


    @Override
    public void provide(AsyncFloatBuffer buffer, CellularNoiseData data) throws SierraNoiseException {
        buffer.mapIndexed((x, z, value) -> (float) singleCellular2Edge((double) x, 0.0, (double) z, data));
    }

    @Override
    public void provide(AsyncDoubleBuffer buffer, CellularNoiseData data) throws SierraNoiseException {
        buffer.mapIndexed((x, z, value) -> singleCellular2Edge(x, 0.0, z, data));
    }

    @Override
    public float noise(float x, float y, float z, CellularNoiseData data) throws SierraNoiseException {
        return (float) singleCellular2Edge(x, y, z, data);
    }

    @Override
    public double noise(double x, double y, double z, CellularNoiseData data) throws SierraNoiseException {
        return singleCellular2Edge(x, y, z, data);
    }

    private double singleCellular2Edge(double x, double y, double z, CellularNoiseData data) {
        int xr = FastMaths.fastFloor(x);
        int yr = FastMaths.fastFloor(y);
        int zr = FastMaths.fastFloor(z);
        int seed = data.seed();

        double distance = 999999.0;
        double distance2 = 999999.0;

        for (int iX = xr - 1; iX <= xr +1; iX++) {
            for (int iY = yr - 1; iY <= yr + 1; iY++) {
                for (int iZ = zr - 1; iZ <= zr + 1; iZ++) {
                    NoiseUtils.Float3 cell = CELL_3D[hash3d(seed, iX, iY, iZ) & 255];

                    double vecX = iX - x + cell.x();
                    double vecY = iY - y + cell.y();
                    double vecZ = iZ - z + cell.z();
                    double newDistance = data.distance().apply(vecX, vecY, vecZ);

                    distance2 = Math.min(Math.min(distance2, newDistance), distance);
                    distance = Math.min(distance, newDistance);
                }
            }
        }

        return data.filter().apply(distance, distance2);
    }

    @Override
    public NoiseType getProviderType() {
        return NoiseType.CELLULAR;
    }

    public enum DistanceFunction {
        EUCLIDEAN((x, y, z) -> x * x + y * y + z * z),
        MANHATTAN((x, y, z) -> FastMaths.fastAbs(x) + FastMaths.fastAbs(y) + FastMaths.fastAbs(z)),
        NATURAL((x, y, z) -> FastMaths.fastAbs(x) + FastMaths.fastAbs(y) + FastMaths.fastAbs(z) + (x * x + y * y + z * z));

        private final TriFunction<Double, Double, Double, Double> function;

        DistanceFunction(TriFunction<Double, Double, Double, Double> function) {
            this.function = function;
        }

        public double apply(double x, double y, double z) {
            return function.apply(x, y, z);
        }
    }

    public enum FilterFunction {
        DISTANCE((a, b) -> a),
        DISTANCE_2((a, b) -> b - 1),
        DISTANCE_2_ADD((a, b) -> b + a - 1),
        DISTANCE_2_SUB((a, b) -> b - a - 1),
        DISTANCE_2_MUL((a, b) -> b * a - 1),
        DISTANCE_2_DIV((a, b) -> b / a - 1);

        private final BiFunction<Double, Double, Double> filter;

        FilterFunction(BiFunction<Double, Double, Double> filter) {
            this.filter = filter;
        }

        public double apply(double distance, double distance2) {
            return filter.apply(distance, distance2);
        }
    }

}

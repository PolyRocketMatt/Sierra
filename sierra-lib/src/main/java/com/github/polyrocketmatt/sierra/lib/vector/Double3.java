package com.github.polyrocketmatt.sierra.lib.vector;

import com.github.polyrocketmatt.sierra.lib.exception.SierraArgumentException;

public record Double3(double x, double y, double z) implements Vector<Double> {

    @Override
    public Double3 add(Vector<Double> other) {
        if (!(other instanceof Double3 vector))
            throw new SierraArgumentException("'other' must be a Double3, found %s".formatted(other.getClass().getName()));

        return new Double3(this.x + vector.x(), this.y + vector.y(), this.z + vector.z());
    }

    @Override
    public Double3 subtract(Vector<Double> other) {
        if (!(other instanceof Double3 vector))
            throw new SierraArgumentException("'other' must be a Double3, found %s".formatted(other.getClass().getName()));

        return new Double3(this.x - vector.x(), this.y - vector.y(), this.z - vector.z());
    }

    @Override
    public Double3 multiply(Vector<Double> other) {
        if (!(other instanceof Double3 vector))
            throw new SierraArgumentException("'other' must be a Double3, found %s".formatted(other.getClass().getName()));

        return new Double3(this.x * vector.x(), this.y * vector.y(), this.z * vector.z());
    }

    @Override
    public Double3 divide(Vector<Double> other) {
        if (!(other instanceof Double3 vector))
            throw new SierraArgumentException("'other' must be a Double3, found %s".formatted(other.getClass().getName()));
        if (vector.x() == 0.0 || vector.y() == 0.0 || vector.z() == 0.0)
            throw new SierraArgumentException("'other' cannot have a zero component (x: %f, y: %f, z: %f)".formatted(vector.x(), vector.y(), vector.z()));

        return new Double3(this.x / vector.x(), this.y / vector.y(), this.z / vector.z());
    }

    @Override
    public Double3 add(Double other) {
        return new Double3(this.x + other, this.y + other, this.z + other);
    }

    @Override
    public Double3 subtract(Double other) {
        return new Double3(this.x - other, this.y - other, this.z - other);
    }

    @Override
    public Double3 multiply(Double other) {
        return new Double3(this.x * other, this.y * other, this.z * other);
    }

    @Override
    public Double3 divide(Double other) {
        if (other == 0.0)
            throw new SierraArgumentException("'other' cannot be zero");

        return new Double3(this.x / other, this.y / other, this.z / other);
    }

    @Override
    public Double3 negate() {
        return new Double3(-this.x, -this.y, -this.z);
    }

    @Override
    public Double3 normalize() {
        double length = this.length();
        if (length == 0.0)
            throw new SierraArgumentException("Cannot normalize a zero-length vector");
        return new Double3(this.x / length, this.y / length, this.z / length);
    }

    @Override
    public Double dot(Vector<Double> other) {
        if (!(other instanceof Double3 vector))
            throw new SierraArgumentException("'other' must be a Double3, found %s".formatted(other.getClass().getName()));
        return x * vector.x() + y * vector.y();
    }

    @Override
    public Double length() {
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public Double lengthSquared() {
        return x * x + y * y;
    }

    @Override
    public Double distance(Vector<Double> other) {
        if (!(other instanceof Double3 vector))
            throw new SierraArgumentException("'other' must be a Double3, found %s".formatted(other.getClass().getName()));

        double dx = this.x - vector.x();
        double dy = this.y - vector.y();
        double dz = this.z - vector.z();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    @Override
    public Double distanceSquared(Vector<Double> other) {
        if (!(other instanceof Double3 vector))
            throw new SierraArgumentException("'other' must be a Double3, found %s".formatted(other.getClass().getName()));

        double dx = this.x - vector.x();
        double dy = this.y - vector.y();
        double dz = this.z - vector.z();
        return dx * dx + dy * dy + dz * dz;
    }

}


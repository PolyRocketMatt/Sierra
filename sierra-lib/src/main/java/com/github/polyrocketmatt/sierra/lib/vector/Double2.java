package com.github.polyrocketmatt.sierra.lib.vector;

import com.github.polyrocketmatt.sierra.lib.exception.SierraArgumentException;

public record Double2(double x, double y) implements Vector<Double> {

    @Override
    public Double2 add(Vector<Double> other) {
        if (!(other instanceof Double2 vector))
            throw new SierraArgumentException("'other' must be a Double2, found %s".formatted(other.getClass().getName()));

        return new Double2(this.x + vector.x(), this.y + vector.y());
    }

    @Override
    public Double2 subtract(Vector<Double> other) {
        if (!(other instanceof Double2 vector))
            throw new SierraArgumentException("'other' must be a Double2, found %s".formatted(other.getClass().getName()));

        return new Double2(this.x - vector.x(), this.y - vector.y());
    }

    @Override
    public Double2 multiply(Vector<Double> other) {
        if (!(other instanceof Double2 vector))
            throw new SierraArgumentException("'other' must be a Double2, found %s".formatted(other.getClass().getName()));

        return new Double2(this.x * vector.x(), this.y * vector.y());
    }

    @Override
    public Double2 divide(Vector<Double> other) {
        if (!(other instanceof Double2 vector))
            throw new SierraArgumentException("'other' must be a Double2, found %s".formatted(other.getClass().getName()));
        if (vector.x() == 0.0 || vector.y() == 0.0)
            throw new SierraArgumentException("'other' cannot have a zero component (x: %f, y: %f)".formatted(vector.x(), vector.y()));

        return new Double2(this.x / vector.x(), this.y / vector.y());
    }

    @Override
    public Double2 add(Double other) {
        return new Double2(this.x + other, this.y + other);
    }

    @Override
    public Double2 subtract(Double other) {
        return new Double2(this.x - other, this.y - other);
    }

    @Override
    public Double2 multiply(Double other) {
        return new Double2(this.x * other, this.y * other);
    }

    @Override
    public Double2 divide(Double other) {
        if (other == 0.0)
            throw new SierraArgumentException("'other' cannot be zero");

        return new Double2(this.x / other, this.y / other);
    }

    @Override
    public Double2 negate() {
        return new Double2(-this.x, -this.y);
    }

    @Override
    public Double2 normalize() {
        double length = this.length();
        if (length == 0.0)
            throw new SierraArgumentException("Cannot normalize a zero-length vector");
        return new Double2(this.x / length, this.y / length);
    }

    @Override
    public Double dot(Vector<Double> other) {
        if (!(other instanceof Double2 vector))
            throw new SierraArgumentException("'other' must be a Double2, found %s".formatted(other.getClass().getName()));
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
        if (!(other instanceof Double2 vector))
            throw new SierraArgumentException("'other' must be a Double2, found %s".formatted(other.getClass().getName()));

        double dx = this.x - vector.x();
        double dy = this.y - vector.y();
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public Double distanceSquared(Vector<Double> other) {
        if (!(other instanceof Double2 vector))
            throw new SierraArgumentException("'other' must be a Double2, found %s".formatted(other.getClass().getName()));

        double dx = this.x - vector.x();
        double dy = this.y - vector.y();
        return dx * dx + dy * dy;
    }
}

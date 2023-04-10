package com.github.polyrocketmatt.sierra.lib.vector;

import com.github.polyrocketmatt.sierra.lib.exception.SierraArgumentException;

public record Int2(int x, int y) implements Vector<Integer> {

    @Override
    public Int2 add(Vector<Integer> other) {
        if (!(other instanceof Int2 vector))
            throw new SierraArgumentException("'other' must be a Int2, found %s".formatted(other.getClass().getName()));

        return new Int2(this.x + vector.x(), this.y + vector.y());
    }

    @Override
    public Int2 subtract(Vector<Integer> other) {
        if (!(other instanceof Int2 vector))
            throw new SierraArgumentException("'other' must be a Int2, found %s".formatted(other.getClass().getName()));

        return new Int2(this.x - vector.x(), this.y - vector.y());
    }

    @Override
    public Int2 multiply(Vector<Integer> other) {
        if (!(other instanceof Int2 vector))
            throw new SierraArgumentException("'other' must be a Int2, found %s".formatted(other.getClass().getName()));

        return new Int2(this.x * vector.x(), this.y * vector.y());
    }

    @Override
    public Int2 divide(Vector<Integer> other) {
        if (!(other instanceof Int2 vector))
            throw new SierraArgumentException("'other' must be a Int2, found %s".formatted(other.getClass().getName()));
        if (vector.x() == 0 || vector.y() == 0)
            throw new SierraArgumentException("'other' cannot have a zero component (x: %f, y: %f)".formatted(vector.x(), vector.y()));

        return new Int2(this.x / vector.x(), this.y / vector.y());
    }

    @Override
    public Int2 add(Integer other) {
        return new Int2(this.x + other, this.y + other);
    }

    @Override
    public Int2 subtract(Integer other) {
        return new Int2(this.x - other, this.y - other);
    }

    @Override
    public Int2 multiply(Integer other) {
        return new Int2(this.x * other, this.y * other);
    }

    @Override
    public Int2 divide(Integer other) {
        if (other == 0)
            throw new SierraArgumentException("'other' cannot be zero");

        return new Int2(this.x / other, this.y / other);
    }

    @Override
    public Int2 negate() {
        return new Int2(-this.x, -this.y);
    }

    @Override
    public Int2 normalize() {
        float length = this.length();
        if (length == 0.0f)
            throw new SierraArgumentException("Cannot normalize a zero-length vector");
        return new Int2((int) (this.x / length), (int) (this.y / length));
    }

    @Override
    public Integer dot(Vector<Integer> other) {
        if (!(other instanceof Int2 vector))
            throw new SierraArgumentException("'other' must be a Int2, found %s".formatted(other.getClass().getName()));
        return x * vector.x() + y * vector.y();
    }

    @Override
    public Integer length() {
        return (int) Math.sqrt(x * x + y * y);
    }

    @Override
    public Integer lengthSquared() {
        return x * x + y * y;
    }

    @Override
    public Integer distance(Vector<Integer> other) {
        if (!(other instanceof Int2 vector))
            throw new SierraArgumentException("'other' must be a Int2, found %s".formatted(other.getClass().getName()));

        double dx = this.x - vector.x();
        double dy = this.y - vector.y();
        return (int) Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public Integer distanceSquared(Vector<Integer> other) {
        if (!(other instanceof Int2 vector))
            throw new SierraArgumentException("'other' must be a Int2, found %s".formatted(other.getClass().getName()));

        double dx = this.x - vector.x();
        double dy = this.y - vector.y();
        return (int) (dx * dx + dy * dy);
    }

}

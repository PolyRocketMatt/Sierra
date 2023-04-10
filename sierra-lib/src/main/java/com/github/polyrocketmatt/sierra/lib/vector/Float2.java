package com.github.polyrocketmatt.sierra.lib.vector;

import com.github.polyrocketmatt.sierra.lib.exception.SierraArgumentException;

public record Float2(float x, float y) implements Vector<Float> {

    @Override
    public Float2 add(Vector<Float> other) {
        if (!(other instanceof Float2 vector))
            throw new SierraArgumentException("'other' must be a Float2, found %s".formatted(other.getClass().getName()));

        return new Float2(this.x + vector.x(), this.y + vector.y());
    }

    @Override
    public Float2 subtract(Vector<Float> other) {
        if (!(other instanceof Float2 vector))
            throw new SierraArgumentException("'other' must be a Float2, found %s".formatted(other.getClass().getName()));

        return new Float2(this.x - vector.x(), this.y - vector.y());
    }

    @Override
    public Float2 multiply(Vector<Float> other) {
        if (!(other instanceof Float2 vector))
            throw new SierraArgumentException("'other' must be a Float2, found %s".formatted(other.getClass().getName()));

        return new Float2(this.x * vector.x(), this.y * vector.y());
    }

    @Override
    public Float2 divide(Vector<Float> other) {
        if (!(other instanceof Float2 vector))
            throw new SierraArgumentException("'other' must be a Float2, found %s".formatted(other.getClass().getName()));
        if (vector.x() == 0.0f || vector.y() == 0.0f)
            throw new SierraArgumentException("'other' cannot have a zero component (x: %f, y: %f)".formatted(vector.x(), vector.y()));

        return new Float2(this.x / vector.x(), this.y / vector.y());
    }

    @Override
    public Float2 add(Float other) {
        return new Float2(this.x + other, this.y + other);
    }

    @Override
    public Float2 subtract(Float other) {
        return new Float2(this.x - other, this.y - other);
    }

    @Override
    public Float2 multiply(Float other) {
        return new Float2(this.x * other, this.y * other);
    }

    @Override
    public Float2 divide(Float other) {
        if (other == 0.0f)
            throw new SierraArgumentException("'other' cannot be zero");

        return new Float2(this.x / other, this.y / other);
    }

    @Override
    public Float2 negate() {
        return new Float2(-this.x, -this.y);
    }

    @Override
    public Float2 normalize() {
        float length = this.length();
        if (length == 0.0f)
            throw new SierraArgumentException("Cannot normalize a zero-length vector");
        return new Float2(this.x / length, this.y / length);
    }

    @Override
    public Float dot(Vector<Float> other) {
        if (!(other instanceof Float2 vector))
            throw new SierraArgumentException("'other' must be a Float2, found %s".formatted(other.getClass().getName()));
        return x * vector.x() + y * vector.y();
    }

    @Override
    public Float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    @Override
    public Float lengthSquared() {
        return x * x + y * y;
    }

    @Override
    public Float distance(Vector<Float> other) {
        if (!(other instanceof Float2 vector))
            throw new SierraArgumentException("'other' must be a Float2, found %s".formatted(other.getClass().getName()));

        double dx = this.x - vector.x();
        double dy = this.y - vector.y();
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public Float distanceSquared(Vector<Float> other) {
        if (!(other instanceof Float2 vector))
            throw new SierraArgumentException("'other' must be a Float2, found %s".formatted(other.getClass().getName()));

        double dx = this.x - vector.x();
        double dy = this.y - vector.y();
        return (float) (dx * dx + dy * dy);
    }

}

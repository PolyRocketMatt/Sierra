package com.github.polyrocketmatt.sierra.lib.vector;

import com.github.polyrocketmatt.sierra.lib.exception.SierraArgumentException;

public record Float3(float x, float y, float z) implements Vector<Float> {

    @Override
    public Float3 add(Vector<Float> other) {
        if (!(other instanceof Float3 vector))
            throw new SierraArgumentException("'other' must be a Float3, found %s".formatted(other.getClass().getName()));

        return new Float3(this.x + vector.x(), this.y + vector.y(), this.z + vector.z());
    }

    @Override
    public Float3 subtract(Vector<Float> other) {
        if (!(other instanceof Float3 vector))
            throw new SierraArgumentException("'other' must be a Float3, found %s".formatted(other.getClass().getName()));

        return new Float3(this.x - vector.x(), this.y - vector.y(), this.z - vector.z());
    }

    @Override
    public Float3 multiply(Vector<Float> other) {
        if (!(other instanceof Float3 vector))
            throw new SierraArgumentException("'other' must be a Float3, found %s".formatted(other.getClass().getName()));

        return new Float3(this.x * vector.x(), this.y * vector.y(), this.z * vector.z());
    }

    @Override
    public Float3 divide(Vector<Float> other) {
        if (!(other instanceof Float3 vector))
            throw new SierraArgumentException("'other' must be a Float3, found %s".formatted(other.getClass().getName()));
        if (vector.x() == 0.0f || vector.y() == 0.0f || vector.z() == 0.0f)
            throw new SierraArgumentException("'other' cannot have a zero component (x: %f, y: %f, z: %f)".formatted(vector.x(), vector.y(), vector.z()));

        return new Float3(this.x / vector.x(), this.y / vector.y(), this.z / vector.z());
    }

    @Override
    public Float3 add(Float other) {
        return new Float3(this.x + other, this.y + other, this.z + other);
    }

    @Override
    public Float3 subtract(Float other) {
        return new Float3(this.x - other, this.y - other, this.z - other);
    }

    @Override
    public Float3 multiply(Float other) {
        return new Float3(this.x * other, this.y * other, this.z * other);
    }

    @Override
    public Float3 divide(Float other) {
        if (other == 0.0f)
            throw new SierraArgumentException("'other' cannot be zero");

        return new Float3(this.x / other, this.y / other, this.z / other);
    }

    @Override
    public Float3 negate() {
        return new Float3(-this.x, -this.y, -this.z);
    }

    @Override
    public Float3 normalize() {
        float length = this.length();
        if (length == 0.0f)
            throw new SierraArgumentException("Cannot normalize a zero-length vector");
        return new Float3(this.x / length, this.y / length, this.z / length);
    }

    @Override
    public Float dot(Vector<Float> other) {
        if (!(other instanceof Float3 vector))
            throw new SierraArgumentException("'other' must be a Float3, found %s".formatted(other.getClass().getName()));
        return x * vector.x() + y * vector.y() + z * vector.z();
    }

    @Override
    public Float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    public Float lengthSquared() {
        return x * x + y * y + z * z;
    }
}
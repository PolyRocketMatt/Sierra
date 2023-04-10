package com.github.polyrocketmatt.sierra.lib.vector;

public interface Vector<T> {

    Vector<T> add(Vector<T> other);
    Vector<T> subtract(Vector<T> other);
    Vector<T> multiply(Vector<T> other);
    Vector<T> divide(Vector<T> other);

    Vector<T> add(T other);
    Vector<T> subtract(T other);
    Vector<T> multiply(T other);
    Vector<T> divide(T other);

    Vector<T> negate();
    Vector<T> normalize();

    T dot(Vector<T> other);
    T length();
    T lengthSquared();
    T distance(Vector<T> other);
    T distanceSquared(Vector<T> other);

}

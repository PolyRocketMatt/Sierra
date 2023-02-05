package com.github.polyrocketmatt.sierra.lib.utils;

import java.util.function.Function;

/**
 * Represents a function that accepts two arguments and produces a result.
 * This is the three-arity specialization of {@link Function}.
 *
 * @param <S> the type of the first argument to the function
 * @param <T> the type of the second argument to the function
 * @param <U> the type of the third argument to the function
 * @param <R> the type of the result of the function
 *
 * @see Function
 * @since 1.8
 */
@FunctionalInterface
public interface TriFunction<S, T, U, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param s the first function argument
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    R apply(S s, T t, U u);
}

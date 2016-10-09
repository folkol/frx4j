package com.folkol.rx;

import java.util.function.Function;

/**
 * <p>An Operator is a Function that creates the Observer to be used when Chaining Observables together.</p>
 *
 * Terminology:
 * <ul>
 *     <li>Upstream Observer: The Observer to be used when subscribing to the Observer on which we called ::chain.</li>
 *     <li>Downstream Observer: The Observer that was passed in to the subscribe call of the new Observable.</li>
 * </ul>
 *
 * @param <T> The type of the downstream Observer.
 * @param <R> The type of the upstream Observer.
 */
abstract public class Operator<T, R> implements Function<Observer<R>, Observer<T>>
{
}

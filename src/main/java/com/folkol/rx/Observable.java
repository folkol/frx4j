package com.folkol.rx;

import java.util.function.Consumer;

/**
 * <p>An {@code Observable} represents a series of <em>callbacks</em>.</p>
 *
 * <p>An {@link Observer} <em>subscribes</em> to an {@code Observable} by calling its
 * {@link Observable#subscribe} method. Throughout this subscription, the
 * Observable will call {@link Observer#onNext onNext} zero or more times,
 * and then optionally call <em>either</em> {@link Observer#onCompleted onCompleted}
 * <em>or</em> {@link Observer#onError onError} zero or one time.</p>
 */
public class Observable
{
    private Consumer<Observer> onSubscribe;

    /**
     * @param onSubscribe A callback function that will be called when someone subscribes to this Observable.
     */
    public Observable(Consumer<Observer> onSubscribe)
    {
        this.onSubscribe = onSubscribe;
    }

    public void subscribe()
    {
        onSubscribe.accept(null);
    }
}

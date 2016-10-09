package com.folkol.rx;

import java.util.function.Consumer;

/**
 * <p>An {@code Observable} represents a stream of, possibly not yet obtained, <em>items</em>.</p>
 *
 * <p>An {@link Observer} can <em>subscribe</em> to an {@code Observable} by calling its
 * {@link Observable#subscribe} method. Throughout this subscription, the {@code Observable} will call
 * {@link Observer#onNext} for every item it wants to <em>emit</em> — if any at all.</p>
 *
 * <p>If the Observable will produce no more items, it <em>may</em> call <strong>either</strong>
 * {@link Observer#onCompleted} <strong>or</strong> {@link Observer#onError} <strong>at most</strong>
 * one (1) time.</p>
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

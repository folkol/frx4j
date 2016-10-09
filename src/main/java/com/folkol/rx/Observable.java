package com.folkol.rx;

import java.util.function.Consumer;
import java.util.function.Function;

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
public class Observable<T>
{
    private Consumer<Observer<T>> onSubscribe;

    /**
     * @param onSubscribe A callback function that will be called when someone subscribes to this Observable.
     */
    public Observable(Consumer<Observer<T>> onSubscribe)
    {
        this.onSubscribe = onSubscribe;
    }

    public void subscribe(Observer<T> observer)
    {
        onSubscribe.accept(observer);
    }

    /**
     * Creates a <em>new Observable</em> that will, when subscribed to, in turn subscribe to
     * this Observable — using the Observer supplied by the given operator.
     *
     * @param operator The Operator that will supply the delegating Observer.
     * @return A new Observable that is chained to this one.
     */
    public <R> Observable<R> chain(Function<Observer<R>, Observer<T>> operator)
    {
        return new Observable<>(observer -> onSubscribe.accept(operator.apply(observer)));
    }


    //-----------------------------------------------------------------------------------------
    // The methods below are not defining properties of the Observable, but rather convenience
    // methods to make working with Observables easier.
    //-----------------------------------------------------------------------------------------

    /**
     * Subscribes to this Observable with a NO-OP-Observer.
     */
    public void subscribe()
    {
        subscribe(item -> {}, () -> {}, throwable -> {});
    }

    public void subscribe(Consumer<T> onNext)
    {
        subscribe(onNext, () -> {}, x -> {});
    }

    public void subscribe(Consumer<T> onNext, Runnable onCompleted)
    {
        subscribe(onNext, onCompleted, x -> {});
    }

        public void subscribe(Consumer<T> onNext, Runnable onCompleted, Consumer<Throwable> onError)
    {
        subscribe(new Observer<T>()
        {
            @Override
            public void onNext(T item)
            {
                onNext.accept(item);
            }

            @Override
            public void onCompleted()
            {
                onCompleted.run();
            }

            @Override
            public void onError(Throwable t)
            {
                onError.accept(t);
            }
        });
    }
}

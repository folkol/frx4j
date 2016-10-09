package com.folkol.rx;

import com.folkol.rx.operators.FilteringOperator;
import com.folkol.rx.operators.MappingOperator;
import com.folkol.rx.operators.MergingOperator;
import com.folkol.rx.operators.SubscribeOnOperator;
import com.folkol.rx.util.Schedulers;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * <p>
 * An {@code Observable} represents a stream of, possibly not yet obtained, <em>items</em>.
 * </p>
 * <p>
 * An {@link Observer} can <em>subscribe</em> to an {@code Observable} by calling its
 * {@link Observable#subscribe} method. Throughout this subscription, the {@code Observable}
 * will call {@link Observer#onNext} for every item it wants to <em>emit</em> — if any at all.
 * </p>
 * <p>
 * If the Observable will produce no more items, it <em>may</em> call <strong>either</strong>
 * {@link Observer#onCompleted} <strong>or</strong> {@link Observer#onError} <strong>at most</strong>
 * one (1) time.
 * </p>
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
        try {
            onSubscribe.accept(observer);
        } catch (Throwable t) {
            // This is just to demonstrate how exceptions are "converted" to onError-calls.
            // A proper implementation must make sure that we do not break the contract.
            observer.onError(t);
        }
    }

    /**
     * Creates a <em>new Observable</em> that will, when subscribed to, in turn subscribe
     * to this Observable — using the Observer supplied by the given operator.
     *
     * @param operator The Operator that will supply the delegating Observer.
     * @return A new Observable that is chained to this one.
     */
    public <R> Observable<R> chain(Function<Observer<R>, Observer<T>> operator)
    {
        return new Observable<>(observer -> onSubscribe.accept(operator.apply(observer)));
    }


    //------------------------------------------------------------------------------//
    //                                                                              //
    // The methods below are not defining properties of the Observable, but rather  //
    // convenience methods that makes working with Observables a bit more pleasant. //
    //                                                                              //
    //------------------------------------------------------------------------------//

    public static <T> Observable<T> from(Iterable<T> ts)
    {
        return new Observable<>(observer -> {
            ts.forEach(observer::onNext);
            observer.onCompleted();
        });
    }

    public static <T> Observable<T> just(T t)
    {
        return Observable.from(Collections.singleton(t));
    }

    public static <T> Observable<T> just(T t1, T t2)
    {
        return Observable.from(Arrays.asList(t1, t2));
    }

    public static <T> Observable<T> just(T t1, T t2, T t3)
    {
        return Observable.from(Arrays.asList(t1, t2, t3));
    }

    /**
     * Creates an Observable that will emit no items and then call onComplete.
     */
    public static <T> Observable<T> empty()
    {
        return new Observable<>(Observer::onCompleted);
    }

    /**
     * Some convenience methods that creates Observers from callbacks
     */
    public void subscribe()
    {
        subscribe(item -> {}, () -> {}, throwable -> {});
    }

    public void subscribe(Consumer<T> onNext)
    {
        subscribe(onNext, () -> {}, throwable -> {});
    }

    public void subscribe(Consumer<T> onNext, Runnable onCompleted)
    {
        subscribe(onNext, onCompleted, throwable -> {});
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

    /**
     * Creates a new Observable that will only pass-through items matching the predicate.
     */
    public Observable<T> filter(Predicate<T> predicate)
    {
        return chain(new FilteringOperator<>(predicate));
    }

    /**
     * Creates a new Observable that will pass-through items after applying the given map function to them.
     */
    public <R> Observable<R> map(Function<T, R> f)
    {
        return chain(new MappingOperator<>(f));
    }

    /**
     * Creates a new Observable that will subscribe to its upstream Observable on the given scheduler, and then
     * pass-through all items.
     */
    public Observable<T> subscribeOn(Scheduler scheduler)
    {
        Observable<Observable<T>> nested = Observable.just(this);
        return nested.chain(new SubscribeOnOperator<>(scheduler));
    }

    /**
     * <p>
     * Creates a new Observable that will merge the emits from all Observables emitted by upstream.
     * </p>
     * <p>
     *     This operator will
     * </p>
     */
    public static <T> Observable<T> merge(Observable<Observable<T>> source)
    {
        return merge(source, Schedulers.immediate());
    }

    public static <T> Observable<T> merge(Observable<Observable<T>> source, Scheduler scheduler)
    {
        return source.chain(new MergingOperator<>(scheduler));
    }
}

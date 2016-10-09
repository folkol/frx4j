package com.folkol.rx;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static java.util.function.Function.identity;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ObservableTest
{
    @Test
    public void onSubscribeDeferred() throws Exception {
        CompletableFuture<?> future = new CompletableFuture<>();
        Consumer<Observer<Object>> onSubscribe = observer -> future.complete(null);

        Observable<?> observable = new Observable<>(onSubscribe);
        assertFalse(future.isDone());

        observable.subscribe();
        assertTrue(future.isDone());
    }

    @Test
    public void chainCreatesNewObservable() throws Exception {
        Consumer<Observer<Object>> onSubscribe = x -> {};
        Observable<Object> original = new Observable<>(onSubscribe);

        Observable<Object> chained = original.chain(identity());

        assertFalse(original == chained);
    }

    @Test
    public void chainDeferredSubscription() throws Exception {
        CompletableFuture<?> future = new CompletableFuture<>();

        Observable<Object> original = new Observable<>(observer -> future.complete(null));
        assertFalse(future.isDone());

        Observable<?> chained = original.chain(x -> x);
        assertFalse(future.isDone());

        chained.subscribe();
        assertTrue(future.isDone());
    }
}

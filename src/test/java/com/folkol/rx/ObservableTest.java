package com.folkol.rx;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ObservableTest
{
    @Test
    public void onSubscribeDeferred() throws Exception {
        CompletableFuture<?> future = new CompletableFuture<>();

        Observable<?> observable = new Observable<>(observer -> future.complete(null));
        assertFalse(future.isDone());

        observable.subscribe();
        assertTrue(future.isDone());
    }

    @Test
    public void chainCreatesNewObservable() throws Exception {
        Observable<Object> original = new Observable<>(x -> {});

        Observable<?> chained = original.chain(x -> x);

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

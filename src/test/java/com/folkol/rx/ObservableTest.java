package com.folkol.rx;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static java.util.function.Function.identity;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ObservableTest
{
    @Test
    public void onSubscribeDeferred() throws Exception {
        CompletableFuture<?> future = new CompletableFuture<>();
        Consumer<Observer<Object>> onSubscribe = observer -> future.complete(null);

        Observable<?> observable = new Observable<>(onSubscribe);
        assertFalse("onSubscribe shouldn't have been called yet.", future.isDone());

        observable.subscribe();
        assertTrue("onSubscribe should have been called by now", future.isDone());
    }

    @Test
    public void chainCreatesNewObservable() throws Exception {
        Consumer<Observer<Object>> onSubscribe = x -> {};
        Observable<Object> original = new Observable<>(onSubscribe);

        Observable<Object> chained = original.chain(identity());

        assertFalse("::chain should return a new Observable", original == chained);
    }

    @Test
    public void chainDeferredSubscription() throws Exception {
        CompletableFuture<?> future = new CompletableFuture<>();
        Consumer<Observer<Object>> onSubscribe = observer -> future.complete(null);

        Observable<Object> original = new Observable<>(onSubscribe);
        assertFalse("onSubscribe shouldn't have been called yet.", future.isDone());

        Observable<?> chained = original.chain(identity());
        assertFalse("onSubscribe shouldn't have been called yet.", future.isDone());

        chained.subscribe();
        assertTrue("onSubscribe should have been called by now", future.isDone());
    }

    @Test
    public void throwFromCallbackCausesOnError() throws Exception {
        CompletableFuture<Throwable> future = new CompletableFuture<>();
        Consumer<Observer<Object>> onSubscribe = observer -> observer.onNext(null);

        Observable<Object> observable = new Observable<>(onSubscribe);
        assertFalse("onSubscribe shouldn't have been called yet.", future.isDone());

        observable.subscribe(item -> fail(), () -> {}, future::complete);
        assertTrue("onSubscribe should have been called by now", future.isDone());
    }
}

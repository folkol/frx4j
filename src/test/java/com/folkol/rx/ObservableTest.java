package com.folkol.rx;

import com.folkol.rx.util.Schedulers;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.util.function.Function.identity;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ObservableTest
{
    @Test
    public void onSubscribeDeferred() throws Exception
    {
        CompletableFuture<?> future = new CompletableFuture<>();

        Observable<?> observable = new Observable<>(observer -> future.complete(null));
        assertFalse("onSubscribe shouldn't have been called yet.", future.isDone());

        observable.subscribe();
        assertTrue("onSubscribe should have been called by now", future.isDone());
    }

    @Test
    public void chainCreatesNewObservable() throws Exception
    {
        Observable<?> original = Observable.empty();

        Observable<?> chained = original.chain(identity());

        assertFalse("::chain should return a new Observable", original == chained);
    }

    @Test
    public void chainDefersSubscription() throws Exception
    {
        CompletableFuture<?> future = new CompletableFuture<>();

        Observable<?> chained =
            new Observable<>(observer -> future.complete(null))
                .chain(identity());
        assertFalse("onSubscribe shouldn't have been called yet.", future.isDone());

        chained.subscribe();
        assertTrue("onSubscribe should have been called by now", future.isDone());
    }

    @Test
    public void throwFromCallbackCausesOnError() throws Exception
    {
        CompletableFuture<Throwable> future = new CompletableFuture<>();
        Observable<Object> observable = Observable.just(null);

        observable.subscribe(item -> fail(), () -> {}, future::complete);

        assertTrue("onSubscribe should have been called by now", future.isDone());
    }

    @Test
    public void testScheduleOn()
        throws Exception
    {
        CompletableFuture<Thread> future = new CompletableFuture<>();

        Observable.just(null)
            .subscribeOn(Schedulers.newThread())
            .subscribe(item -> future.complete(Thread.currentThread()));

        Thread thread = future.get(10, TimeUnit.SECONDS);
        assertNotEquals("Expected the onNext callback from another Thread", Thread.currentThread(), thread);
        assertTrue(future.get().getName().startsWith("schedulers-new-thread-"));
    }
}

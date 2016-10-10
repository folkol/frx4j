package com.folkol.rx.operators;

import com.folkol.rx.Observable;
import com.folkol.rx.util.Schedulers;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotEquals;

public class ObserveOnOperatorTest
{
    @Test
    public void testObserveOn() throws Exception
    {
        CompletableFuture<Thread> onSubmitThread = new CompletableFuture<>();
        CompletableFuture<Thread> onNextThread = new CompletableFuture<>();
        Observable<Object> observable =
            new Observable<>(observer -> {
                onSubmitThread.complete(Thread.currentThread());
                observer.onNext(null);
            });

        observable
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe(item -> onNextThread.complete(Thread.currentThread()));

        Thread t1 = onSubmitThread.get(1, TimeUnit.SECONDS);
        Thread t2 = onNextThread.get(1, TimeUnit.SECONDS);

        assertNotEquals("Expected the onSubmit callback from another Thread", Thread.currentThread(), t1);
        assertNotEquals("Expected the onNext callback from another Thread", Thread.currentThread(), t2);
        assertNotEquals("Expected the onSubmit and onNext callback from different Threads", t1, t2);
    }
}

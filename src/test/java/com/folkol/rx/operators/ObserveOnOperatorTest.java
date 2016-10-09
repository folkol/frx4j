package com.folkol.rx.operators;

import com.folkol.rx.Observable;
import com.folkol.rx.util.Schedulers;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ObserveOnOperatorTest
{
    @Test
    public void testObserveOn() throws Exception
    {
        CompletableFuture<Thread> future = new CompletableFuture<>();

        Observable.just(null)
            .observeOn(Schedulers.newThread())
            .subscribe(item -> future.complete(Thread.currentThread()));

        Thread thread = future.get(10, TimeUnit.SECONDS);
        assertNotEquals("Expected the onNext callback from another Thread", Thread.currentThread(), thread);
        assertTrue(future.get().getName().startsWith("schedulers-new-thread-"));
    }
}

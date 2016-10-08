package com.folkol.rx;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ObservableTest
{
    @Test
    public void onSubscribeDeferredUntilSubscription() throws Exception {
        CompletableFuture<?> f = new CompletableFuture<>();

        Observable observable = new Observable(observer -> f.complete(null));
        assertFalse(f.isDone());

        observable.subscribe();
        assertTrue(f.isDone());
    }
}

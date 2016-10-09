package com.folkol.rx.operators;

import com.folkol.rx.Observable;
import com.folkol.rx.util.Schedulers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MergingOperatorTest
{
    private static final int TIME_PER_ITEM = 1000;
    private static final int NUM_ITEMS = 100;

    CountDownLatch latch = new CountDownLatch(1);

    @Test(timeout = TIME_PER_ITEM * 2)
    public void canMergeInParallel() throws Exception
    {
        Observable<Observable<Integer>> source = bunchOfObserables();

        Observable<Integer> merge = Observable.merge(source, Schedulers.io());

        List<Integer> xs = collectItems(merge);
        assertEquals(NUM_ITEMS, xs.size());
        for (int i = 0; i < NUM_ITEMS; i++) {
            xs.contains(i);
        }
    }

    private List<Integer> collectItems(Observable<Integer> merge) throws InterruptedException
    {
        List<Integer> items = new ArrayList<>();

        merge.subscribe(items::add, latch::countDown);

        assertTrue(latch.await(TIME_PER_ITEM * 2, MILLISECONDS));
        return items;
    }

    private Observable<Observable<Integer>> bunchOfObserables()
    {
        List<Observable<Integer>> observables = new ArrayList<>();
        for (int i = 0; i < NUM_ITEMS; i++) {
            observables.add(blockingAndSlow(i));
        }
        return Observable.from(observables);
    }

    private Observable<Integer> blockingAndSlow(int item)
    {
        return new Observable<>(observer -> {
            try {
                Thread.sleep(TIME_PER_ITEM);
            } catch (InterruptedException e) {
            }
            observer.onNext(item);
            observer.onCompleted();
        });
    }
}
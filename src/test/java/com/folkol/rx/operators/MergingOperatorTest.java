package com.folkol.rx.operators;

import com.folkol.rx.Observable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class MergingOperatorTest
{
    CountDownLatch latch = new CountDownLatch(1);

    @Test
    public void testMergeItems() throws Exception
    {
        Observable<Observable<Integer>> source =
            Observable.just(
                Observable.just(1),
                Observable.just(10),
                Observable.just(666)
            );

        Observable<Integer> merge = Observable.merge(source);

        List<Integer> xs = new ArrayList<>();
        merge.subscribe(xs::add, latch::countDown);

        latch.await(1, TimeUnit.SECONDS);

        assertEquals(3, xs.size());
        assertEquals(Integer.valueOf(1), xs.get(0));
        assertEquals(Integer.valueOf(10), xs.get(1));
        assertEquals(Integer.valueOf(666), xs.get(2));
    }
}
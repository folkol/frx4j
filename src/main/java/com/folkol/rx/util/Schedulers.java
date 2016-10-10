package com.folkol.rx.util;

import com.folkol.rx.Scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * There should be a Worker-abstraction in between the Schedulers
 * and the Observers. Since this is not in place, only immediate and
 * newThread is available.
 */
public class Schedulers
{
    private static final AtomicLong id = new AtomicLong();

    /**
     * {@code newThread()} creates a Worker that schedules work on a
     * {@link Executors#newSingleThreadExecutor new SingleThreadExecutor}.
     */
    public static Scheduler newThread()
    {
        return () -> new Worker()
        {
            ExecutorService es = Executors.newSingleThreadExecutor(runnable -> {
                Thread thread = new Thread(runnable);
                thread.setDaemon(true);
                thread.setName("schedulers-thread-" + id.incrementAndGet());
                return thread;
            });

            @Override
            public void schedule(Runnable performOnSubscribe)
            {
                es.submit(performOnSubscribe);
            }
        };
    }

    /**
     * <p>
     * {@code immediate()} creates a Worker that calls onSubscribe on the current thread.
     * </p>
     * <p>
     * Note that this means "whatever thread THIS observable's schedule-call is made from",
     * not necessarily the same thread as the original call to subscribe() was called from.
     * </p>
     */
    public static Scheduler immediate()
    {
        return () -> Runnable::run;
    }
}

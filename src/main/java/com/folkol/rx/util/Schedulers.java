package com.folkol.rx.util;

import com.folkol.rx.Scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.String.format;

public class Schedulers
{

    /**
     * Produces daemons!
     */
    private static final AtomicLong id = new AtomicLong();
    private static ThreadFactory twistingNether(String kind)
    {
        return runnable -> {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            thread.setName(format("schedulers-%s-%d", kind, id.incrementAndGet()));
            return thread;
        };
    }

    private static final ExecutorService io = Executors.newCachedThreadPool(twistingNether("io"));
    private static final ExecutorService computation = Executors.newFixedThreadPool(10, twistingNether("computation"));

    /**
     * {@code newThread()} will create a new thread to schedule work on.
     */
    public static Scheduler newThread()
    {
        return new Scheduler()
        {
            ExecutorService es = Executors.newSingleThreadExecutor(twistingNether("new-thread"));

            @Override
            public void schedule(Runnable performOnSubscribe)
            {
                es.submit(performOnSubscribe);
            }
        };
    }

    /**
     * {@code io()} is backed by a CachedThreadPool
     */
    public static Scheduler io()
    {
        return io::submit;
    }

    /**
     * {@code computation()} is backed by a FixedThreadPool
     */
    public static Scheduler computation()
    {
        return computation::submit;
    }

    /**
     * <p>
     * {@code immediate()} will call onSubscribe on the current thread.
     * </p>
     * <p>
     * Note that this means "whatever thread THIS observable's schedule-call is made from",
     * not necessarily the same thread as the original call to subscribe() was called from.
     * </p>
     */
    public static Scheduler immediate()
    {
        return Runnable::run;
    }
}

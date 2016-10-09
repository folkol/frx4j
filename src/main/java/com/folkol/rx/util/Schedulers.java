package com.folkol.rx.util;

import com.folkol.rx.Scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class Schedulers
{
    private static final AtomicLong id = new AtomicLong();

    private static ThreadFactory daemons()
    {
        return r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("schedulers-io-" + id.incrementAndGet());
            return thread;
        };
    }

    private static final ExecutorService io = Executors.newCachedThreadPool(daemons());
    private static final ExecutorService computation = Executors.newFixedThreadPool(10, daemons());

    public static Scheduler newThread()
    {
        ExecutorService es = Executors.newSingleThreadExecutor(r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("schedulers-new-thread-" + id.incrementAndGet());
            return thread;
        });
        return es::submit;
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

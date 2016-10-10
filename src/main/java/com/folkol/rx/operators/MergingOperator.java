package com.folkol.rx.operators;

import com.folkol.rx.Observable;
import com.folkol.rx.Observer;
import com.folkol.rx.Scheduler;
import com.folkol.rx.util.Schedulers;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * This Operator will return an Observer that will subscribe to all Observables that it
 * is notified about, and relay all items that they in turn emits to the downstream Observer.
 */
public class MergingOperator<T> implements Function<Observer<T>, Observer<Observable<T>>>
{
    private final AtomicInteger numObservables = new AtomicInteger();
    private final AtomicBoolean sourceCompleted = new AtomicBoolean();
    private final ConcurrentLinkedQueue<T> items = new ConcurrentLinkedQueue<>();
    private final Scheduler drainingScheduler = Schedulers.newThread();

    private Observer<T> observer;

    @Override
    public Observer<Observable<T>> apply(Observer<T> observer)
    {
        this.observer = observer;
        return new Observer<Observable<T>>()
        {
            @Override
            public void onNext(Observable<T> observable)
            {
                numObservables.incrementAndGet();
                observable
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(MergingOperator.this::addAndScheduleDrain, numObservables::decrementAndGet);
            }

            @Override
            public void onCompleted()
            {
                sourceCompleted.set(true);
                drainingScheduler.schedule(MergingOperator.this::drain);
            }

            @Override
            public void onError(Throwable t)
            {

            }
        };
    }

    private void addAndScheduleDrain(T t)
    {
        items.add(t);
        drainingScheduler.schedule(this::drain);
    }

    private final Object drainMutex = new Object[0];

    private void drain()
    {
        synchronized (drainMutex) {
            Iterator<T> iterator = items.iterator();
            while (iterator.hasNext()) {
                observer.onNext(iterator.next());
                iterator.remove();
            }
            if (sourceCompleted.get() && numObservables.get() == 0) {
                drainingScheduler.schedule(observer::onCompleted);
            }
        }
    }
}

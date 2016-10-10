package com.folkol.rx.operators;

import com.folkol.rx.Observable;
import com.folkol.rx.Observer;
import com.folkol.rx.Scheduler;
import com.folkol.rx.util.Worker;

import java.util.function.Function;

public class ObserveOnOperator<T> implements Function<Observer<T>, Observer<Observable<T>>>
{
    private Worker scheduler;

    public ObserveOnOperator(Scheduler scheduler)
    {
        this.scheduler = scheduler.createWorker();
    }

    @Override
    public Observer<Observable<T>> apply(Observer<T> observer)
    {
        return new Observer<Observable<T>>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                observer.onError(e);
            }

            // This single emit will come from our nested Observable that we create in Observable::subscribeOn
            @Override
            public void onNext(final Observable<T> observable)
            {
                observable.subscribe(new Observer<T>()
                {
                    @Override
                    public void onNext(T item)
                    {
                        scheduler.schedule(() -> observer.onNext(item));
                    }

                    @Override
                    public void onCompleted()
                    {
                        scheduler.schedule(observer::onCompleted);
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        scheduler.schedule(() -> observer.onError(t));
                    }
                });
            }
        };
    }
}

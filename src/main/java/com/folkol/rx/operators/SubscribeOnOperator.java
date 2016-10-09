package com.folkol.rx.operators;

import com.folkol.rx.Observable;
import com.folkol.rx.Observer;
import com.folkol.rx.Scheduler;

import java.util.function.Function;

public class SubscribeOnOperator<T> implements Function<Observer<T>, Observer<Observable<T>>>
{
    private Scheduler scheduler;

    public SubscribeOnOperator(Scheduler scheduler)
    {
        this.scheduler = scheduler;
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
                scheduler.schedule(() -> {
                    observable.subscribe(new Observer<T>()
                    {

                        @Override
                        public void onCompleted()
                        {
                            observer.onCompleted();
                        }

                        @Override
                        public void onError(Throwable e)
                        {
                            observer.onError(e);
                        }

                        @Override
                        public void onNext(T t)
                        {
                            observer.onNext(t);
                        }
                    });
                });
            }
        };
    }
}

package com.folkol.rx.operators;

import com.folkol.rx.Observer;

import java.util.function.Function;
import java.util.function.Predicate;

public class FilteringOperator<T> implements Function<Observer<T>, Observer<T>>
{
    private Predicate<T> predicate;

    public FilteringOperator(Predicate<T> predicate)
    {
        this.predicate = predicate;
    }

    @Override
    public Observer<T> apply(Observer<T> observer)
    {
        return new Observer<T>()
        {
            @Override
            public void onCompleted()
            {
                observer.onCompleted();
            }

            @Override
            public void onError(Throwable t)
            {
                observer.onError(t);
            }

            @Override
            public void onNext(T item)
            {
                if (predicate.test(item)) {
                    observer.onNext(item);
                }
            }
        };
    }
}
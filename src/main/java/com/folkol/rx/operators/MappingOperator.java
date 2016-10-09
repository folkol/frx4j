package com.folkol.rx.operators;

import com.folkol.rx.Observer;

import java.util.function.Function;

public class MappingOperator<T, R> implements Function<Observer<R>, Observer<T>>
{
    private Function<T, R> map;

    public MappingOperator(Function<T, R> map)
    {
        this.map = map;
    }

    @Override
    public Observer<T> apply(Observer<R> observer)
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
                observer.onNext(map.apply(item));
            }
        };
    }
}

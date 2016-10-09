package com.folkol.rx.operators;

import com.folkol.rx.Observable;
import com.folkol.rx.Observer;

import java.util.function.Function;

public class MergingOperator<T> implements Function<Observer<Observer<T>>, Observer<T>>
{
//    @Override
//    public Observer<T> apply(Observer<T> observer)
//    {
//        return new Observer<T>()
//        {
//            @Override
//            public void onCompleted()
//            {
//                observer.onCompleted();
//            }
//
//            @Override
//            public void onError(Throwable t)
//            {
//                observer.onError(t);
//            }
//
//            @Override
//            public void onNext(T item)
//            {
//                if (predicate.test(item)) {
//                    observer.onNext(item);
//                }
//            }
//        };
//    }
//

    @Override
    public Observer<T> apply(Observer<Observer<T>> observerObserver)
    {
        return null;
    }
}

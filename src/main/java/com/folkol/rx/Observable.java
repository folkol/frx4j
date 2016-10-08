package com.folkol.rx;

import java.util.function.Consumer;

public class Observable
{
    private Consumer<Observer> onSubscribe;

    /**
     * @param onSubscribe A callback function that will be called when someone subscribes to this Observable.
     */
    public Observable(Consumer<Observer> onSubscribe)
    {
        this.onSubscribe = onSubscribe;
    }

    public void subscribe()
    {
        onSubscribe.accept(null);
    }
}

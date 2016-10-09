package com.folkol.rx;

public interface Scheduler
{
    void schedule(Runnable performOnSubscribe);
}

package com.folkol.rx;

import com.folkol.rx.util.Worker;

public interface Scheduler
{
    Worker createWorker();
}

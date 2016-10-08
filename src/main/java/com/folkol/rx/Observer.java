package com.folkol.rx;

/**
 * An {@code Observer} provides callbacks for {@code Observables} to use when they want to
 * send notifications.
 */
public interface Observer<T>
{
    /**
     * <p>{@code onNext} will be called when an Observable 'emits' an {@code item}.</p>
     *
     * <p>{@code onNext} might be called from different threads, but it will
     * never be called concurrently. It is the Observable's responsibility
     * to establish a happens-before-relationship in between calls to this
     * method for a given subscription.</p>
     *
     * @param item The item emitted by the Observable.
     */
    void onNext(T item);

    /**
     * <p>{@code onCompleted} may be called by the Observable to notify its
     * Observers that there are no more items to emit.</p>
     *
     * <p>If {@code onCompleted} is called, the Observer will not call {@code onError}
     * for this subscription.</p>
     */
    void onCompleted();

    /**
     * <p>{@code onError} may be called by the Observable to notify its
     * Observers that there has been an accident, and no more items can be emitted.</p>
     *
     * <p>If {@code onError} is called, the Observer will not call {@code onCompleted}
     * for this subscription.</p>
     */
    void onError(Throwable t);
}

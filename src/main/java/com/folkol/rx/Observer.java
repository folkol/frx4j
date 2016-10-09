package com.folkol.rx;

/**
 * An {@code Observer} provides callbacks for {@link Observable Observables}
 * to use when they want to send notifications.
 */
public interface Observer<T>
{
    /**
     * <p>{@code onNext} will be called when an {@link Observable} <em>emits</em>
     * an <em>item</em>.</p>
     *
     * <p><b>N.b.</b> {@code onNext} might be called from different threads, but it will
     * never be called concurrently. It is the {@link Observable Observable's} responsibility
     * to establish a <em>happens-before-relationship</em> in between calls to this
     * method for a given <em>subscription</em>.</p>
     *
     * @param item The <em>item emitted</em> by the {@link Observable}.
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

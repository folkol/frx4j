# frx4j — Folkol's Reactive Extensions for Java

**frx4j** is intended — for several reasons — to be a bare-bones implementation of _a subset_ of [ReactiveX](http://reactivex.io). Partially because I am a lazy bum, but also because I want to demonstrate the core contepts of RX to those who prefer reading code over staring at [marble diagrams](http://rxmarbles.com).

> **N.b.** Like most other projects that I upload to GitHub, this hack is only intended for education and recreation. _If you want to use RX in a real Java app, use [a proper implementation](https://github.com/ReactiveX/RxJava) instead._

# Build

    $ gradle

# Core concepts

- **Observable Stream:** An _Observable Stream_ is a _push-based_ stream of *Items*.
- **Observable:** An _Observable_ (that is, an instance of the class _Observable_) is the _producer_ of a stream of _Items_.
- **Observer:** An _Observer_ (that is, an instance of the interface _Observer_) is the _consumer_ of a stream of _Items_.
- **Emission:** An _Observable_ _Emits_ _Items_ to its _subscribed Observer_ by calling the _Observer's onNext-method_.
- **Subscribe:** A particular _Observer_ instance can _subscribe_ to a particular _Observable_ instance by calling its _subscribe-method_.
- **Subscription:** A connection between a particular _Observer_ and a particular _Observable_ that begins with a call to _subscribe_.
- **Chain:** Chain creates a _new Observable_ that is chained to the _old Observable_. (**N.b.** This is called "lift" in Netflix's RxJava*.)
- **Operator:** A function that creates the _Observer_ used by _Chain_.

> *) Even if ::lift actually do "lift some function into the Observable", I think that _Chain_ is a better name.

# Secondary concepts

- **Scheduler:** Even though asynchronicity and _Schedulers_ are ubiquitous in RxJava, I wouldn't call them core concepts. Schedulers are factories that can create Workers. (**N.b.** I haven't implemented Workers yet, my _Schedulers_ act like RX-Workers.)
- **Worker:** A Worker is basically a with a job-queue to which one can submit tasks instead of calling them directly — for example the call to _onSubscribe_ for the SubscribeOnOperator.
- **Upstream:** Closer to the originating Observable.
- **Downstream:** Closer to the original subscriber.

# The Observable Contract

There is [a contract](http://reactivex.io/documentation/contract.html) that all Observables must obey.

- "An Observable may make zero or more OnNext notifications, and it may then follow those emission notifications by either an OnCompleted or an OnError notification — but not both."
- An Observable may notify the Observer from different threads, but not concurrently.
- It is the Observable's responsibility to establish a _happens-before-relationship_ between these calls.

# Cut corners

This implementation do not support:

- **Multiple Observers:**
- **Backpressure:**
- **Unsubscribing:**

# References

- [http://www.introtorx.com/]()
- [http://reactivex.io/]()
- [https://github.com/ReactiveX/RxJava/]()

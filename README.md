# frx4j — Folkol's Reactive Extensions for Java

_**frx4j**_ is intended  — for several reasons — to be a bare-bones implementation of [ReactiveX](http://reactivex.io). Partially because I am a lazy bum, but also in order to keep things simple enough for the core concepts of RX to be visible by reading the code or stepping with a debugger.

> **N.b.** Like most other projects on my GitHub, this hack is only intended for education and recreation. _If you want to use RX in a real Java app, use instead [Netflix's implementation](https://github.com/ReactiveX/RxJava) instead._

# Build

    $ gradle

# Core concepts

- **Observable stream:** An _Observable Stream_ is a push-based stream of items.
- **Observable:** An _Observable_ is the _producer_ of a stream of _Items_. (An instance of the class _Observable_.)
- **Observer:** An _Observer_ is the _consumer_ of a stream of _Items_. (An instance of the interface _Observer_.)
- **Emission:** An _Observable_ _Emits_ _Items_ to its _subscribed Observer_ by calling the _Observer's onNext-method_.
- **Subscribe:** A particular _Observer_ instance can _subscribe_ to a particular _Observable_ instance by calling its _subscribe-method_.
- **Subscription:** When a particular _Observer_ instance is connected to a particular _Observable_ instance in order to recieve _notifications_, the connection is called _a Subscription_.

To be implemented:

- **Lifting:** _Lifting_ is creating a _new Observable_ and chaining it together with the _old Observable_. When _subscribing_ to this _new Observable_, it will in turn _subscribe_ to the _old one_ and re-emit its items — transformed by a given _Operator_.
- **Operator:** A function that transforms _Items_.
- **Compose:**
- **Scheduler:** A _Scheduler_ decides which _Worker/Executor_ that will call a _Observer's onSubscribe_.
- **Worker/Executor:** A _Worker_ is calling an _Observable's_ _onSubscribe_ when a _Observer_ is starting a _subscription_.

# The Observable Contract

...

# Corners cut

This implementation do not support:

- **Multiple Observers:**
- **Backpressure:**
- **Unsubscribing:**

# References

- [http://www.introtorx.com/]()
- [http://reactivex.io/]()
- [https://github.com/ReactiveX/RxJava/]()

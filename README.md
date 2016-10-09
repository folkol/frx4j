# frx4j — Folkol's Reactive Extensions for Java

**frx4j** is intended — for several reasons — to be a bare-bones implementation of _a subset_ of [ReactiveX](http://reactivex.io). Partially because I am a lazy bum, but also because I want to demonstrate the core contepts of RX for those who prefer to read code over staring at marble diagrams.

> **N.b.** Like most other projects that I upload to GitHub, this hack is only intended for education and recreation. _If you want to use RX in a real Java app, use [Netflix's implementation](https://github.com/ReactiveX/RxJava) instead._

# Build

    $ gradle

# Core concepts

- **Observable Stream:** An _Observable Stream_ is a _push-based_ stream of items.
- **Observable:** An _Observable_ is the _producer_ of a stream of _Items_. (An instance of the class _Observable_.)
- **Observer:** An _Observer_ is the _consumer_ of a stream of _Items_. (An instance of the interface _Observer_.)
- **Emission:** An _Observable_ _Emits_ _Items_ to its _subscribed Observer_ by calling the _Observer's onNext-method_.
- **Subscribe:** A particular _Observer_ instance can _subscribe_ to a particular _Observable_ instance by calling its _subscribe-method_.
- **Subscription:** A connection between a particular _Observer_ and a particular _Observable_ that begins with a call to _subscribe_.

To be implemented:

- **Chain:** Chain creates a _new Observable_ that is chained to the _old Observable_. (**N.b.** This is called "lift" in Netflix's RxJava*.)
- **Operator:** A function that creates the _Observer_ used by _Chain_.
- **Compose:** A function that transforms the _Observable_ into a new _Observable_.
- **Scheduler:** A _Scheduler_ decides which _Worker/Executor_ that will call a _Observer's onSubscribe_.
- **Executor:** A _Worker_ is making the actual call an _Observable's_ _onSubscribe_ when a _Observer_ is starting a _subscription_.

> *) Even if ::lift actually do lift some function into the Observable, I think that ::chain is a better name.

# The Observable Contract

...

WIP: onXXX-calls for a particular subscription must not be concurrent, and must have a happens-before relationship even if called from separate threads.
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

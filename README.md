# Folkol's RX for Java

This is intended to be a bare-bone implementation of [ReactiveX](http://reactivex.io) — for several reasons. Partially because I am a lazy bum, but also in order to keep things simple enough for the core concepts of RX to be visible by reading the code or stepping with a debugger.

> **N.b.** Like most other projects on my GitHub, this hack is only intended for education and recreation. _If you want to use RX in a real Java app, use instead [Netflix's implementation](https://github.com/ReactiveX/RxJava) instead._

# Build

    $ gradle

# Core concepts

- **Observable:** An _Observable_ is a _producer_ of a stream of _Items_.
- **Observer:** An _Observer_ is a _consumer_ of a stream of _Items_.
- **Item:** Objects that are _emitted_ from the _Observable_ to the _Observer_ are called _Items_.
- **Emission:** When an _Observable_ has got hold of an _item_, it can _emit_ the _item_ to its _subscribed Observer_ by calling the _Observer's onNext-method_.
- **Subscription:** A subscription is when an _Observer_ is connected to an _Observable_ in order to recieve notifications.
- **Subscribe:** An _Observer_ can _subscribe_ to a given _Observable_ by calling its _subscribe method_.

To be implemented:

- **Lifting:** _Lifting_ is creating a _new Observable_ and chaining it together with the _old Observable_. When _subscribing_ to this _new Observable_, it will in turn _subscribe_ to the _old one_ and re-emit its items — transformed by a given _Operator_.
- **Operator:** A function that transforms _Items_.
- **Compose:**
- **Scheduler:** A _Scheduler_ decides which _Worker/Executor_ that will call a _Observer's onSubscribe_.
- **Worker/Executor:** A _Worker_ is calling an _Observable's_ _onSubscribe_ when a _Observer_ is starting a _subscription_.

# Corners cut

This implementation do not support:

- **Multiple Observers:**
- **Backpressure:**
- **Unsubscribing:**

# References

- [http://www.introtorx.com/]()
- [http://reactivex.io/]()
- [https://github.com/ReactiveX/RxJava/]()

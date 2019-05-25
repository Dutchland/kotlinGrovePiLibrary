package nl.dutchland.grove.lightsensor

import nl.dutchland.grove.utility.time.Period
import nl.dutchland.grove.utility.time.Millisecond

import kotlin.concurrent.fixedRateTimer

abstract class PollingSensor<T> {
    private var mostRecentValue: T = this.getStatus()

    init {
        fixedRateTimer("Polling timer", false, 100, 100)
        { mostRecentValue = getStatus()}
    }

    open fun subscribe(listener: (T) -> Unit, pollInterval: Period) {
        val intervalInMilliseconds : Long = pollInterval.valueIn(Millisecond).toLong()
        fixedRateTimer("Polling timer", false, intervalInMilliseconds, intervalInMilliseconds) { callListener(listener) }
        callListener(listener)
    }

    private fun callListener(listener: (T) -> Unit) {
        listener.invoke(this.mostRecentValue)
    }

     abstract fun getStatus(): T

}

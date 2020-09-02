package nl.dutchland.grove

import nl.dutchland.grove.utility.baseunits.time.Millisecond
import nl.dutchland.grove.utility.baseunits.time.Period
import java.util.*
import kotlin.concurrent.schedule

class PollingTimer<T>(private val interval: Period, private val listener: (T) -> Unit) {
    private var acceptsNewValue = true

    @Synchronized
    fun onNewValue(value: T) {
        if (acceptsNewValue) {
            acceptsNewValue = false
            listener.invoke(value)
            Timer().schedule(interval.valueIn(Millisecond).toLong())
            { acceptsNewValue = true }
        }
    }
}
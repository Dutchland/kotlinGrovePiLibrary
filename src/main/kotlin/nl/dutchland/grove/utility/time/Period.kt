package nl.dutchland.grove.utility.time

import nl.dutchland.grove.utility.assertLargerThanZero

data class Period internal constructor(private val seconds: Double) {
    init {
        seconds.assertLargerThanZero { throw InvalidIntervalException("Period cannot be negative") }
    }

    companion object {
        fun of(value: Double, unit: TimeUnit): Period {
            return Period(unit.toSeconds(value))
        }
    }

    fun valueIn(unit: TimeUnit): Double {
        return unit.fromSeconds(this.seconds)
    }

    interface TimeUnit {
        fun toSeconds(value: Double): Double
        fun fromSeconds(value: Double): Double
        val name: String
    }
}

class InvalidIntervalException(message: String) : IllegalArgumentException(message)

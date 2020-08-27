package nl.dutchland.grove.utility.time

import nl.dutchland.grove.utility.assertLargerThanZero

data class Period internal constructor(private val seconds: Double) : Comparable<Period> {
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

    abstract class TimeUnit {
        abstract fun toSeconds(value: Double): Double
        abstract fun fromSeconds(valueInSeconds: Double): Double
        abstract val longName: String
        abstract val shortName: String

        override fun toString(): String = longName
    }

    override fun compareTo(other: Period): Int {
        return this.seconds.compareTo(other.seconds)
    }
}

class InvalidIntervalException(message: String) : IllegalArgumentException(message)

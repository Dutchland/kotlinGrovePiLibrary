package nl.dutchland.grove.utility.baseunits.time

import nl.dutchland.grove.utility.StandardUnitPrefix
import nl.dutchland.grove.utility.assertLargerThanZero
import nl.dutchland.grove.utility.baseunits.length.Length
import nl.dutchland.grove.utility.derivedunits.kinematic.pace.Pace
import nl.dutchland.grove.utility.derivedunits.kinematic.speed.Speed

typealias Period = Time

data class Time internal constructor(private val seconds: Double) : Comparable<Period> {
    init {
        seconds.assertLargerThanZero { throw InvalidIntervalException("Period cannot be negative") }
    }

    companion object {
        fun of(value: Double, unit: Unit): Period {
            return Period(unit.toSeconds(value))
        }
    }

    fun valueIn(unit: Unit): Double {
        return unit.fromSeconds(this.seconds)
    }

    operator fun plus(other: Time) : Time {
        return Time.of(seconds + other.seconds, Second)
    }

    override fun compareTo(other: Time): Int {
        return this.seconds.compareTo(other.seconds)
    }

    operator fun minus(other: Time): Time {
        return Time.of(seconds - other.seconds, Second)
    }

    operator fun div(pace: Pace): Length {
        TODO("Not yet implemented")
    }

    interface Unit : Comparable<Unit> {
        fun toSeconds(value: Double): Double
        fun fromSeconds(valueInSeconds: Double): Double
        val longName: String

        val shortName: String

        override fun toString(): String
        override fun compareTo(other: Unit): Int {
            return this.toSeconds(1.0).compareTo(other.toSeconds(1.0))
        }

        operator fun div(lengthUnit: Length.Unit): Pace.Unit {
            TODO("Not yet implemented")
        }
    }
}

class InvalidIntervalException(message: String) : IllegalArgumentException(message)

operator fun StandardUnitPrefix.times(timeUnit: Time.Unit): Time.Unit {
    return FactorizedUnit(
            longName = this.longName + timeUnit.longName.toLowerCase(),
            shortName = this.symbol + timeUnit.shortName,
            toSecondFactor = this.factor * timeUnit.toSeconds(1.0))
}

operator fun Number.times(timeUnit: Time.Unit): Time {
    return Time.of(this.toDouble(), timeUnit)
}

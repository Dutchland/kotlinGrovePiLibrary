package nl.dutchland.grove.utility.baseunits.length

import nl.dutchland.grove.utility.UnitPrefix
import nl.dutchland.grove.utility.baseunits.time.Period
import nl.dutchland.grove.utility.baseunits.time.Second
import nl.dutchland.grove.utility.baseunits.time.Time
import nl.dutchland.grove.utility.derivedunits.mechanical.area.Area
import nl.dutchland.grove.utility.derivedunits.mechanical.area.m2
import nl.dutchland.grove.utility.derivedunits.kinematic.speed.MeterPerSecond
import nl.dutchland.grove.utility.derivedunits.kinematic.speed.Speed

typealias Distance = Length
typealias LengthProvider = () -> Double

data class Length internal constructor(private val value: LengthProvider, private val unit: Unit) : Comparable<Length> {
    private val lengthInMeters: Double by lazy {
        this.unit.toMeter(this.value.invoke())
    }

    companion object {
        fun of(value: Double, unit: Unit): Length =
                Length({ value }, unit)

        fun of(value: LengthProvider, unit: Unit): Length =
                Length(value, unit)
    }

    fun valueIn(unit: Unit): Double {
        return unit.fromMeter(lengthInMeters)
    }

    override operator fun compareTo(other: Length): Int {
        return this.lengthInMeters.compareTo(other.lengthInMeters)
    }

    operator fun plus(other: Length): Length {
        if (this.unit == other.unit) {
            return Length({ this.value.invoke() + other.value.invoke() }, this.unit)
        }
        return Length({ this.lengthInMeters + other.lengthInMeters }, Meter)
    }

    operator fun minus(other: Length): Length {
        if (this.unit == other.unit) {
            return Length({ this.value.invoke() - other.value.invoke() }, this.unit)
        }
        return Length({ this.lengthInMeters - other.lengthInMeters }, Meter)
    }

    operator fun div(divider: Double): Length {
        return Length({ this.lengthInMeters / divider }, Meter)
    }

    operator fun div(divider: Length): Double {
        if (this.unit == divider.unit) {
            return this.value.invoke() / divider.value.invoke()
        }
        return this.lengthInMeters / divider.lengthInMeters
    }

    operator fun div(divider: Period): Speed {
        return Speed.of(this.lengthInMeters / divider.valueIn(Second), MeterPerSecond)
    }

    operator fun div(speed: Speed): Time {
        return Period.of(this.lengthInMeters / speed.valueIn(MeterPerSecond), Second)
    }

    operator fun div(period: Time.Unit): Speed {
        return Speed.of(this.lengthInMeters, Meter / period)
    }

    operator fun times(factor: Double): Length {
        return Length({ this.value.invoke() * factor }, unit)
    }

    operator fun times(factor: Length): Area {
        return Area.of(this.lengthInMeters * factor.lengthInMeters, m2)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Length
        if (lengthInMeters != other.lengthInMeters) return false
        return true
    }

    override fun hashCode(): Int {
        return lengthInMeters.hashCode()
    }

    override fun toString(): String {
        return "$lengthInMeters $Meter"
    }

    interface Unit {
        fun fromMeter(valueInMeter: Double): Double
        fun toMeter(value: Double): Double
        val shortName: String
        val longName: String

        override fun toString(): String

        operator fun div(timeUnit: Time.Unit): Speed.Unit {
            return Speed.ParameterizedUnit(this, timeUnit)
        }

        companion object {
            fun ofParameterized(longName: String, shortName: String, toMeterFactor: Double): Unit {
                return ParameterizedUnit(longName, shortName, toMeterFactor)
            }
        }
    }

    internal class ParameterizedUnit(
            override val longName: String,
            override val shortName: String,
            private val toMeterFactor: Double) : Unit {

        override fun fromMeter(valueInMeter: Double): Double = valueInMeter / toMeterFactor
        override fun toMeter(value: Double): Double = value * toMeterFactor

        override fun toString(): String {
            return longName
        }
    }
}

fun Iterable<Length>.sum(): Length {
    return Length.of(this.map { l -> l.valueIn(Meter) }.sum(), Meter)
}

operator fun Double.times(lengthUnit: Length.Unit) : Length {
    return Length.of(this, lengthUnit)
}

operator fun UnitPrefix.times(lengthUnit: Length.Unit) : Length.Unit {
    return Length.ParameterizedUnit(
            longName = this.longName + lengthUnit.longName.toLowerCase(),
            shortName = this.symbol + lengthUnit.shortName,
            toMeterFactor = this.factor * lengthUnit.toMeter(1.0))
}
package nl.dutchland.grove.utility.baseunits.length

import nl.dutchland.grove.utility.baseunits.time.Period
import nl.dutchland.grove.utility.baseunits.time.Second
import nl.dutchland.grove.utility.derivedunits.area.Area
import nl.dutchland.grove.utility.derivedunits.area.m2
import nl.dutchland.grove.utility.derivedunits.speed.MeterPerSecond
import nl.dutchland.grove.utility.derivedunits.speed.Speed

typealias Distance = Length

typealias LengthProvider = () -> Double
private typealias LengthInMetersProvider = () -> Double

data class Length internal constructor(private val lengthInMetersProvider: LengthInMetersProvider) : Comparable<Length> {
    private val lengthInMeters: Double by lazy {
        lengthInMetersProvider.invoke()
    }

    companion object {
        fun of(value: Double, unit: Unit): Length {
            return Length { unit.toMeter(value) }
        }

        fun of(value: LengthProvider, unit: Unit): Length {
            return Length { unit.toMeter(value.invoke()) }
        }
    }

    fun valueIn(unit: Unit): Double {
        return unit.fromMeter(lengthInMeters)
    }

    override operator fun compareTo(other: Length): Int {
        return this.lengthInMeters.compareTo(other.lengthInMeters)
    }

    operator fun plus(other: Length): Length {
        return Length { this.lengthInMeters + other.lengthInMeters }
    }

    operator fun minus(other: Length): Length {
        return Length { this.lengthInMeters - other.lengthInMeters }
    }

    operator fun div(divider: Double): Length {
        return Length { this.lengthInMeters / divider }
    }

    operator fun div(divider: Length): Double {
        return this.lengthInMeters / divider.lengthInMeters
    }

    operator fun div(divider: Period): Speed {
        return Speed.of(this.lengthInMeters / divider.valueIn(Second), MeterPerSecond)
    }

    operator fun times(factor: Double): Length {
        return Length { this.lengthInMeters * factor }
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
        return "$lengthInMeters$Meter"
    }

    operator fun div(speed: Speed): Period {
        return Period.of(this.lengthInMeters / speed.valueIn(MeterPerSecond), Second)
    }

    operator fun div(period: Period.TimeUnit): Speed {
        return Speed.of(this.lengthInMeters, Meter/period)
    }

    interface Unit {
        fun fromMeter(valueInMeter: Double): Double
        fun toMeter(value: Double): Double
        val shortName: String
        val longName: String

        override fun toString(): String

        operator fun div(divider: Period.TimeUnit): Speed.Unit {
            return Speed.Unit(this, divider)
        }

        companion object {
            fun ofParameterized(longName: String, shortName: String, toMeterFactor: Double): Unit {
                return ParameterizedUnit(longName, shortName, toMeterFactor)
            }
        }
    }

    private class ParameterizedUnit(
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
    val sumInMeterProvider = {
        this.map { l -> l.valueIn(Meter) }
                .sum()
    }
    return Length.of(sumInMeterProvider, Meter)
}
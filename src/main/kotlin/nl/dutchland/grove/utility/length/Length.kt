package nl.dutchland.grove.utility.length

import nl.dutchland.grove.utility.mass.Gram
import nl.dutchland.grove.utility.mass.Mass

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

    operator fun times(factor: Double): Length {
        return Length { this.lengthInMeters * factor }
    }

    abstract class Unit {
        abstract fun fromMeter(valueInMeter: Double): Double
        abstract fun toMeter(value: Double): Double
        abstract val shortName: String
        abstract val longName: String

        override fun toString(): String {
            return longName
        }
    }
}

fun Iterable<Length>.sum(): Length {
    return this.fold(Length.of(0.0, Meter)) { w1, w2 -> w1 + w2 }
}
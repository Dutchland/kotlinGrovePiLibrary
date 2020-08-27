package nl.dutchland.grove.utility.volume

import nl.dutchland.grove.utility.length.Length
import nl.dutchland.grove.utility.length.Meter

typealias AreaInM2Provider = () -> Double

data class Volume internal constructor(private val areaInM2Provider: AreaInM2Provider) : Comparable<Volume> {
    private val areaInM2: Double by lazy {
        areaInM2Provider.invoke()
    }

    companion object {
        fun of(value: Double, unit: Unit): Volume {
            return Volume { unit.toM2(value) }
        }
    }

    fun valueIn(unit: Unit): Double {
        return unit.fromM2(areaInM2)
    }

    override operator fun compareTo(other: Volume): Int {
        return this.areaInM2.compareTo(other.areaInM2)
    }

    operator fun plus(other: Volume): Volume {
        return Volume { this.areaInM2 + other.areaInM2 }
    }

    operator fun minus(other: Volume): Volume {
        return Volume { this.areaInM2 - other.areaInM2 }
    }

    operator fun div(divider: Double): Volume {
        return Volume { this.areaInM2 / divider }
    }

    operator fun times(factor: Double): Volume {
        return Volume { this.areaInM2 * factor }
    }

    abstract class Unit(private val lengthUnit: Length.Unit) {
//        companion object {
//            fun squared(lengthUnit: Length.Unit) : Unit {
//                return Unit(lengthUnit)
//            }
//        }


        fun fromM2(valueInM2: Double): Double {
            return Length.of(valueInM2, Meter).valueIn(lengthUnit)
        }

        fun toM2(value: Double): Double {
            return lengthUnit.toMeter(value)
        }

        val shortName: String = "${lengthUnit.shortName}^2"
        val longName: String = "${lengthUnit.longName} squared"

        override fun toString(): String {
            return longName
        }
    }
}
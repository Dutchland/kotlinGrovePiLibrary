package nl.dutchland.grove.utility.derivedunits.area

import nl.dutchland.grove.utility.baseunits.length.Length
import nl.dutchland.grove.utility.baseunits.length.Meter
import nl.dutchland.grove.utility.derivedunits.volume.Volume
import nl.dutchland.grove.utility.derivedunits.volume.m3

typealias AreaInM2Provider = () -> Double

data class Area internal constructor(private val areaInM2Provider: AreaInM2Provider) : Comparable<Area> {
    private val areaInM2: Double by lazy {
        areaInM2Provider.invoke()
    }

    companion object {
        fun of(value: Double, unit: Unit): Area {
            return Area { unit.toM2(value) }
        }
    }

    fun valueIn(unit: Unit): Double {
        return unit.fromM2(areaInM2)
    }

    override operator fun compareTo(other: Area): Int {
        return this.areaInM2.compareTo(other.areaInM2)
    }

    operator fun plus(other: Area): Area {
        return Area { this.areaInM2 + other.areaInM2 }
    }

    operator fun minus(other: Area): Area {
        return Area { this.areaInM2 - other.areaInM2 }
    }

    operator fun div(divider: Double): Area {
        return Area { this.areaInM2 / divider }
    }

    operator fun div(divider: Length): Length {
        return Length.of(this.areaInM2 / divider.valueIn(Meter), Meter)
    }

    operator fun times(factor: Double): Area {
        return Area { this.areaInM2 * factor }
    }

    operator fun times(factor: Length): Volume {
        return Volume.of(this.areaInM2 * factor.valueIn(Meter), m3)
    }

    interface Unit {
        fun fromM2(valueInM2: Double): Double
        fun toM2(value: Double): Double

        val shortName: String
        val longName: String

        override fun toString(): String

        companion object {
            fun ofSquared(lengthUnit: Length.Unit): Unit {
                return LengthDerivedUnit(lengthUnit)
            }
        }
    }

    private class LengthDerivedUnit(private val lengthUnit: Length.Unit) : Unit {
        override fun fromM2(valueInM2: Double): Double =
                Length.of(valueInM2, Meter).valueIn(lengthUnit)

        override fun toM2(value: Double): Double =
                lengthUnit.toMeter(value)

        override val shortName: String = "${lengthUnit.shortName}^2"
        override val longName: String = "${lengthUnit.longName} squared"

        override fun toString(): String = longName
    }
}

fun Iterable<Area>.sum(): Area {
    return this.fold(Area.of(0.0, m2)) { w1, w2 -> w1 + w2 }
}
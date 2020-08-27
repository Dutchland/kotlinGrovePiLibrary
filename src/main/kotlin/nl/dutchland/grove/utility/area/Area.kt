package nl.dutchland.grove.utility.area

import nl.dutchland.grove.utility.length.Length
import nl.dutchland.grove.utility.length.Meter

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

    operator fun times(factor: Double): Area {
        return Area { this.areaInM2 * factor }
    }

    abstract class Unit {
        abstract fun fromM2(valueInM2: Double): Double
        abstract fun toM2(value: Double): Double

        abstract val shortName: String
        abstract val longName: String

        override fun toString(): String {
            return longName
        }

        companion object {
            fun ofSquared(lengthUnit: Length.Unit) : Unit {
                return LengthDerivedUnit(lengthUnit);
            }
        }
    }

    open class LengthDerivedUnit(private val lengthUnit: Length.Unit) : Unit() {
        override fun fromM2(valueInM2: Double): Double {
            return Length.of(valueInM2, Meter).valueIn(lengthUnit)
        }

        override fun toM2(value: Double): Double {
            return lengthUnit.toMeter(value)
        }

        override val shortName: String = "${lengthUnit.shortName}^2"
        override val longName: String = "${lengthUnit.longName} squared"
    }
}
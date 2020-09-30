package nl.dutchland.grove.utility.derivedunits.pressure

import nl.dutchland.grove.utility.derivedunits.mechanical.area.Area
import nl.dutchland.grove.utility.derivedunits.force.Force

typealias PressureInPascalProvider = () -> Double

data class Pressure internal constructor(private val pressureInPascalProvider: PressureInPascalProvider) : Comparable<Pressure> {
    private val pressureInPascal: Double by lazy {
        pressureInPascalProvider.invoke()
    }

    companion object {
        fun of(value: Double, unit: Unit): Pressure {
            return Pressure { unit.toPascal(value) }
        }
    }

    fun valueIn(unit: Unit): Double {
        return unit.fromPascal(pressureInPascal)
    }

    override operator fun compareTo(other: Pressure): Int {
        return this.pressureInPascal.compareTo(other.pressureInPascal)
    }

    operator fun plus(other: Pressure): Pressure {
        return Pressure { this.pressureInPascal + other.pressureInPascal }
    }

    operator fun minus(other: Pressure): Pressure {
        return Pressure { this.pressureInPascal - other.pressureInPascal }
    }

    operator fun div(divider: Double): Pressure {
        return Pressure { this.pressureInPascal / divider }
    }

    operator fun times(factor: Double): Pressure {
        return Pressure { this.pressureInPascal * factor }
    }

    interface Unit {
        val shortName: String
        val longName: String

        fun fromPascal(valueInPascal: Double): Double
        fun toPascal(value: Double): Double

        override fun toString(): String

        companion object {
            fun ofParameterized(forceUnit: Force.Unit, areaUnit: Area.Unit): Unit {
                return ParameterizedUnit(forceUnit, areaUnit)
            }
        }
    }

    private class ParameterizedUnit(forceUnit: Force.Unit, areaUnit: Area.Unit) : Unit {
        override val shortName: String = "(${forceUnit.shortName} / ${areaUnit.shortName}"
        override val longName: String = "(${forceUnit.longName} divided by ${areaUnit.longName}"

        private val factor = forceUnit.toNewton(1.0) / areaUnit.toM2(1.0)

        override fun fromPascal(valueInPascal: Double): Double = valueInPascal * factor
        override fun toPascal(value: Double): Double = value * factor

        override fun toString(): String = longName
    }
}
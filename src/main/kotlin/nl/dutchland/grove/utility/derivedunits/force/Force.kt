package nl.dutchland.grove.utility.derivedunits.force

import nl.dutchland.grove.utility.baseunits.length.Length
import nl.dutchland.grove.utility.derivedunits.area.Area
import nl.dutchland.grove.utility.baseunits.mass.Mass
import nl.dutchland.grove.utility.baseunits.time.Period
import nl.dutchland.grove.utility.derivedunits.acceleration.Acceleration

typealias EnergyInJouleProvider = () -> Double

data class Force internal constructor(private val energyInJouleProvider: EnergyInJouleProvider) : Comparable<Force> {
    private val forceInNewton: Double by lazy {
        energyInJouleProvider.invoke()
    }

    companion object {
        fun of(value: Double, unit: Unit): Force {
            return Force { unit.toNewton(value) }
        }
    }

    fun valueIn(unit: Unit): Double {
        return unit.fromNewton(forceInNewton)
    }

    override operator fun compareTo(other: Force): Int {
        return this.forceInNewton.compareTo(other.forceInNewton)
    }

    operator fun plus(other: Force): Force {
        return Force { this.forceInNewton + other.forceInNewton }
    }

    operator fun minus(other: Force): Force {
        return Force { this.forceInNewton - other.forceInNewton }
    }

    operator fun div(divider: Double): Force {
        return Force { this.forceInNewton / divider }
    }

    operator fun times(factor: Double): Force {
        return Force { this.forceInNewton * factor }
    }

    interface Unit {
        val shortName: String
        val longName: String

        fun fromNewton(valueInNewton: Double): Double
        fun toNewton(value: Double): Double

        override fun toString(): String

        companion object {
            fun ofParameterized(massUnit: Mass.Unit,accelerationUnit: Acceleration.Unit): Unit {
                return ParameterizedUnit(massUnit, accelerationUnit)
            }
        }
    }

    private class ParameterizedUnit(
            private val massUnit: Mass.Unit,
            private val accelerationUnit: Acceleration.Unit) : Unit {

        override val shortName: String = "(${massUnit.shortName} * ${accelerationUnit.shortName}"
        override val longName: String = "(${massUnit.longName} times ${accelerationUnit.longName}"

        override fun fromNewton(valueInNewton: Double): Double {
            TODO()
        }

        override fun toNewton(value: Double): Double {
            TODO()
        }

        override fun toString(): String = longName
    }
}
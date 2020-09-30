package nl.dutchland.grove.utility.derivedunits.force

import nl.dutchland.grove.utility.baseunits.length.Length
import nl.dutchland.grove.utility.baseunits.length.m
import nl.dutchland.grove.utility.baseunits.mass.Mass
import nl.dutchland.grove.utility.derivedunits.kinematic.acceleration.Acceleration
import nl.dutchland.grove.utility.derivedunits.mechanical.area.Area
import nl.dutchland.grove.utility.derivedunits.mechanical.area.m2
import nl.dutchland.grove.utility.derivedunits.energy.EnergyAmount
import nl.dutchland.grove.utility.derivedunits.pressure.Pressure

typealias ForceInNewtonProvider = () -> Double

data class Force internal constructor(private val forceInNewtonProvider: ForceInNewtonProvider) : Comparable<Force> {
    private val forceInNewton: Double by lazy {
        forceInNewtonProvider.invoke()
    }

    companion object {
        fun of(value: Double, unit: Unit): Pressure {
            return Pressure { unit.toNewton(value) }
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

    operator fun div(area: Area): Pressure {
        return Pressure.of(this.forceInNewton/area.valueIn(m2), Newton/m2)
    }

    operator fun times(factor: Double): Force {
        return Force { this.forceInNewton * factor }
    }

    operator fun times(factor: Length): EnergyAmount {
        return EnergyAmount.of(this.forceInNewton * factor.valueIn(m), N * m)
    }

    interface Unit {
        val shortName: String
        val longName: String

        fun fromNewton(valueInNewton: Double): Double
        fun toNewton(value: Double): Double

        override fun toString(): String

        operator fun div(areaUnit: Area.Unit) : Pressure.Unit {
            return Pressure.Unit.ofParameterized(this, areaUnit)
        }

        operator fun times(lengthUnit: Length.Unit) : EnergyAmount.Unit {
            return EnergyAmount.Unit.ofParameterized(this, lengthUnit)
        }

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
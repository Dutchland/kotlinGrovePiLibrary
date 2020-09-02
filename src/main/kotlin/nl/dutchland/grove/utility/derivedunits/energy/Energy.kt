package nl.dutchland.grove.utility.derivedunits.energy

import nl.dutchland.grove.utility.baseunits.length.Length
import nl.dutchland.grove.utility.derivedunits.area.Area
import nl.dutchland.grove.utility.baseunits.mass.Mass
import nl.dutchland.grove.utility.baseunits.time.Period
import nl.dutchland.grove.utility.derivedunits.force.Force

typealias EnergyInJouleProvider = () -> Double

data class EnergyAmount internal constructor(private val energyInJouleProvider: EnergyInJouleProvider) : Comparable<EnergyAmount> {
    private val energyInJoule: Double by lazy {
        energyInJouleProvider.invoke()
    }

    companion object {
        fun of(value: Double, unit: Unit): EnergyAmount {
            return EnergyAmount { unit.toJoule(value) }
        }
    }

    fun valueIn(unit: Unit): Double {
        return unit.fromJoule(energyInJoule)
    }

    override operator fun compareTo(other: EnergyAmount): Int {
        return this.energyInJoule.compareTo(other.energyInJoule)
    }

    operator fun plus(other: EnergyAmount): EnergyAmount {
        return EnergyAmount { this.energyInJoule + other.energyInJoule }
    }

    operator fun minus(other: EnergyAmount): EnergyAmount {
        return EnergyAmount { this.energyInJoule - other.energyInJoule }
    }

    operator fun div(divider: Double): EnergyAmount {
        return EnergyAmount { this.energyInJoule / divider }
    }

//    operator fun div(divider: Period.TimeUnit): Power {
//        return EnergyAmount { this.energyInJoule / divider }
//    }

    operator fun times(factor: Double): EnergyAmount {
        return EnergyAmount { this.energyInJoule * factor }
    }

    interface Unit {
        val shortName: String
        val longName: String

        fun fromJoule(valueInJoule: Double): Double
        fun toJoule(value: Double): Double

        override fun toString(): String

        companion object {
            fun ofParameterized(areaUnit: Area.Unit, massUnit: Mass.Unit, timeUnit: Period.TimeUnit): Unit {
                return ParameterizedUnit(areaUnit, massUnit, timeUnit)
            }

//            fun ofParameterized(forceUnit: Force.Unit, lengthUnit: Length.Unit): Unit {
//                return ParameterizedUnit(areaUnit, massUnit, timeUnit)
//            }
        }
    }

    private class ParameterizedUnit(private val factor: Double) : Unit {
        constructor(areaUnit: Area.Unit,
                    massUnit: Mass.Unit,
                    timeUnit: Period.TimeUnit) :
                this((areaUnit.toM2(1.0) * massUnit.toKiloGrams(1.0)) / timeUnit.toSeconds(1.0))

        override fun fromJoule(valueInJoule: Double): Double {
            TODO()
        }

        override fun toJoule(value: Double): Double {
            TODO()
        }

        override val shortName: String = "" //(${areaUnit.shortName} * ${massUnit.shortName}) / ${timeUnit.shortName}^2"
        override val longName: String = ""

        override fun toString(): String = longName
    }
}
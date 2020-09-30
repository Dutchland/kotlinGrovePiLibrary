package nl.dutchland.grove.utility.derivedunits.energy

import nl.dutchland.grove.utility.baseunits.length.Length
import nl.dutchland.grove.utility.derivedunits.mechanical.area.Area
import nl.dutchland.grove.utility.baseunits.mass.Mass
import nl.dutchland.grove.utility.baseunits.temperature.Temperature
import nl.dutchland.grove.utility.baseunits.time.Second
import nl.dutchland.grove.utility.baseunits.time.Time
import nl.dutchland.grove.utility.derivedunits.force.Force
import nl.dutchland.grove.utility.derivedunits.heatcapacity.HeatCapacity
import nl.dutchland.grove.utility.derivedunits.power.Power
import nl.dutchland.grove.utility.derivedunits.specificheatcapacity.SpecificHeatCapacity

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

    operator fun div(divider: Time): Power {
        return Power { this.energyInJoule / divider.valueIn(Second) }
    }


    operator fun times(factor: Double): EnergyAmount {
        return EnergyAmount { this.energyInJoule * factor }
    }

    operator fun div(heatCapacity: HeatCapacity): Temperature {
        TODO("Not yet implemented")
    }

    interface Unit {
        val shortName: String
        val longName: String

        fun fromJoule(valueInJoule: Double): Double
        fun toJoule(value: Double): Double

        override fun toString(): String

        operator fun div(timeUnit: Time.Unit): Power.Unit {
            return Power.Unit.ofParameterized(this, timeUnit)
        }

        operator fun div(temperatureUnit: Temperature.Scale) : HeatCapacity.Unit {
            TODO()
        }


        companion object {
            fun ofParameterized(areaUnit: Area.Unit, massUnit: Mass.Unit, timeUnit: Time.Unit): Unit {
                val factor = (areaUnit.toM2(1.0) * massUnit.toKiloGrams(1.0)) / timeUnit.toSeconds(1.0)
                val shortName = "(${areaUnit.shortName} * ${massUnit.shortName}) / ${timeUnit.shortName}^2"
                return ParameterizedUnit(factor, shortName, "")
            }

            fun ofParameterized(forceUnit: Force.Unit, lengthUnit: Length.Unit): Unit {
                val factor = forceUnit.toNewton(1.0) * lengthUnit.toMeter(1.0)
                val shortName = "${forceUnit.shortName} * ${lengthUnit.shortName}"
                return ParameterizedUnit(factor, shortName, "")
            }

            fun ofFactorized(toJouleFactor: Double, shortName: String, longName: String): Unit {
                return ParameterizedUnit(toJouleFactor, shortName, longName)
            }
        }
    }

    private class ParameterizedUnit(private val factor: Double,
                                    override val shortName: String,
                                    override val longName: String) : Unit {
        override fun fromJoule(valueInJoule: Double): Double = valueInJoule / factor
        override fun toJoule(value: Double): Double = value * factor

        override fun toString(): String = longName
    }
}
package nl.dutchland.grove.utility.derivedunits.massdensity

import nl.dutchland.grove.utility.baseunits.mass.Kilogram
import nl.dutchland.grove.utility.baseunits.mass.Mass
import nl.dutchland.grove.utility.baseunits.mass.kg
import nl.dutchland.grove.utility.derivedunits.volume.Volume
import nl.dutchland.grove.utility.derivedunits.volume.m3

private typealias MassInKiloGramPerCubicMeterProvider = () -> Double

data class MassDensity internal constructor(private val massInKiloGramPerCubicMeterProvider: MassInKiloGramPerCubicMeterProvider) : Comparable<MassDensity> {
    private val massInKiloGramPerCubicMeter: Double by lazy {
        massInKiloGramPerCubicMeterProvider.invoke()
    }

    companion object {
        fun of(value: Double, unit: MassDensity.Unit): MassDensity {
            return MassDensity { unit.toKilogramPerMeterQubed(value) }
        }
    }

    fun valueIn(unit: Unit): Double {
        return unit.fromKilogramPerMeterQubed(massInKiloGramPerCubicMeter)
    }

    override operator fun compareTo(other: MassDensity): Int {
        return this.massInKiloGramPerCubicMeter.compareTo(other.massInKiloGramPerCubicMeter)
    }

    operator fun times(volume: Volume): Mass {
        return Mass.of(this.massInKiloGramPerCubicMeter * volume.valueIn(m3), Kilogram)
    }

    interface Unit {
        val shortName: String
        val longName: String

        fun fromKilogramPerMeterQubed(valueInKilogramPerMeterQubed: Double): Double
        fun toKilogramPerMeterQubed(value: Double): Double

        override fun toString(): String

        companion object {
            fun parameterized(massUnit: Mass.Unit, volumeUnit: Volume.Unit): Unit {
                return ParameterizedUnit(massUnit, volumeUnit)
            }
        }
    }

    private class ParameterizedUnit(private val massUnit: Mass.Unit, private val volumeUnit: Volume.Unit) : Unit {
        override val shortName: String = "${massUnit.shortName} / ${volumeUnit.shortName}"
        override val longName: String = "${massUnit.longName} per ${volumeUnit.longName}"

        override fun fromKilogramPerMeterQubed(valueInKilogramPerMeterQubed: Double): Double {
            return massUnit.fromKilograms(valueInKilogramPerMeterQubed) / volumeUnit.fromM3(1.0)
        }

        override fun toKilogramPerMeterQubed(value: Double): Double {
            return massUnit.toKiloGrams(value) / volumeUnit.toM3(1.0)
        }

        override fun toString(): String = longName
    }
}

operator fun Mass.Unit.div(unit: Volume.Unit): MassDensity.Unit {
    return MassDensity.Unit.parameterized(this, unit)
}

operator fun Mass.div(volumeUnit: Volume.Unit): MassDensity {
    return MassDensity.of(this.valueIn(kg), kg/volumeUnit)
}
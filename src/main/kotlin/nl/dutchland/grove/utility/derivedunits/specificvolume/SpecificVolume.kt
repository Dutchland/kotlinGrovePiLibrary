package nl.dutchland.grove.utility.derivedunits.specificvolume

import nl.dutchland.grove.utility.baseunits.mass.Mass
import nl.dutchland.grove.utility.baseunits.mass.kg
import nl.dutchland.grove.utility.derivedunits.mechanical.massdensity.MassDensity
import nl.dutchland.grove.utility.derivedunits.mechanical.volume.Volume
import nl.dutchland.grove.utility.derivedunits.mechanical.volume.m3

private typealias CubicMeterPerMassInKiloGramProvider = () -> Double

data class SpecificVolume internal constructor(private val cubicMeterPerMassInKiloGramProvider: CubicMeterPerMassInKiloGramProvider) : Comparable<SpecificVolume> {
    private val cubicMeterPerMassInKiloGram: Double by lazy {
        cubicMeterPerMassInKiloGramProvider.invoke()
    }

    companion object {
        fun of(value: Double, unit: SpecificVolume.Unit): SpecificVolume {
            return SpecificVolume { unit.toMeterQubedPerKilogram(value) }
        }
    }

    fun valueIn(unit: Unit): Double {
        return unit.fromMeterQubedPerKilogram(cubicMeterPerMassInKiloGram)
    }

    override operator fun compareTo(other: SpecificVolume): Int {
        return this.cubicMeterPerMassInKiloGram.compareTo(other.cubicMeterPerMassInKiloGram)
    }

    operator fun times(mass: Mass): Volume {
        return Volume.of(this.cubicMeterPerMassInKiloGram * mass.valueIn(kg), m3)
    }

    operator fun times(factor: Double): SpecificVolume {
        TODO("Not yet implemented")
    }

    fun toMassDensity() : MassDensity {
        return MassDensity { 1.0 / this.cubicMeterPerMassInKiloGram }
    }

    interface Unit {
        val shortName: String
        val longName: String

        fun fromMeterQubedPerKilogram(valueInMeterQubedPerKilogram: Double): Double
        fun toMeterQubedPerKilogram(value: Double): Double

        override fun toString(): String

        companion object {
            fun parameterized(volumeUnit: Volume.Unit, massUnit: Mass.Unit): Unit {
                return ParameterizedUnit(volumeUnit, massUnit)
            }
        }
    }

    private class ParameterizedUnit(private val volumeUnit: Volume.Unit, private val massUnit: Mass.Unit) : Unit {
        override val shortName: String = "${volumeUnit.shortName} / ${massUnit.shortName}"
        override val longName: String = "${volumeUnit.longName} per ${massUnit.longName}"

        override fun fromMeterQubedPerKilogram(valueInMeterQubedPerKilogram: Double): Double {
            return massUnit.fromKilograms(valueInMeterQubedPerKilogram) / volumeUnit.fromM3(1.0)
        }

        override fun toMeterQubedPerKilogram(value: Double): Double {
            return massUnit.toKiloGrams(value) / volumeUnit.toM3(1.0)
        }

        override fun toString(): String = longName
    }
}
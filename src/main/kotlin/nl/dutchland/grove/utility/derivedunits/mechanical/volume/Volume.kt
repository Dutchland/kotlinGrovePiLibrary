package nl.dutchland.grove.utility.derivedunits.mechanical.volume

import nl.dutchland.grove.utility.baseunits.length.Length
import nl.dutchland.grove.utility.baseunits.mass.Mass
import nl.dutchland.grove.utility.baseunits.mass.kg
import nl.dutchland.grove.utility.baseunits.time.Time
import nl.dutchland.grove.utility.derivedunits.kinematic.volumetricflow.VolumetricFlow
import nl.dutchland.grove.utility.derivedunits.mechanical.massdensity.MassDensity
import nl.dutchland.grove.utility.derivedunits.specificvolume.SpecificVolume
import kotlin.math.pow

typealias VolumeInM3Provider = () -> Double

data class Volume internal constructor(private val volumeInM3Provider: VolumeInM3Provider) : Comparable<Volume> {
    private val volumeInM3: Double by lazy {
        volumeInM3Provider.invoke()
    }

    companion object {
        fun of(value: Double, unit: Unit): Volume {
            return Volume { unit.toM3(value) }
        }
    }

    fun valueIn(unit: Unit): Double {
        return unit.fromM3(volumeInM3)
    }

    override operator fun compareTo(other: Volume): Int {
        return this.volumeInM3.compareTo(other.volumeInM3)
    }

    operator fun plus(other: Volume): Volume {
        return Volume { this.volumeInM3 + other.volumeInM3 }
    }

    operator fun minus(other: Volume): Volume {
        return Volume { this.volumeInM3 - other.volumeInM3 }
    }

    operator fun div(divider: Double): Volume {
        return Volume { this.volumeInM3 / divider }
    }

    operator fun times(factor: Double): Volume {
        return Volume { this.volumeInM3 * factor }
    }

    operator fun div(divider: Volume): Double {
        return this.volumeInM3 / divider.volumeInM3
    }

    operator fun times(massDensity: MassDensity): Mass {
        TODO("Not yet implemented")
    }

    operator fun div(mass: Mass): SpecificVolume {
        return SpecificVolume.of(this.volumeInM3 / mass.valueIn(kg), m3 / kg)
    }

    operator fun div(massUnit: Mass.Unit): SpecificVolume {
        return SpecificVolume.of(this.volumeInM3, m3 / massUnit)
    }

    operator fun div(volumetricFlow: VolumetricFlow) : Time {
        TODO()
    }


    interface Unit {
        fun fromM3(valueInM3: Double): Double
        fun toM3(value: Double): Double

        val shortName: String
        val longName: String

        override fun toString(): String

        companion object {
            fun ofCubic(lengthUnit: Length.Unit): Unit {
                return LengthDerivedUnit(lengthUnit)
            }
        }

        operator fun div(unit: Mass.Unit): SpecificVolume.Unit {
            return SpecificVolume.Unit.parameterized(this, unit)
        }

        operator fun div(timeUnit: Time.Unit): VolumetricFlow.Unit {
            TODO()
        }
    }

    private class LengthDerivedUnit(private val lengthUnit: Length.Unit) : Volume.Unit {
        override fun fromM3(valueInM3: Double): Double =
                valueInM3 * lengthUnit.fromMeter(1.0).pow(3)

        override fun toM3(value: Double): Double =
                value * lengthUnit.toMeter(1.0).pow(3)

        override val shortName: String = "${lengthUnit.shortName}^3"
        override val longName: String = "${lengthUnit.longName} cubed"

        override fun toString(): String = longName
    }
}
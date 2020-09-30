package nl.dutchland.grove.utility.derivedunits.massflow

import nl.dutchland.grove.utility.baseunits.mass.Mass
import nl.dutchland.grove.utility.baseunits.mass.kg
import nl.dutchland.grove.utility.baseunits.time.Second
import nl.dutchland.grove.utility.baseunits.time.Time
import nl.dutchland.grove.utility.baseunits.time.s
import nl.dutchland.grove.utility.derivedunits.kinematic.volumetricflow.VolumetricFlow
import nl.dutchland.grove.utility.derivedunits.mechanical.massdensity.MassDensity
import nl.dutchland.grove.utility.derivedunits.mechanical.volume.Volume
import nl.dutchland.grove.utility.derivedunits.mechanical.volume.m3
import nl.dutchland.grove.utility.derivedunits.specificvolume.SpecificVolume

private typealias KgPerSecondProvider = () -> Double

data class MassFlow internal constructor(private val kgPerSecondProvider: KgPerSecondProvider) : Comparable<MassFlow> {
    private val kgPerSecond: Double by lazy {
        kgPerSecondProvider.invoke()
    }

    companion object {
        fun of(value: Double, unit: MassFlow.Unit): MassFlow {
            return MassFlow { unit.toCubicMeterPerSecond(value) }
        }
    }

    fun valueIn(unit: MassFlow.Unit): Double {
        return unit.fromCubicMeterPerSecond(this.kgPerSecond)
    }

    operator fun plus(other: MassFlow): MassFlow {
        return MassFlow { this.kgPerSecond + other.kgPerSecond }
    }

    operator fun minus(other: MassFlow): MassFlow {
        return MassFlow { this.kgPerSecond - other.kgPerSecond }
    }

    operator fun times(time: Time): Mass {
        return Mass.of(this.kgPerSecond * time.valueIn(Second), kg)
    }

    operator fun times(factor: Double): MassFlow {
        return MassFlow { this.kgPerSecond * factor }
    }

    override operator fun compareTo(other: MassFlow): Int {
        return this.kgPerSecond.compareTo(other.kgPerSecond)
    }

    operator fun div(massDensity: MassDensity): VolumetricFlow {
        TODO("Not yet implemented")
    }

    operator fun times(specificVolume: SpecificVolume): VolumetricFlow {
        TODO("Not yet implemented")
    }

    interface Unit {
        val shortName: String
        val longName: String

        fun fromCubicMeterPerSecond(valueInCubicMeterPerSecond: Double): Double
        fun toCubicMeterPerSecond(value: Double): Double

        override fun toString(): String

        companion object {
            fun of(volumeUnit: Volume.Unit): Builder {
                return Builder { timeUnit -> ParameterizedUnit(volumeUnit, timeUnit) }
            }
        }

        fun interface Builder {
            fun per(timeUnit: Time.Unit): Unit
        }
    }

    private class ParameterizedUnit(private val volumeUnit: Volume.Unit, private val timeUnit: Time.Unit) : Unit {
        override fun fromCubicMeterPerSecond(valueInCubicMeterPerSecond: Double): Double {
            return volumeUnit.fromM3(valueInCubicMeterPerSecond) / timeUnit.fromSeconds(1.0)
        }

        override fun toCubicMeterPerSecond(value: Double): Double {
            return volumeUnit.toM3(value) / timeUnit.toSeconds(1.0)
        }

        override val shortName: String = "${volumeUnit.shortName}/${timeUnit.shortName}"
        override val longName: String = "${volumeUnit.longName} per ${timeUnit.longName}"

        override fun toString(): String {
            return longName
        }
    }
}

fun Iterable<MassFlow>.sum(): MassFlow {
    val sumInMetersPerSecond = this.map { flow -> flow.valueIn(kg / s) }.sum()
    return MassFlow.of(sumInMetersPerSecond, kg / s)
}


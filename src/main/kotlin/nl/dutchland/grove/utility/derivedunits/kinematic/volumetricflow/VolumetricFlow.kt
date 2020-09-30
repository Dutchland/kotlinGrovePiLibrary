package nl.dutchland.grove.utility.derivedunits.kinematic.volumetricflow

import nl.dutchland.grove.utility.baseunits.time.Second
import nl.dutchland.grove.utility.baseunits.time.Time
import nl.dutchland.grove.utility.baseunits.time.s
import nl.dutchland.grove.utility.derivedunits.massflow.MassFlow
import nl.dutchland.grove.utility.derivedunits.mechanical.massdensity.MassDensity
import nl.dutchland.grove.utility.derivedunits.mechanical.volume.Volume
import nl.dutchland.grove.utility.derivedunits.mechanical.volume.m3
import nl.dutchland.grove.utility.derivedunits.specificvolume.SpecificVolume

private typealias M3PerSecondProvider = () -> Double

data class VolumetricFlow internal constructor(private val speedInMeterPerSecondProvider: M3PerSecondProvider) : Comparable<VolumetricFlow> {
    private val m3PerSecond: Double by lazy {
        speedInMeterPerSecondProvider.invoke()
    }

    companion object {
        fun of(value: Double, unit: VolumetricFlow.Unit): VolumetricFlow {
            return VolumetricFlow { unit.toCubicMeterPerSecond(value) }
        }
    }

    fun valueIn(unit: VolumetricFlow.Unit): Double {
        return unit.fromCubicMeterPerSecond(this.m3PerSecond)
    }

    operator fun plus(other: VolumetricFlow): VolumetricFlow {
        return VolumetricFlow { this.m3PerSecond + other.m3PerSecond }
    }

    operator fun minus(other: VolumetricFlow): VolumetricFlow {
        return VolumetricFlow { this.m3PerSecond - other.m3PerSecond }
    }

    operator fun times(time: Time): Volume {
        return Volume.of(this.m3PerSecond * time.valueIn(Second), m3)
    }

    operator fun times(factor: Double): VolumetricFlow {
        return VolumetricFlow { this.m3PerSecond * factor }
    }

    override operator fun compareTo(other: VolumetricFlow): Int {
        return this.m3PerSecond.compareTo(other.m3PerSecond)
    }

    operator fun times(massDensity: MassDensity): MassFlow {
        TODO("Not yet implemented")
    }

    operator fun div(specificVolume: SpecificVolume): MassFlow {
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

fun Iterable<VolumetricFlow>.sum(): VolumetricFlow {
    val sumInMetersPerSecond = this.map { flow -> flow.valueIn(m3 / s) }.sum()
    return VolumetricFlow.of(sumInMetersPerSecond, m3 / s)
}




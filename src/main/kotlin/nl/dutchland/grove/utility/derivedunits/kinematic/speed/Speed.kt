package nl.dutchland.grove.utility.derivedunits.kinematic.speed

import nl.dutchland.grove.utility.baseunits.length.Length
import nl.dutchland.grove.utility.baseunits.length.Meter
import nl.dutchland.grove.utility.baseunits.length.m
import nl.dutchland.grove.utility.baseunits.time.Period
import nl.dutchland.grove.utility.baseunits.time.Second
import nl.dutchland.grove.utility.baseunits.time.Time
import nl.dutchland.grove.utility.baseunits.time.s
import nl.dutchland.grove.utility.derivedunits.kinematic.acceleration.Acceleration
import nl.dutchland.grove.utility.derivedunits.kinematic.volumetricflow.VolumetricFlow
import nl.dutchland.grove.utility.derivedunits.mechanical.area.Area

typealias Velocity = Speed

private typealias SpeedInMeterPerSecondProvider = () -> Double

data class Speed internal constructor(private val speedInMeterPerSecondProvider: SpeedInMeterPerSecondProvider) : Comparable<Speed> {
    private val speedInMeterPerSecond: Double by lazy {
        speedInMeterPerSecondProvider.invoke()
    }

    companion object {
        fun of(value: Double, unit: Speed.Unit): Speed {
            return Speed { unit.toMeterPerSecond(value) }
        }

        val LIGHT_SPEED_THROUGH_VACUUM = Speed.of(299_792_458.0, Meter/Second)
    }

    fun valueIn(unit: Speed.Unit): Double {
        return unit.fromMeterPerSecond(this.speedInMeterPerSecond)
    }

    operator fun plus(other: Speed): Speed {
        return Speed { this.speedInMeterPerSecond + other.speedInMeterPerSecond }
    }

    operator fun minus(other: Speed): Speed {
        return Speed { this.speedInMeterPerSecond - other.speedInMeterPerSecond }
    }

    operator fun times(time: Period): Length {
        return Length.of(this.speedInMeterPerSecond * time.valueIn(Second), Meter)
    }

    operator fun times(factor: Double): Speed {
        return Speed { this.speedInMeterPerSecond * factor }
    }

    operator fun div(timeUnit: Time.Unit): Acceleration {
        return Acceleration.of(this.speedInMeterPerSecond, (m/s)/timeUnit)
    }

    override operator fun compareTo(other: Speed): Int {
        return this.speedInMeterPerSecond.compareTo(other.speedInMeterPerSecond)
    }

    operator fun times(area: Area): VolumetricFlow {
        TODO("Not yet implemented")
    }

    interface Unit {
        val shortName: String
        val longName: String

        fun fromMeterPerSecond(valueInMeterPerSecond: Double): Double
        fun toMeterPerSecond(value: Double): Double

        operator fun div(divider: Time.Unit): Acceleration.Unit

        override fun toString(): String
    }

    class ParameterizedUnit(private val lengthUnit: Length.Unit, private val timeUnit: Time.Unit) : Unit {
        override fun fromMeterPerSecond(valueInMeterPerSecond: Double): Double {
            return lengthUnit.fromMeter(valueInMeterPerSecond) / timeUnit.fromSeconds(1.0)
        }

        override operator fun div(divider: Time.Unit): Acceleration.Unit {
            return Acceleration.ParameterizedUnit(this, divider)
        }

        override fun toMeterPerSecond(value: Double): Double {
            return lengthUnit.toMeter(value) / timeUnit.toSeconds(1.0)
        }

        override val shortName: String = "${lengthUnit.shortName}/${timeUnit.shortName}"
        override val longName: String = "${lengthUnit.longName} per ${timeUnit.longName}"

        override fun toString(): String {
            return longName
        }
    }
}

fun Iterable<Speed>.sum(): Speed {
    val sumInMetersPerSecond = this.map { speed -> speed.valueIn(MeterPerSecond) }.sum()
    return Speed.of(sumInMetersPerSecond, MeterPerSecond)
}
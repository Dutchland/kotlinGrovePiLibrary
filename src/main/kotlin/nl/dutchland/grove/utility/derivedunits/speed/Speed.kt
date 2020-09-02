package nl.dutchland.grove.utility.derivedunits.speed

import nl.dutchland.grove.utility.baseunits.length.Length
import nl.dutchland.grove.utility.baseunits.length.Meter
import nl.dutchland.grove.utility.baseunits.time.Period
import nl.dutchland.grove.utility.baseunits.time.Second
import nl.dutchland.grove.utility.derivedunits.acceleration.Acceleration

private typealias SpeedInMeterPerSecondProvider = () -> Double

data class Speed internal constructor(private val speedInMeterPerSecondProvider: SpeedInMeterPerSecondProvider) : Comparable<Speed> {
    private val speedInMeterPerSecond: Double by lazy {
        speedInMeterPerSecondProvider.invoke()
    }

    companion object {
        fun of(value: Double, unit: Speed.Unit): Speed {
            return Speed { unit.toMeterPerSecond(value) }
        }

        fun of(value: Double, lengthUnit: Length.Unit): Builder {
            return Builder { of(value, Unit(lengthUnit, it)) }
        }

        val LIGHT_SPEED_THROUGH_VACUUM = Speed.of(299_792_458.0, Meter).per(Second)
    }

    fun interface Builder {
        fun per(timeUnit: Period.TimeUnit): Speed
    }

    fun valueIn(lengthUnit: Length.Unit, timeUnit: Period.TimeUnit): Double {
        return valueIn(Speed.Unit(lengthUnit, timeUnit))
    }

    fun valueIn(unit: Speed.Unit): Double {
        return unit.fromMeterPerSecond(this.speedInMeterPerSecond)
    }

    override operator fun compareTo(other: Speed): Int {
        return this.speedInMeterPerSecond.compareTo(other.speedInMeterPerSecond)
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

    operator fun div(period: Period.TimeUnit): Acceleration {
        TODO("Not yet implemented")
    }

    open class Unit(private val lengthUnit: Length.Unit, private val timeUnit: Period.TimeUnit) {
        fun fromMeterPerSecond(valueInMeterPerSecond: Double): Double {
            return lengthUnit.fromMeter(valueInMeterPerSecond) / timeUnit.fromSeconds(1.0)
        }

        operator fun div(divider: Period.TimeUnit): Acceleration.Unit {
            return Acceleration.Unit(this, divider)
        }

        fun toMeterPerSecond(value: Double): Double {
            return lengthUnit.toMeter(value) / timeUnit.toSeconds(1.0)
        }

        open val shortName: String = "${lengthUnit.shortName}/${timeUnit.shortName}"
        open val longName: String = "${lengthUnit.longName} per ${timeUnit.longName}"

        override fun toString(): String {
            return longName
        }
    }
}

fun Iterable<Speed>.sum(): Speed {
    val sumInMetersPerSecond = this.map { speed -> speed.valueIn(MeterPerSecond) }.sum()
    return Speed.of(sumInMetersPerSecond, MeterPerSecond)
}
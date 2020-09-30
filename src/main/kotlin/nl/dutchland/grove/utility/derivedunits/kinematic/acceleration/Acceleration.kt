package nl.dutchland.grove.utility.derivedunits.kinematic.acceleration

import nl.dutchland.grove.utility.baseunits.time.Period
import nl.dutchland.grove.utility.baseunits.time.Second
import nl.dutchland.grove.utility.baseunits.time.Time
import nl.dutchland.grove.utility.derivedunits.kinematic.speed.MeterPerSecond
import nl.dutchland.grove.utility.derivedunits.kinematic.speed.Speed

private typealias SpeedInMeterPerSecondSquaredProvider = () -> Double

data class Acceleration internal constructor(private val speedInMeterPerSecondSquaredProvider: SpeedInMeterPerSecondSquaredProvider) : Comparable<Acceleration> {
    private val speedInMeterPerSecondSquared: Double by lazy {
        speedInMeterPerSecondSquaredProvider.invoke()
    }

    companion object {
        fun of(value: Double, unit: Acceleration.Unit): Acceleration {
            return Acceleration { unit.toMeterPerSecond(value) }
        }

        fun of(value: Double, speedUnit: Speed.Unit): Builder {
            return Builder { timeUnit -> of(value, ParameterizedUnit(speedUnit, timeUnit)) }
        }
    }

    fun interface Builder {
        fun per(timeUnit: Time.Unit): Acceleration
    }

    fun valueIn(unit: Acceleration.Unit): Double {
        return unit.fromMeterPerSecond(this.speedInMeterPerSecondSquared)
    }

    override operator fun compareTo(other: Acceleration): Int {
        return this.speedInMeterPerSecondSquared.compareTo(other.speedInMeterPerSecondSquared)
    }

    operator fun plus(other: Acceleration): Acceleration {
        return Acceleration { this.speedInMeterPerSecondSquared + other.speedInMeterPerSecondSquared }
    }

    operator fun minus(other: Acceleration): Acceleration {
        return Acceleration { this.speedInMeterPerSecondSquared - other.speedInMeterPerSecondSquared }
    }

    operator fun times(time: Period): Speed {
        return Speed.of(this.speedInMeterPerSecondSquared * time.valueIn(Second), MeterPerSecond)
    }

    operator fun times(factor: Double): Acceleration {
        return Acceleration { this.speedInMeterPerSecondSquared * factor }
    }

    interface Unit {
        fun fromMeterPerSecond(valueInMeterPerSecond: Double): Double
        fun toMeterPerSecond(value: Double): Double

        val shortName: String
        val longName: String

        override fun toString(): String
    }

    class ParameterizedUnit(private val speedUnit: Speed.Unit, private val timeUnit: Time.Unit) : Unit {
        override fun fromMeterPerSecond(valueInMeterPerSecond: Double): Double {
            return speedUnit.fromMeterPerSecond(valueInMeterPerSecond) / timeUnit.fromSeconds(1.0)
        }

        override fun toMeterPerSecond(value: Double): Double {
            return speedUnit.toMeterPerSecond(value) / timeUnit.toSeconds(1.0)
        }

        override val shortName: String = "(${speedUnit.shortName})/${timeUnit.shortName}"
        override val longName: String = "${speedUnit.longName} per ${timeUnit.longName}"

        override fun toString(): String {
            return longName
        }
    }
}

fun Iterable<Acceleration>.sum(): Acceleration {
    return this.fold(Acceleration.of(0.0, MeterPerSecondSquared)) { s1, s2 -> s1 + s2 }
}
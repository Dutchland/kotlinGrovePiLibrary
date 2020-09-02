package nl.dutchland.grove.utility.derivedunits.acceleration

import nl.dutchland.grove.utility.baseunits.length.Length
import nl.dutchland.grove.utility.baseunits.length.Meter
import nl.dutchland.grove.utility.baseunits.length.m
import nl.dutchland.grove.utility.baseunits.time.Period
import nl.dutchland.grove.utility.baseunits.time.Second
import nl.dutchland.grove.utility.baseunits.time.s
import nl.dutchland.grove.utility.derivedunits.speed.MeterPerSecond
import nl.dutchland.grove.utility.derivedunits.speed.Speed

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
            return Builder { timeUnit -> of(value, Unit(speedUnit, timeUnit)) }
        }
    }

    fun interface Builder {
        fun per(timeUnit: Period.TimeUnit): Acceleration
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

    open class Unit(private val speedUnit: Speed.Unit, private val timeUnit: Period.TimeUnit) {
        fun fromMeterPerSecond(valueInMeterPerSecond: Double): Double {
            return speedUnit.fromMeterPerSecond(valueInMeterPerSecond) / timeUnit.fromSeconds(1.0)
        }

        fun toMeterPerSecond(value: Double): Double {
            return speedUnit.toMeterPerSecond(value) / timeUnit.toSeconds(1.0)
        }

        open val shortName: String = "(${speedUnit.shortName})/${timeUnit.shortName}"
        open val longName: String = "${speedUnit.longName} per ${timeUnit.longName}"

        override fun toString(): String {
            return longName
        }
    }
}

fun Iterable<Acceleration>.sum(): Acceleration {
    return this.fold(Acceleration.of(0.0, MeterPerSecondSquared)) { s1, s2 -> s1 + s2 }
}
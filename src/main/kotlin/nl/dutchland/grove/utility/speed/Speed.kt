package nl.dutchland.grove.utility.speed

import nl.dutchland.grove.utility.length.Kilometer
import nl.dutchland.grove.utility.length.Length
import nl.dutchland.grove.utility.length.Meter
import nl.dutchland.grove.utility.time.Period
import nl.dutchland.grove.utility.time.Second

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
            return object : Builder {
                override fun per(timeUnit: Period.TimeUnit): Speed {
                    return of(value, Unit(lengthUnit, timeUnit))
                }
            }
        }

        val LIGHT_SPEED_THROUGH_VACUUM = Speed.of(299_792_458.0, Meter).per(Second)
    }

    interface Builder {
        fun per(timeUnit: Period.TimeUnit) : Speed
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

    operator fun times(time: Period) : Length {
        return Length.of(this.speedInMeterPerSecond * time.valueIn(Second), Meter)
    }

    operator fun times(factor: Double) : Speed {
        return Speed {this.speedInMeterPerSecond * factor }
    }

    open class Unit(private val lengthUnit: Length.Unit, private val timeUnit: Period.TimeUnit) {
        fun fromMeterPerSecond(valueInMeterPerSecond: Double): Double {
            return Period.of(Length.of(valueInMeterPerSecond, Meter).valueIn(lengthUnit), timeUnit)
                    .valueIn(Second)
        }

        fun toMeterPerSecond(value: Double): Double {
            return Period.of(Length.of(value, lengthUnit).valueIn(Meter), Second)
                    .valueIn(timeUnit)
        }

        val shortName: String = "${lengthUnit.shortName}/${timeUnit.shortName}"
        val longName: String = "${lengthUnit.longName} per ${timeUnit.longName}"

        override fun toString(): String {
            return longName
        }
    }
}
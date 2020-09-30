package nl.dutchland.grove.utility.derivedunits.kinematic.angularvelocity

import nl.dutchland.grove.utility.baseunits.length.Length
import nl.dutchland.grove.utility.baseunits.length.Meter
import nl.dutchland.grove.utility.baseunits.time.Period
import nl.dutchland.grove.utility.baseunits.time.Second
import nl.dutchland.grove.utility.baseunits.time.Time
import nl.dutchland.grove.utility.derivedunits.angle.Angle
import nl.dutchland.grove.utility.derivedunits.kinematic.angularacceleration.AngularAcceleration

private typealias RadiansPerSecondProvider = () -> Double

data class AngularVelocity internal constructor(private val radiansPerSecondProvider: RadiansPerSecondProvider) : Comparable<AngularVelocity> {
    private val radiansPerSecond: Double by lazy {
        radiansPerSecondProvider.invoke()
    }

    companion object {
        fun of(value: Double, unit: Unit): AngularVelocity {
            return AngularVelocity { unit.toRadiansPerSecond(value) }
        }
    }

    fun valueIn(unit: AngularVelocity.Unit): Double {
        return unit.fromRadiansPerSecond(this.radiansPerSecond)
    }

    operator fun plus(other: AngularVelocity): AngularVelocity {
        return AngularVelocity { this.radiansPerSecond + other.radiansPerSecond }
    }

    operator fun minus(other: AngularVelocity): AngularVelocity {
        return AngularVelocity { this.radiansPerSecond - other.radiansPerSecond }
    }

    operator fun times(time: Period): Length {
        return Length.of(this.radiansPerSecond * time.valueIn(Second), Meter)
    }

    operator fun times(factor: Double): AngularVelocity {
        return AngularVelocity { this.radiansPerSecond * factor }
    }

//    operator fun div(timeUnit: Time.Unit): AngularAcceleration {
//        return Acceleration.of(this.radiansPerSecond, (m/s)/timeUnit)
//    }

    override operator fun compareTo(other: AngularVelocity): Int {
        return this.radiansPerSecond.compareTo(other.radiansPerSecond)
    }

    interface Unit {
        val shortName: String
        val longName: String

        fun fromRadiansPerSecond(valueInRadiansPerSecond: Double): Double
        fun toRadiansPerSecond(value: Double): Double

        override fun toString(): String

        operator fun div(timeUnit: Time.Unit): AngularAcceleration.Unit {
            return AngularAcceleration.ParameterizedUnit(this, timeUnit)
        }

        companion object {
            fun of(angleUnit: Angle.Unit): Builder {
                return Builder { timeUnit -> ParameterizedUnit(angleUnit, timeUnit) }
            }
        }

        fun interface Builder {
            fun per(timeUnit: Time.Unit): Unit
        }
    }

    private class ParameterizedUnit(private val angleUnit: Angle.Unit, private val timeUnit: Time.Unit) : Unit {
        override fun fromRadiansPerSecond(valueInRadiansPerSecond: Double): Double {
            return angleUnit.fromRadians(valueInRadiansPerSecond) / timeUnit.fromSeconds(1.0)
        }

//        override operator fun div(divider: Time.Unit): Acceleration.Unit {
//            return Acceleration.ParameterizedUnit(this, divider)
//        }

        override fun toRadiansPerSecond(value: Double): Double {
            return angleUnit.toRadians(value) / timeUnit.toSeconds(1.0)
        }

        override val shortName: String = "${angleUnit.shortName}/${timeUnit.shortName}"
        override val longName: String = "${angleUnit.longName} per ${timeUnit.longName}"

        override fun toString(): String {
            return longName
        }
    }
}

//fun Iterable<Speed>.sum(): Speed {
//    val sumInMetersPerSecond = this.map { speed -> speed.valueIn(MeterPerSecond) }.sum()
//    return Speed.of(sumInMetersPerSecond, MeterPerSecond)
//}
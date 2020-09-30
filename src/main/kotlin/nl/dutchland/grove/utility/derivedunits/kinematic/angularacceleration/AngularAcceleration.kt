package nl.dutchland.grove.utility.derivedunits.kinematic.angularacceleration

import nl.dutchland.grove.utility.baseunits.length.Length
import nl.dutchland.grove.utility.baseunits.length.Meter
import nl.dutchland.grove.utility.baseunits.time.Period
import nl.dutchland.grove.utility.baseunits.time.Second
import nl.dutchland.grove.utility.baseunits.time.Time
import nl.dutchland.grove.utility.derivedunits.angle.Angle
import nl.dutchland.grove.utility.derivedunits.kinematic.angularvelocity.AngularVelocity

private typealias RadiansPerSecondSquaredProvider = () -> Double

data class AngularAcceleration internal constructor(private val radiansPerSecondSquaredProvider: RadiansPerSecondSquaredProvider) : Comparable<AngularAcceleration> {
    private val radiansPerSecondSquared: Double by lazy {
        radiansPerSecondSquaredProvider.invoke()
    }

    companion object {
        fun of(value: Double, unit: Unit): AngularAcceleration {
            return AngularAcceleration { unit.toRadiansPerSecondSquared(value) }
        }
    }

    fun valueIn(unit: AngularAcceleration.Unit): Double {
        return unit.fromRadiansPerSecondSquared(this.radiansPerSecondSquared)
    }

    operator fun plus(other: AngularAcceleration): AngularAcceleration {
        return AngularAcceleration { this.radiansPerSecondSquared + other.radiansPerSecondSquared }
    }

    operator fun minus(other: AngularAcceleration): AngularAcceleration {
        return AngularAcceleration { this.radiansPerSecondSquared - other.radiansPerSecondSquared }
    }

    operator fun times(time: Period): Length {
        return Length.of(this.radiansPerSecondSquared * time.valueIn(Second), Meter)
    }

    operator fun times(factor: Double): AngularAcceleration {
        return AngularAcceleration { this.radiansPerSecondSquared * factor }
    }

    override operator fun compareTo(other: AngularAcceleration): Int {
        return this.radiansPerSecondSquared.compareTo(other.radiansPerSecondSquared)
    }

    interface Unit {
        val shortName: String
        val longName: String

        fun fromRadiansPerSecondSquared(valueInRadiansPerSecond: Double): Double
        fun toRadiansPerSecondSquared(value: Double): Double

        override fun toString(): String
    }

    internal class ParameterizedUnit(private val angularVelocityUnit: AngularVelocity.Unit, private val timeUnit: Time.Unit,) : Unit {
        override fun fromRadiansPerSecondSquared(valueInRadiansPerSecond: Double): Double {
            return angularVelocityUnit.fromRadiansPerSecond(valueInRadiansPerSecond) / timeUnit.fromSeconds(1.0)
        }

//        override operator fun div(divider: Time.Unit): Acceleration.Unit {
//            return Acceleration.ParameterizedUnit(this, divider)
//        }

        override fun toRadiansPerSecondSquared(value: Double): Double {
            return angularVelocityUnit.toRadiansPerSecond(value) / timeUnit.toSeconds(1.0)
        }

        override val shortName: String = "(${angularVelocityUnit.shortName})/${timeUnit.shortName}"
        override val longName: String = "${angularVelocityUnit.longName} per ${timeUnit.longName}"

        override fun toString(): String {
            return longName
        }
    }
}

//fun Iterable<Speed>.sum(): Speed {
//    val sumInMetersPerSecond = this.map { speed -> speed.valueIn(MeterPerSecond) }.sum()
//    return Speed.of(sumInMetersPerSecond, MeterPerSecond)
//}
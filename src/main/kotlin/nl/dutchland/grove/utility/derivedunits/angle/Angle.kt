package nl.dutchland.grove.utility.derivedunits.angle

import nl.dutchland.grove.utility.baseunits.time.Time
import nl.dutchland.grove.utility.derivedunits.kinematic.angularvelocity.AngularVelocity

class Angle internal constructor(
        private val value: Double,
        private val unit: Unit) {

    fun valueIn(unit: Unit): Double {
        TODO()
    }

    companion object {
        fun of(value: Double, unit: Unit): Angle {
            TODO()
        }
    }

    interface Unit {
        val longName: String
        val shortName: String

        fun fromRadians(valueInRadians: Double): Double
        fun toRadians(value: Double): Double

        override fun toString(): String

        operator fun div(timeUnit: Time.Unit): AngularVelocity.Unit {
            return AngularVelocity.Unit.of(this).per(timeUnit)
        }
    }
}
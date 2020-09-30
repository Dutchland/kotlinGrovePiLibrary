package nl.dutchland.grove.utility.derivedunits.angle

import kotlin.math.PI

private const val TO_RADIAN_FACTOR =  PI / 180.0

class Degree : Angle.Unit {
    override val longName: String = "Degrees"
    override val shortName: String = "deg"

    override fun fromRadians(valueInRadians: Double): Double {
        return valueInRadians / TO_RADIAN_FACTOR
    }

    override fun toRadians(value: Double): Double {
        return value * TO_RADIAN_FACTOR
    }

    override fun toString(): String = longName
}
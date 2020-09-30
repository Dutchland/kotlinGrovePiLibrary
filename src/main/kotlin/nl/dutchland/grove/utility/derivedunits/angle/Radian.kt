package nl.dutchland.grove.utility.derivedunits.angle

object Radian : Angle.Unit {
    override val longName: String = "Radian"
    override val shortName: String = "rad"

    override fun fromRadians(valueInRadians: Double): Double = valueInRadians
    override fun toRadians(value: Double): Double = value

    override fun toString(): String = longName
}
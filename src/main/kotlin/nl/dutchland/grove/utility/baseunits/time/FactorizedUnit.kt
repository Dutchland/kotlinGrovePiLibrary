package nl.dutchland.grove.utility.baseunits.time

internal class FactorizedUnit(private val toSecondFactor: Double,
                              override val shortName: String,
                              override val longName: String) : Time.Unit {

    override fun toSeconds(value: Double): Double = value * toSecondFactor
    override fun fromSeconds(valueInSeconds: Double): Double = valueInSeconds / toSecondFactor

    override fun toString(): String = longName
}
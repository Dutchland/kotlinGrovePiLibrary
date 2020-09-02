package nl.dutchland.grove.utility.derivedunits.energy

import nl.dutchland.grove.utility.derivedunits.area.m2
import nl.dutchland.grove.utility.baseunits.mass.Kilogram
import nl.dutchland.grove.utility.baseunits.time.Second

object Joule : EnergyAmount.Unit by EnergyAmount.Unit.ofParameterized(m2, Kilogram, Second) {
    override val shortName: String = "J"
    override val longName: String = "Joule"

    override fun fromJoule(valueInJoule: Double): Double = valueInJoule
    override fun toJoule(value: Double): Double = value
}
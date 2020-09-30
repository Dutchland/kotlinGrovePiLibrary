package nl.dutchland.grove.utility.derivedunits.power

import nl.dutchland.grove.utility.baseunits.time.Second
import nl.dutchland.grove.utility.derivedunits.energy.Joule

object Watt : Power.Unit by Joule / Second {
    override val shortName: String = "W"
    override val longName: String = "Watt"
}
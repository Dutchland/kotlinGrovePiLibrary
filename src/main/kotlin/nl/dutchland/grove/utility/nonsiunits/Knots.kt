package nl.dutchland.grove.utility.nonsiunits

import nl.dutchland.grove.utility.baseunits.time.Hour
import nl.dutchland.grove.utility.derivedunits.speed.Speed

object Knots : Speed.Unit(NauticalMile, Hour) {
    override val shortName = "kn"
    override val longName = "Knots"
}
package nl.dutchland.grove.utility.nonsiunits

import nl.dutchland.grove.utility.baseunits.time.Hour
import nl.dutchland.grove.utility.derivedunits.kinematic.speed.Speed

object Knots : Speed.Unit by NauticalMile / Hour {
    override val shortName = "kn"
    override val longName = "Knots"
}
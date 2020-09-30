package nl.dutchland.grove.utility.nonsiunits

import nl.dutchland.grove.utility.baseunits.length.Kilometer
import nl.dutchland.grove.utility.baseunits.time.Hour
import nl.dutchland.grove.utility.derivedunits.kinematic.speed.Speed

object KilometerPerHour : Speed.Unit by Kilometer / Hour
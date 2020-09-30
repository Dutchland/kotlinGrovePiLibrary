package nl.dutchland.grove.utility.derivedunits.kinematic.acceleration

import nl.dutchland.grove.utility.baseunits.length.Meter
import nl.dutchland.grove.utility.baseunits.time.Second

object MeterPerSecondSquared : Acceleration.Unit by (Meter / Second) / Second {
    override val longName: String = "Meter per Second squared"
    override val shortName: String = "m/s^2"
}
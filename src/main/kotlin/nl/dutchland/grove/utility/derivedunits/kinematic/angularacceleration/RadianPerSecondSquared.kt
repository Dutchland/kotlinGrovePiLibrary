package nl.dutchland.grove.utility.derivedunits.kinematic.angularacceleration

import nl.dutchland.grove.utility.baseunits.time.Second
import nl.dutchland.grove.utility.derivedunits.angle.Radian

class RadianPerSecondSquared : AngularAcceleration.Unit by (Radian / Second) / Second {
    override val longName: String = "Radian per Second squared"
    override val shortName: String = "rad/s^2"
}
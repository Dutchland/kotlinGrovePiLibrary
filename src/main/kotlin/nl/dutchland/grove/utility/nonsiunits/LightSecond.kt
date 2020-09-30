package nl.dutchland.grove.utility.nonsiunits

import nl.dutchland.grove.utility.baseunits.length.Length
import nl.dutchland.grove.utility.baseunits.length.Meter
import nl.dutchland.grove.utility.baseunits.time.Period
import nl.dutchland.grove.utility.baseunits.time.Second
import nl.dutchland.grove.utility.derivedunits.kinematic.speed.Speed

object LightSecond : Length.Unit by Length.Unit.ofParameterized(
        longName = "Light second",
        shortName = "ls",
        toMeterFactor = (Speed.LIGHT_SPEED_THROUGH_VACUUM * Period.of(1.0, Second)).valueIn(Meter))
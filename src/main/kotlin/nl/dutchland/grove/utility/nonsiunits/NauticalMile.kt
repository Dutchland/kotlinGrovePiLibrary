package nl.dutchland.grove.utility.nonsiunits

import nl.dutchland.grove.utility.baseunits.length.Length

object NauticalMile : Length.Unit by Length.Unit.ofParameterized(
        longName = "Nautical mile",
        shortName = "NM",
        toMeterFactor = 1852.0)
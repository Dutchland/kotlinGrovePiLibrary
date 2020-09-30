package nl.dutchland.grove.utility.nonsiunits

import nl.dutchland.grove.utility.baseunits.length.Length

object Mile : Length.Unit by Length.ParameterizedUnit(
        longName = "Mile",
        shortName = "",
        toMeterFactor = 1600.0)
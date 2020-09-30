package nl.dutchland.grove.utility.baseunits.time

typealias h = Hour
object Hour : Time.Unit by FactorizedUnit(
        toSecondFactor = 60.0 * 60.0,
        shortName = "h",
        longName = "Hour")
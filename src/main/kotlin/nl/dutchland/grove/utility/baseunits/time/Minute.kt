package nl.dutchland.grove.utility.baseunits.time

typealias min = Minute
object Minute : Time.Unit by FactorizedUnit(
        toSecondFactor = 60.0,
        shortName = "min",
        longName = "Minute")
package nl.dutchland.grove.utility.baseunits.time

typealias s = Second
object Second : Time.Unit by FactorizedUnit(
        toSecondFactor = 1.0,
        shortName = "s",
        longName = "Second")
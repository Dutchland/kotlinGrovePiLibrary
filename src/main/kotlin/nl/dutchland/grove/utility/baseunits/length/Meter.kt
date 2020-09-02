package nl.dutchland.grove.utility.baseunits.length

typealias m = Meter
object Meter : Length.Unit by Length.Unit.ofParameterized(
        longName = "Meter",
        shortName = "m",
        toMeterFactor = 1.0)
package nl.dutchland.grove.utility.baseunits.length

typealias km = Kilometer
object Kilometer : Length.Unit by Length.Unit.ofParameterized(
        longName = "Kilometer",
        shortName = "km",
        toMeterFactor = 1000.0)
package nl.dutchland.grove.utility.baseunits.length

typealias hm = Hectometer
object Hectometer : Length.Unit by Length.Unit.ofParameterized(
        longName = "Hectometer",
        shortName = "hm",
        toMeterFactor = 100.0)
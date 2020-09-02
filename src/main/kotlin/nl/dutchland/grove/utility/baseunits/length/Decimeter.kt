package nl.dutchland.grove.utility.baseunits.length

typealias dm = Decimeter
object Decimeter : Length.Unit by Length.Unit.ofParameterized(
        longName = "Decimeter",
        shortName = "dm",
        toMeterFactor = 0.1)
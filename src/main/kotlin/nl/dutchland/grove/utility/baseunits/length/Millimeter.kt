package nl.dutchland.grove.utility.baseunits.length

typealias mm = Millimeter
object Millimeter : Length.Unit by Length.Unit.ofParameterized(
        longName = "Millimeter",
        shortName = "mm",
        toMeterFactor = 0.001)
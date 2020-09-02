package nl.dutchland.grove.utility.baseunits.length

import nl.dutchland.grove.utility.derivedunits.area.cm2

typealias cm = Centimeter
object Centimeter : Length.Unit by Length.Unit.ofParameterized(
        longName = "Centimeter",
        shortName = "cm",
        toMeterFactor = 0.01) {
    operator fun times(centimeter: Centimeter) : cm2 {
        return cm2
    }
}
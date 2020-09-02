package nl.dutchland.grove.utility.derivedunits.volume

import nl.dutchland.grove.utility.baseunits.length.*

typealias km3 = QubicKilometer
object QubicKilometer : Volume.Unit by Volume.Unit.ofCubic(Kilometer)

typealias m3 = QubicMeter
object QubicMeter : Volume.Unit by Volume.Unit.ofCubic(Meter)

typealias dm3 = QubicDecimeter
object QubicDecimeter : Volume.Unit by Volume.Unit.ofCubic(Decimeter)

typealias cm3 = QubicCentimeter
object QubicCentimeter : Volume.Unit by Volume.Unit.ofCubic(Centimeter)

typealias mm3 = QubicMillimeter
object QubicMillimeter : Volume.Unit by Volume.Unit.ofCubic(Millimeter)

typealias liter = Liter
object Liter : Volume.Unit by Volume.Unit.ofCubic(Decimeter) {
    override val longName: String = "Liter"
    override val shortName: String = "l"
}
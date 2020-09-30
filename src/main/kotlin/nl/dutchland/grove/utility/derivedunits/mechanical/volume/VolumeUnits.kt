package nl.dutchland.grove.utility.derivedunits.mechanical.volume

import nl.dutchland.grove.utility.baseunits.length.*

typealias km3 = CubicKilometer
object CubicKilometer : Volume.Unit by Volume.Unit.ofCubic(Kilometer)

typealias m3 = CubicMeter
object CubicMeter : Volume.Unit by Volume.Unit.ofCubic(Meter)

typealias dm3 = CubicDecimeter
object CubicDecimeter : Volume.Unit by Volume.Unit.ofCubic(Decimeter)

typealias cm3 = CubicCentimeter
object CubicCentimeter : Volume.Unit by Volume.Unit.ofCubic(Centimeter)

typealias mm3 = CubicMillimeter
object CubicMillimeter : Volume.Unit by Volume.Unit.ofCubic(Millimeter)

typealias l = Liter
object Liter : Volume.Unit by Volume.Unit.ofCubic(Decimeter) {
    override val longName: String = "Liter"
    override val shortName: String = "l"
}
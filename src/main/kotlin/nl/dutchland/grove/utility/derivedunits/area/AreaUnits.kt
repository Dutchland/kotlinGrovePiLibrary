package nl.dutchland.grove.utility.derivedunits.area

import nl.dutchland.grove.utility.baseunits.length.*

object SquaredKilometer : Area.Unit by Area.Unit.ofSquared(Kilometer)
typealias km2 = SquaredKilometer

object SquaredHectometer : Area.Unit by Area.Unit.ofSquared(Hectometer)
typealias hm2 = SquaredHectometer

object SquaredMeter : Area.Unit by Area.Unit.ofSquared(Meter)
typealias m2 = SquaredMeter

object SquaredDecimeter : Area.Unit by Area.Unit.ofSquared(Decimeter)
typealias dm2 = SquaredDecimeter

object SquaredCentimeter : Area.Unit by Area.Unit.ofSquared(Centimeter)
typealias cm2 = SquaredCentimeter

object SquaredMillimeter : Area.Unit by Area.Unit.ofSquared(Millimeter)
typealias mm2 = SquaredMillimeter
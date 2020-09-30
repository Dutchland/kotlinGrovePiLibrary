package nl.dutchland.grove.utility.derivedunits.photometric.luminance

import nl.dutchland.grove.utility.baseunits.luminousintensity.LuminousIntensity
import nl.dutchland.grove.utility.derivedunits.mechanical.area.Area

class Luminance internal constructor(
        private val valueInCandelaPerSquareMeter: Double){

    fun valueIn(unit: Unit): Double {
        return unit.fromCandelaPerSquareMeter(valueInCandelaPerSquareMeter)
    }

    interface Unit {
        val shortName: String
        val longName: String

        fun fromCandelaPerSquareMeter(valueInCandelaPerSquareMeter: Double) : Double

        fun toCandelaPerSquareMeter(value: Double) : Double
    }

    internal class ParameterizedUnit(luminousIntensityUnit: LuminousIntensity.Unit, areaUnit: Area.Unit) : Unit {
        override val shortName: String
            get() = TODO("Not yet implemented")
        override val longName: String
            get() = TODO("Not yet implemented")

        override fun fromCandelaPerSquareMeter(valueInCandelaPerSquareMeter: Double): Double {
            TODO("Not yet implemented")
        }

        override fun toCandelaPerSquareMeter(value: Double): Double {
            TODO("Not yet implemented")
        }
    }
}
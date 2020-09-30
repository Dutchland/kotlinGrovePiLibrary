package nl.dutchland.grove.utility.derivedunits.specificheatcapacity

import nl.dutchland.grove.utility.baseunits.mass.Mass
import nl.dutchland.grove.utility.derivedunits.heatcapacity.HeatCapacity

class SpecificHeatCapacity private constructor(
        private val valueInJoulePerKilogramPerKelvin: Double) {

    companion object {
        fun of(value: Double, unit: SpecificHeatCapacity.Unit) : SpecificHeatCapacity {
            TODO()
        }
    }

    fun valueIn(unit: SpecificHeatCapacity.Unit): Double {
        TODO()
    }

    operator fun times(mass: Mass) : HeatCapacity {
        TODO()
    }


    interface Unit {
        fun fromJoulePerKelvinPerKilogram(valueInJoulePerKelvin: Double) : Double
        fun toJoulePerKelvinPerKilogram(value: Double) : Double
    }
}
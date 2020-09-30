package nl.dutchland.grove.utility.derivedunits.heatcapacity

import nl.dutchland.grove.utility.baseunits.mass.Mass
import nl.dutchland.grove.utility.baseunits.temperature.Temperature
import nl.dutchland.grove.utility.derivedunits.energy.EnergyAmount
import nl.dutchland.grove.utility.derivedunits.mechanical.volume.Volume
import nl.dutchland.grove.utility.derivedunits.specificheatcapacity.SpecificHeatCapacity
import nl.dutchland.grove.utility.derivedunits.volumetricheatcapacity.VolumetricHeatCapacity

class HeatCapacity private constructor(
        private val valueInJoulePerKelvin: Double) {

    companion object {
        fun of(value: Double, unit: HeatCapacity.Unit) : HeatCapacity {
            TODO()
        }
    }

    fun valueIn(unit: HeatCapacity.Unit): Double {
        TODO()
    }

    operator fun plus(other: HeatCapacity): HeatCapacity {
        TODO("Not yet implemented")
    }

    operator fun times(temperature: Temperature): EnergyAmount {
        TODO()
    }


    interface Unit {
        fun fromJoulePerKelvin(valueInJoulePerKelvin: Double) : Double
        fun toJoulePerKelvin(value: Double) : Double

        operator fun div(massUnit: Mass.Unit) : SpecificHeatCapacity.Unit {
            TODO()
        }

        operator fun div(volumeUnit: Volume.Unit) : VolumetricHeatCapacity.Unit {
            TODO()
        }

    }
}
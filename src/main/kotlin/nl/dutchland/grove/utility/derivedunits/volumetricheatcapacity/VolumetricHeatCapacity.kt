package nl.dutchland.grove.utility.derivedunits.volumetricheatcapacity

import nl.dutchland.grove.utility.baseunits.mass.Mass
import nl.dutchland.grove.utility.derivedunits.heatcapacity.HeatCapacity
import nl.dutchland.grove.utility.derivedunits.mechanical.volume.Volume

class VolumetricHeatCapacity private constructor(
        private val valueInJoulePerM3PerKelvin: Double) {

    companion object {
        fun of(value: Double, unit: VolumetricHeatCapacity.Unit) : VolumetricHeatCapacity {
            TODO()
        }
    }

    fun valueIn(unit: VolumetricHeatCapacity.Unit): Double {
        TODO()
    }

    operator fun times(volume: Volume) : HeatCapacity {
        TODO()
    }


    interface Unit {
        fun fromJoulePerKelvinPerKilogram(valueInJoulePerKelvin: Double) : Double
        fun toJoulePerKelvinPerKilogram(value: Double) : Double
    }
}
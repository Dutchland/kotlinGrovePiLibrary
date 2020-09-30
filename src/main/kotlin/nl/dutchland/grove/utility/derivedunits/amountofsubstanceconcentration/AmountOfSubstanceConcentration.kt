package nl.dutchland.grove.utility.derivedunits.amountofsubstanceconcentration

import nl.dutchland.grove.utility.baseunits.amount.AmountOfSubstance
import nl.dutchland.grove.utility.derivedunits.mechanical.volume.Volume

class AmountOfSubstanceConcentration internal constructor(
        private val molPerCubicMeter : Double){

    fun valueIn(unit: AmountOfSubstanceConcentration.Unit) : Double {
        TODO()
    }

    operator fun times(volume : Volume) : AmountOfSubstance {
        TODO()
    }

    operator fun div(amountOfSubstance: AmountOfSubstance) : Volume {
        TODO()
    }

    interface Unit {
        val shortName: String
        val longName: String
    }
}
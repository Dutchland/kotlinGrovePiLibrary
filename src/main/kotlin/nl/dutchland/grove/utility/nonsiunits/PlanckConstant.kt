package nl.dutchland.grove.utility.nonsiunits

import nl.dutchland.grove.utility.baseunits.time.Second
import nl.dutchland.grove.utility.baseunits.time.Time
import nl.dutchland.grove.utility.derivedunits.energy.EnergyAmount
import nl.dutchland.grove.utility.derivedunits.energy.Joule

private const val PLANCK_CONSTANT: Double = 6.626070040 * 10e-34

class PlanckConstant {
    operator fun div(timePeriod: Time): EnergyAmount {
        return EnergyAmount.of(PLANCK_CONSTANT / timePeriod.valueIn(Second), Joule)
    }
}
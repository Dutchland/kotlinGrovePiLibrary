package nl.dutchland.grove.utility.derivedunits.pressure

import nl.dutchland.grove.utility.derivedunits.mechanical.area.m2
import nl.dutchland.grove.utility.derivedunits.force.N

class Pascal : Pressure.Unit by N / m2 {
    override val shortName: String = "Pa"
    override val longName: String = "Pascal"
}
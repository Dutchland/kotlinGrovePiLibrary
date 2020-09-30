package nl.dutchland.grove.utility.derivedunits.photometric.luminance

import nl.dutchland.grove.utility.baseunits.luminousintensity.Candela
import nl.dutchland.grove.utility.derivedunits.mechanical.area.m2

class CandelaPerSquaredMeter : Luminance.Unit by Candela / m2 {
}
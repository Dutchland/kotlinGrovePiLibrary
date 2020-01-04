package nl.dutchland.grove.led

import nl.dutchland.grove.digitaloutput.PulseWidthModulationOutputDevice
import nl.dutchland.grove.utility.Fraction

interface DimmableLed : Led, PulseWidthModulationOutputDevice {
    override fun turnOn(brightnessPercentage: Fraction)
}
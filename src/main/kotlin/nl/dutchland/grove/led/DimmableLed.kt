package nl.dutchland.grove.led

import nl.dutchland.grove.digitaloutput.PulseWidthModulationOutputDevice
import nl.dutchland.grove.utility.FractionalPercentage

interface DimmableLed : Led, PulseWidthModulationOutputDevice {
    override fun turnOn(brightnessPercentage: FractionalPercentage)
}
package nl.dutchland.grove.digitaloutput

import nl.dutchland.grove.utility.FractionalPercentage

interface PulseWidthModulationOutputDevice : DigitalOutputDevice {
    fun turnOn(percentage: FractionalPercentage)
}
package nl.dutchland.grove.digitaloutput

import nl.dutchland.grove.utility.Fraction

interface PulseWidthModulationOutputDevice : DigitalOutputDevice {
    fun turnOn(percentage: Fraction)
}
package nl.dutchland.grove.buzzer

import nl.dutchland.grove.digitaloutput.PulseWidthModulationOutputDevice
import nl.dutchland.grove.utility.FractionalPercentage

interface AdjustableBuzzer : Buzzer, PulseWidthModulationOutputDevice {
    override fun turnOn(volume: FractionalPercentage)
}
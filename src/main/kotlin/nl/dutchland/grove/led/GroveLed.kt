package nl.dutchland.grove.led

import nl.dutchland.grove.digitaloutput.GrovePulseWithModulationOutputDevice
import nl.dutchland.grove.utility.FractionalPercentage
import org.iot.raspberry.grovepi.devices.GroveLed

internal class GroveLed(groveLed : GroveLed) : DimmableLed, GrovePulseWithModulationOutputDevice(groveLed) {
}

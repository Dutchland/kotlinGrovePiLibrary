package nl.dutchland.grove.led

import nl.dutchland.grove.digitaloutput.GrovePulseWidthModulationOutputDevice
import org.iot.raspberry.grovepi.devices.GroveLed

internal class GroveLed(groveLed : GroveLed)
    : DimmableLed, GrovePulseWidthModulationOutputDevice(groveLed)


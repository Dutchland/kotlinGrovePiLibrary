package nl.dutchland.grove.buzzer

import nl.dutchland.grove.digitaloutput.GrovePulseWidthModulationOutputDevice
import org.iot.raspberry.grovepi.devices.GroveLed

internal class GroveBuzzer(groveLed : GroveLed)
    : AdjustableBuzzer, GrovePulseWidthModulationOutputDevice(groveLed)
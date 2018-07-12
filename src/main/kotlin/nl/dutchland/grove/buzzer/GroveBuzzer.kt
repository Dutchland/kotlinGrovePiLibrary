package nl.dutchland.grove.buzzer

import nl.dutchland.grove.digitaloutput.GrovePulseWithModulationOutputDevice
import org.iot.raspberry.grovepi.devices.GroveLed

internal class GroveBuzzer(groveLed : GroveLed) : AdjustableBuzzer, GrovePulseWithModulationOutputDevice(groveLed) {
}

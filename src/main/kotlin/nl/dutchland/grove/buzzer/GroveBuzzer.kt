package nl.dutchland.grove.buzzer

import nl.dutchland.grove.digitaloutput.GrovePulseWidthModulationOutputDevice
import nl.dutchland.grove.grovepiports.DigitalPort
import nl.dutchland.grove.grovepiports.PulseWidthModulationPort
import org.iot.raspberry.grovepi.GrovePi
import org.iot.raspberry.grovepi.devices.GroveLed

private class GroveBuzzer(groveLed : GroveLed)
    : AdjustableBuzzer, GrovePulseWidthModulationOutputDevice(groveLed)

class GroveBuzzerFactory(private val grovePi : GrovePi) {
    fun on(port : DigitalPort) : Buzzer {
        return GroveBuzzer(
                GroveLed(this.grovePi, port.digitalPin))
    }

    fun adjustableBuzzerOn(port: PulseWidthModulationPort) : AdjustableBuzzer {
        return GroveBuzzer(
                GroveLed(this.grovePi, port.digitalPin))
    }
}

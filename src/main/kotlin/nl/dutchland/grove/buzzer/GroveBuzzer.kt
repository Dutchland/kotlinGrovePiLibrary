package nl.dutchland.grove.buzzer

import nl.dutchland.grove.digitaloutput.GrovePulseWithModulationOutputDevice
import nl.dutchland.grove.grovepiports.DigitalPort
import nl.dutchland.grove.grovepiports.PulseWidthModulationPort
import org.iot.raspberry.grovepi.GrovePi
import org.iot.raspberry.grovepi.devices.GroveLed

private class GroveBuzzer(groveLed : GroveLed)
    : AdjustableBuzzer, GrovePulseWithModulationOutputDevice(groveLed) {
}

class GroveBuzzerFactory(private val grovePi : GrovePi) {
    fun createBuzzerOn(port : DigitalPort) : Buzzer {
        return GroveBuzzer(
                GroveLed(this.grovePi, port.digitalPin))
    }

    fun createAdjustableBuzzerOn(port: PulseWidthModulationPort) : AdjustableBuzzer {
        return GroveBuzzer(
                GroveLed(this.grovePi, port.digitalPin))
    }
}

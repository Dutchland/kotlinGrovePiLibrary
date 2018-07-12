package nl.dutchland.grove.buzzer

import nl.dutchland.grove.grovepiports.DigitalPort
import nl.dutchland.grove.grovepiports.PulseWidthModulationPort
import org.iot.raspberry.grovepi.GrovePi

class GroveBuzzerFactory(private val grovePi : GrovePi) {
    fun createBuzzer(port : DigitalPort) : Buzzer {
        return GroveBuzzer(
                org.iot.raspberry.grovepi.devices.GroveLed(this.grovePi, port.digitalPin))
    }

    fun createAdjustableBuzzer(port: PulseWidthModulationPort) : AdjustableBuzzer {
        return GroveBuzzer(
                org.iot.raspberry.grovepi.devices.GroveLed(this.grovePi, port.digitalPin))
    }
}
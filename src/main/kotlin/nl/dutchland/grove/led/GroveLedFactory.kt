package nl.dutchland.grove.led

import nl.dutchland.grove.grovepiports.DigitalPort
import nl.dutchland.grove.grovepiports.PulseWidthModulationPort
import org.iot.raspberry.grovepi.GrovePi

class GroveLedFactory(private val grovePi : GrovePi) {
    fun on(port : DigitalPort) : Led {
        return GroveLed(
                org.iot.raspberry.grovepi.devices.GroveLed(this.grovePi, port.digitalPin))
    }

    fun dimmableLedOn(port: PulseWidthModulationPort) : DimmableLed {
        return GroveLed(
                org.iot.raspberry.grovepi.devices.GroveLed(this.grovePi, port.digitalPin))
    }
}
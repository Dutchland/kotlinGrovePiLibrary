package nl.dutchland.grove.led

import nl.dutchland.grove.digitaloutput.GrovePulseWidthModulationOutputDevice
import nl.dutchland.grove.grovepiports.DigitalPort
import nl.dutchland.grove.grovepiports.PulseWidthModulationPort
import org.iot.raspberry.grovepi.GrovePi
import org.iot.raspberry.grovepi.devices.GroveLed

private class GroveLed(groveLed : GroveLed)
    : DimmableLed, GrovePulseWidthModulationOutputDevice(groveLed)

class GroveLedFactory(private val grovePi : GrovePi) {
    fun createLed(port : DigitalPort) : Led {
        return GroveLed(
                GroveLed(this.grovePi, port.digitalPin))
    }

    fun createDimmableLed(port: PulseWidthModulationPort) : DimmableLed {
        return GroveLed(
                GroveLed(this.grovePi, port.digitalPin))
    }
}
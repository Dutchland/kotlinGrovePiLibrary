package nl.dutchland.grove.led

import nl.dutchland.grove.digitaloutput.GrovePulseWithModulationOutputDevice
import nl.dutchland.grove.grovepiports.DigitalPort
import nl.dutchland.grove.grovepiports.PulseWidthModulationPort
import nl.dutchland.grove.utility.FractionalPercentage
import org.iot.raspberry.grovepi.GrovePi
import org.iot.raspberry.grovepi.devices.GroveLed

private class GroveLed(groveLed : GroveLed)
    : DimmableLed, GrovePulseWithModulationOutputDevice(groveLed) {
}

class GroveLedFactory(private val grovePi : GrovePi) {
    fun createLed(port : DigitalPort) : Led {
        return GroveLed(
                org.iot.raspberry.grovepi.devices.GroveLed(this.grovePi, port.digitalPin))
    }

    fun createDimmableLed(port: PulseWidthModulationPort) : DimmableLed {
        return GroveLed(
                org.iot.raspberry.grovepi.devices.GroveLed(this.grovePi, port.digitalPin))
    }
}


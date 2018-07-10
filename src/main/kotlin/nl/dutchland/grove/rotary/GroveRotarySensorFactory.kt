package nl.dutchland.grove.rotary

import nl.dutchland.grove.grovepiports.AnalogPort
import nl.dutchland.grove.grovepiports.DigitalPort
import org.iot.raspberry.grovepi.GroveDigitalIn
import org.iot.raspberry.grovepi.GrovePi

class GroveRotarySensorFactory(private val grovePi: GrovePi) {
    fun aRotarySensor(port: AnalogPort) : RotarySensor {
        return GroveRotarySensor(
                org.iot.raspberry.grovepi.devices.GroveRotarySensor(grovePi, port.analogPin))
    }
}
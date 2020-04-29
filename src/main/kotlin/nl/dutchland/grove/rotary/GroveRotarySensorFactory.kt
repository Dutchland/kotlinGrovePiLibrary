package nl.dutchland.grove.rotary

import nl.dutchland.grove.grovepiports.AnalogPort
import org.iot.raspberry.grovepi.GrovePi

class GroveRotarySensorFactory(private val grovePi: GrovePi) {
    fun on(port: AnalogPort, vararg listeners: RotaryChangedListener) : RotarySensor {
        return GroveRotarySensor(
                org.iot.raspberry.grovepi.devices.GroveRotarySensor(grovePi, port.analogPin),
                listeners)
    }
}